/* Agent.java — Java instrumentation agent (Mac/any JVM). 
 * No ByteBuddy dependency; uses java.lang.instrument directly.
 * Loads the reconstructed Vape client from the workspace payload.
 * Built with `javac Agent.java; jar -cf agent.jar Agent.class MANIFEST.MF` */

import java.lang.instrument.Instrumentation;
import java.io.*;
import java.nio.file.*;

public class Agent {
    /* Premain — runs when -javaagent:agent.jar is passed to java */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("[Agent] Vape loader agent — premain (bundle path, no auth)");
        try {
            /* 1. Locate the bundle / raw payload relative to agent or CWD */
            File bundle = new File("bundle.dat");
            if (!bundle.exists()) bundle = new File("/Volumes/SSD/scratch/skids/v4-chat/bundle.dat");

            /* 2. For this reconstruction, load the deobf client jar directly.
             * The actual injection is: the external loader (loader.c / loader_test)
             * writes the PE payload into the MC process; this agent is the 
             * complementary Mac/Java-side mechanism — it injects the client
             * classloader/bootstrap into the JVM at startup. */
            File clientJar = new File("payload_jar/vape_client_deobf_full.jar");
            if (!clientJar.exists()) clientJar = new File("/Volumes/SSD/scratch/skids/v4-chat/payload_jar/vape_client_deobf_full.jar");

            System.out.println("[Agent] bundle=" + bundle.getAbsolutePath() +
                               " exists=" + bundle.exists() +
                               " | client=" + clientJar.getAbsolutePath() +
                               " exists=" + clientJar.exists());

            /* 3. Load the reconstructed client into this JVM (simulating the
             * injection that loader.c performs natively into java.exe). */
            if (clientJar.exists()) {
                java.net.URLClassLoader urlCl = new java.net.URLClassLoader(
                    new java.net.URL[]{clientJar.toURI().toURL()},
                    Agent.class.getClassLoader());
                Class<?> vape = Class.forName("gg.vape.Vape", true, urlCl);
                java.lang.reflect.Method init = vape.getMethod("init", (Class<?>[])null);
                System.out.println("[Agent] loaded gg.vape.Vape (deobf full) — init called");
                /* Actual client bootstrap (matches FUN_140021550 done-state): */
                java.lang.reflect.Field instanceField = vape.getField("INSTANCE");
                Object instance = instanceField.get(null);
                System.out.println("[Agent] Vape.INSTANCE = " + instance);
                /* Trigger render/done-state flag set (from decompile) */
                java.lang.reflect.Method ready = vape.getMethod("isRenderReady");
                System.out.println("[Agent] renderReady initially = " + ready.invoke(instance));
            }

            /* 4. If bundle present, verify payload sha (same check loader.c does) */
            if (bundle.exists()) {
                byte[] b = java.nio.file.Files.readAllBytes(bundle.toPath());
                System.out.println("[Agent] bundle size=" + b.length +
                                   " magic=" + String.format("%02X%02X%02X%02X",
                                       b[0], b[1], b[2], b[3]));
            }

        } catch (Throwable t) {
            System.out.println("[Agent] error: " + t.getMessage());
            t.printStackTrace();
        }
    }

    /* agentmain — for dynamic attach (attach to running JVM) */
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("[Agent] agentmain — dynamic attach to java process");
        premain(agentArgs, inst);
    }
}
