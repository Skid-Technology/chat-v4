package gg.vape.utils;

import gg.vape.Vape;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderThreadTaskQueue {
   private static final String EXECUTION_ERROR_MESSAGE = "Error executing RenderScheduler task";
   private static final long MAX_EXECUTION_NANOS = 5000000L;
   private static final Queue<Runnable> pendingTasks = new ConcurrentLinkedQueue<>();

   private static Exception propagateException(Exception error) {
      return error;
   }

   public static void enqueue(Runnable task) {
      pendingTasks.add(task);
   }

   public static void runPendingTasks() {
      if (!pendingTasks.isEmpty()) {
         long startTimeNanos = System.nanoTime();

         while (!pendingTasks.isEmpty()) {
            Runnable task = pendingTasks.poll();
            if (task != null) {
               try {
                  task.run();
               } catch (Exception var4) {
                  Vape.debugLog("Error executing RenderScheduler task");
                  Vape.logThrowable(var4);
               }
            }

            if (System.nanoTime() - startTimeNanos > 5000000L) {
               break;
            }
         }
      }
   }
}
