package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.notification.NotificationType;
import java.util.ArrayList;
import java.util.List;

public class MappingTaskSet {
   protected List<MappingTask> D = new ArrayList<>();

   public void d() {
      int n = 0;

      for (MappingTask mappingTask : this.D) {
         n++;
         if (!mappingTask.isApplied()) {
            int n2 = 0;
            String phase = "prepare";

            try {
               mappingTask.prepare();
               phase = "transform";
               mappingTask.transform();
               phase = "serialize";
               mappingTask.serialize();
               phase = "commit";
               n2 = mappingTask.commit();
            } catch (Throwable var7) {
               n2 = -1;
               Vape.debugLog("Mapping task " + n + " failed during " + phase + ": " + mappingTask.getClass().getName() + " -> " + targetName(mappingTask));
               Vape.logThrowable(var7);
            }

            if (n2 != 0) {
               String string = n2 + " " + n;
               if (n2 != -1) {
                  Vape.debugLog("Mapping task " + n + " commit returned " + n2 + ": " + mappingTask.getClass().getName() + " -> " + targetName(mappingTask));
               }

               if (Vape.INSTANCE.getNotificationManager() != null) {
                  Vape.INSTANCE
                     .getNotificationManager()
                     .show(
                        "Error with injection",
                        "Please report to support:\nError code " + string + "\n\nSome features may not function",
                        NotificationType.ALERT,
                        30000L,
                        true
                     );
               }
            }
         }
      }
   }

   private static String targetName(MappingTask mappingTask) {
      Class targetClass = mappingTask.getTargetClass();
      return targetClass == null ? "<null>" : targetClass.getName();
   }

   private static Throwable a(Throwable throwable) {
      return throwable;
   }

   public void C() {
      for (MappingTask mappingTask : this.D) {
         mappingTask.rollback();
      }
   }
}
