/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.utils;

import gg.vape.Vape;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadTaskQueue {
    private final static String EXECUTION_ERROR_MESSAGE = "Error executing RenderScheduler task";
    private final static long MAX_EXECUTION_NANOS = 5000000L;
    private final static Queue<Runnable> pendingTasks = new ConcurrentLinkedQueue<Runnable>();

    private static Exception propagateException(Exception error) {
        return error;
    }

    public static void enqueue(Runnable task) {
        pendingTasks.add(task);
    }

    public static void runPendingTasks() {
        if (pendingTasks.isEmpty()) {
            return;
        }
        long startTimeNanos = System.nanoTime();
        while (!pendingTasks.isEmpty()) {
            Runnable task = pendingTasks.poll();
            if (task != null) {
                try {
                    task.run();
                }
                catch (Exception error) {
                    Vape.debugLog(EXECUTION_ERROR_MESSAGE);
                    Vape.logThrowable(error);
                }
            }
            if (System.nanoTime() - startTimeNanos <= 5000000L) continue;
        }
    }
}

