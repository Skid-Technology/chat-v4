package gg.vape.utils;

import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import org.jetbrains.annotations.NotNull;

public class ThreadBoundExecutor implements Executor {
   private final Queue<Runnable> pendingTasks = new ArrayDeque<>();
   private static GuiComponent[] controlFlowMarker;
   private Runnable currentTask;
   private Thread ownerThread;

   public static void setControlFlowMarker(GuiComponent[] marker) {
      controlFlowMarker = marker;
   }

   private static Throwable propagateThrowable(Throwable error) {
      return error;
   }

   public synchronized void runPending() {
      try {
         if (this.ownerThread == null) {
            this.ownerThread = Thread.currentThread();
         }
      } catch (Throwable var3) {
         throw sneakyThrow(propagateThrowable(var3));
      }

      while ((this.currentTask = this.pendingTasks.poll()) != null) {
         try {
            this.currentTask.run();
         } catch (Throwable var2) {
            Vape.logThrowable(var2);
         }
      }
   }

   public Thread getOwnerThread() {
      return this.ownerThread;
   }

   public static GuiComponent[] getControlFlowMarker() {
      return controlFlowMarker;
   }

   @Override
   public synchronized void execute(@NotNull Runnable runnable) {
      boolean onOwnerThread = this.ownerThread != null && Thread.currentThread().equals(this.ownerThread);
      if (onOwnerThread) {
         runnable.run();
      } else {
         this.pendingTasks.offer(runnable);
      }
   }

   private static RuntimeException sneakyThrow(Throwable error) {
      throwUnchecked(error);
      throw new AssertionError("unreachable");
   }

   private static <T extends Throwable> void throwUnchecked(Throwable error) throws T {
      throw error;
   }

   static {
      if (getControlFlowMarker() != null) {
         setControlFlowMarker(new GuiComponent[1]);
      }
   }
}
