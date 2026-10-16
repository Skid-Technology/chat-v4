package gg.vape.notification;

public interface INotification {
   void render();

   void setTargetX(double var1);

   void dismiss();

   void handleClick(double var1, double var3);

   double getCurrentY();

   double getTargetY();

   void setTargetY(double var1);

   void setCurrentX(double var1);

   void setCurrentY(double var1);

   double getWidth();

   double getHeight();

   NotificationType getType();

   double getCurrentX();

   boolean shouldRemove();

   double getTargetX();
}
