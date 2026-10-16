/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.lifecycle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public final class VapeErrorLogger {
    private final static VapeErrorLogger INSTANCE = new VapeErrorLogger();
    private final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final Set<String> syncedFiles = new HashSet<String>();
    private PrintWriter logWriter;
    private boolean opened;
    private boolean handlerInstalled;

    private VapeErrorLogger() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close, "VapeErrorLogger-shutdown"));
    }

    public static VapeErrorLogger getInstance() {
        return INSTANCE;
    }

    public synchronized void installUncaughtExceptionHandler() {
        if (this.handlerInstalled) {
            return;
        }
        this.handlerInstalled = true;
        Thread.UncaughtExceptionHandler existing = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            try {
                this.logThrowable(throwable);
            }
            catch (Throwable throwable2) {
                // empty catch block
            }
            if (existing != null) {
                existing.uncaughtException(thread, throwable);
            }
        });
    }

    private synchronized void ensureOpen() {
        if (this.opened) {
            return;
        }
        this.opened = true;
        try {
            File dir;
            String base = System.getenv("APPDATA");
            if (base == null) {
                base = System.getProperty("user.home");
            }
            if (!(dir = new File(base + File.separator + ".vapeclient")).exists()) {
                dir.mkdirs();
            }
            File logFile = new File(dir, "errors.log");
            this.logWriter = new PrintWriter((Writer)new FileWriter(logFile, true), true);
        }
        catch (Exception ex) {
            this.logWriter = null;
        }
    }

    private synchronized void writeLine(String line) {
        this.ensureOpen();
        if (this.logWriter != null) {
            this.logWriter.println(line);
            this.logWriter.flush();
        }
    }

    public synchronized void logError(String message) {
        if (message == null || message.trim().isEmpty()) {
            return;
        }
        String timestamp = this.fmt.format(new Date());
        this.writeLine(String.format("[%s] [ERROR] %s", timestamp, message.trim()));
    }

    public synchronized void logThrowable(Throwable t) {
        if (t == null) {
            return;
        }
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        String timestamp = this.fmt.format(new Date());
        this.writeLine(String.format("[%s] [ERROR] %s", timestamp, sw.toString().trim()));
    }

    public synchronized void syncNativeLogs() {
        try {
            String appData = System.getenv("APPDATA");
            if (appData == null) {
                return;
            }
            File clrDir = new File(appData + File.separator + "Microsoft" + File.separator + "CLR_v4.0_32");
            if (!clrDir.exists() || !clrDir.isDirectory()) {
                return;
            }
            File[] logFiles = clrDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".log"));
            if (logFiles == null || logFiles.length == 0) {
                return;
            }
            for (File file : logFiles) {
                String key = file.getAbsolutePath() + ":" + file.length();
                if (this.syncedFiles.contains(key)) continue;
                try (BufferedReader reader = new BufferedReader(new FileReader(file));){
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!VapeErrorLogger.isNativeLineError(line)) continue;
                        this.writeLine(line.trim());
                    }
                }
                this.syncedFiles.add(key);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static boolean isNativeLineError(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }
        if (line.contains("DEBUG") || line.contains("client error report: LOAD ") || line.contains("client error report: OK ") || line.contains("materialized product JAR") || line.contains("registered NativeBridge") || line.contains("NativeBridge linked") || line.contains("NativeBridge.start completed") || line.contains("registered org.lwjgl")) {
            return false;
        }
        String lower = line.toLowerCase();
        return lower.contains("exception") || lower.contains("fail") || lower.contains("unable") || lower.contains("invalid") || lower.contains("crash") || line.contains("EXC ");
    }

    private synchronized void close() {
        if (this.logWriter != null) {
            this.logWriter.close();
            this.logWriter = null;
        }
    }
}

