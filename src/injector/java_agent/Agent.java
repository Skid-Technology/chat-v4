/*
 * Agent.java — macOS Java instrumentation agent for the v4-chat loader.
 *
 * Two entry points, both portable (no third-party dependencies):
 *   premain(String,Instrumentation)   — via  java -javaagent:agent.jar=…
 *   agentmain(String,Instrumentation) — via the Attach API (attach.sh)
 *
 * The agent (a) locates bundle.dat / the client jar next to itself, in the
 * CWD, via env or via args, (b) verifies the bundled payload SHA-256 against
 * the digest recorded in the VBDL index, and (c) loads the reconstructed
 * client jar into a child URLClassLoader and touches gg.vape.Vape.
 *
 * Nothing here performs network calls or authentication; it mirrors the
 * offline "bundled" path of src/injector/loader.c.
 *
 * Build: make agent   (javac + jar, see ../Makefile)
 * Args:  key=value[,key=value…]  keys: bundle, client, verbose
 */

import java.lang.instrument.Instrumentation;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public final class Agent {

    private static final int MAGIC = 0x56424C44; /* "VBDL" */

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("[Agent] v4-chat loader agent — premain (" + osTag() + ")");
        run(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("[Agent] v4-chat loader agent — agentmain (dynamic attach)");
        run(agentArgs, inst);
    }

    private static void run(String agentArgs, Instrumentation inst) {
        try {
            Map<String, String> args = parseArgs(agentArgs);
            boolean verbose = args.containsKey("verbose");
            File self = selfLocation();
            if (verbose) {
                System.out.println("[Agent] java=" + System.getProperty("java.home")
                        + " os=" + osTag() + " self=" + self);
            }

            File bundle = firstFile(
                    fileArg(args.get("bundle")),
                    fileEnv("VAPE_BUNDLE"),
                    locate(self, "bundle.dat"),
                    locate(cwd(), "bundle.dat"),
                    macSearch("bundle.dat"));

            File client = firstFile(
                    fileArg(args.get("client")),
                    fileEnv("VAPE_CLIENT_JAR"),
                    locate(self, "payload_jar/vape_client_deobf_full.jar"),
                    locate(self, "payload_jar/vape_client_deobf.jar"),
                    locate(self, "payload_jar/vape_client.jar"),
                    locate(cwd(), "payload_jar/vape_client_deobf_full.jar"),
                    macSearch("payload_jar/vape_client_deobf_full.jar"));

            System.out.println("[Agent] bundle=" + describe(bundle)
                    + " | client=" + describe(client));

            if (bundle != null) {
                verifyBundle(bundle);
            } else {
                System.out.println("[Agent] no bundle.dat found — skipping verification "
                        + "(set VAPE_BUNDLE or pass bundle=/path/to/bundle.dat)");
            }

            if (client != null) {
                loadClient(client);
            } else {
                System.out.println("[Agent] no client jar found — nothing to load "
                        + "(set VAPE_CLIENT_JAR or pass client=/path/to/client.jar)");
            }
        } catch (Throwable t) {
            System.out.println("[Agent] error: " + t);
            t.printStackTrace(System.out);
        }
    }

    /* ---- client loading ---------------------------------------------- */

    private static void loadClient(File jar) {
        try {
            URLClassLoader cl = new URLClassLoader(
                    new URL[]{jar.toURI().toURL()}, Agent.class.getClassLoader());
            Class<?> vape = Class.forName("gg.vape.Vape", false, cl);
            System.out.println("[Agent] loaded " + vape.getName() + " from " + jar.getName());
            try {
                Object instance = vape.getField("INSTANCE").get(null);
                System.out.println("[Agent] gg.vape.Vape.INSTANCE = " + instance);
            } catch (NoSuchFieldException e) {
                System.out.println("[Agent] gg.vape.Vape has no INSTANCE field (deobf gaps)");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[Agent] client jar has no gg.vape.Vape — not a client jar?");
        } catch (Throwable t) {
            // VerifyError and friends carry multi-page verifier dumps; keep it short.
            String msg = String.valueOf(t);
            int nl = msg.indexOf('\n');
            if (nl > 0) msg = msg.substring(0, nl) + " …";
            System.out.println("[Agent] client load failed (continuing): " + msg);
        }
    }

    /* ---- bundle verification (mirrors bundle_extract in loader.c) ----- */

    private static void verifyBundle(File bundle) {
        try {
            byte[] b = Files.readAllBytes(bundle.toPath());
            if (b.length < 12 || le32(b, 0) != MAGIC) {
                System.out.println("[Agent] bundle magic mismatch — not a VBDL archive");
                return;
            }
            int version = le32(b, 4);
            int n = le32(b, 8);
            System.out.println("[Agent] bundle VBDL v" + version + ", " + n + " entries, "
                    + b.length + " bytes");
            int off = 12;
            for (int i = 0; i < n && off < b.length; i++) {
                int nl = b[off++] & 0xff;
                String name = new String(b, off, nl, StandardCharsets.UTF_8);
                off += nl;
                long eoff = le64(b, off);
                long esize = le64(b, off + 8);
                byte[] want = new byte[32];
                System.arraycopy(b, off + 16, want, 0, 32);
                off += 48;
                if (eoff < 0 || esize < 0 || eoff + esize > b.length) {
                    System.out.println("[Agent]   " + name + ": entry out of range");
                    continue;
                }
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(b, (int) eoff, (int) esize);
                byte[] got = md.digest();
                String verdict = MessageDigest.isEqual(want, got) ? "sha256 ok" : "sha256 MISMATCH";
                System.out.println("[Agent]   " + name + ": " + esize + " bytes, " + verdict);
            }
        } catch (Throwable t) {
            System.out.println("[Agent] bundle verification failed: " + t);
        }
    }

    /* ---- path resolution --------------------------------------------- */

    private static File selfLocation() {
        try {
            return new File(Agent.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI());
        } catch (Throwable t) {
            return cwd();
        }
    }

    private static File cwd() {
        return new File(System.getProperty("user.dir", ".")).getAbsoluteFile();
    }

    private static File fileArg(String v) {
        return (v == null || v.isEmpty()) ? null : new File(v).getAbsoluteFile();
    }

    private static File fileEnv(String key) {
        return fileArg(System.getenv(key));
    }

    /* Walk up from `start` (≤8 levels) and return `rel` if it exists. */
    private static File locate(File start, String rel) {
        if (start == null) return null;
        File dir = start.isDirectory() ? start : start.getParentFile();
        for (int i = 0; dir != null && i < 8; i++, dir = dir.getParentFile()) {
            File f = new File(dir, rel);
            if (f.isFile()) return f;
        }
        return null;
    }

    /* macOS convenience locations, e.g. under Application Support. */
    private static File macSearch(String rel) {
        String home = System.getProperty("user.home", "");
        File f = locate(new File(home, "Library/Application Support/v4-chat"), rel);
        if (f != null) return f;
        return firstFile(locate(new File("/usr/local/share/v4-chat"), rel),
                locate(new File("/opt/homebrew/share/v4-chat"), rel));
    }

    private static File firstFile(File... candidates) {
        for (File c : candidates) {
            if (c != null && c.isFile()) return c;
        }
        return null;
    }

    private static String describe(File f) {
        return f == null ? "<none>" : f.getAbsolutePath();
    }

    private static String osTag() {
        return System.getProperty("os.name") + "/" + System.getProperty("os.arch");
    }

    /* ---- small helpers ----------------------------------------------- */

    private static Map<String, String> parseArgs(String s) {
        Map<String, String> m = new HashMap<>();
        if (s == null) return m;
        for (String part : s.split(",")) {
            if (part.isEmpty()) continue;
            int eq = part.indexOf('=');
            if (eq < 0) m.put(part.trim(), "true");
            else m.put(part.substring(0, eq).trim(), part.substring(eq + 1).trim());
        }
        return m;
    }

    private static int le32(byte[] b, int o) {
        return (b[o] & 0xff) | (b[o + 1] & 0xff) << 8
                | (b[o + 2] & 0xff) << 16 | (b[o + 3] & 0xff) << 24;
    }

    private static long le64(byte[] b, int o) {
        return (le32(b, o) & 0xffffffffL) | ((long) le32(b, o + 4) << 32);
    }
}
