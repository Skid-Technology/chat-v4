package gg.vape.event;

public interface ICancelableEvent extends IEvent {
   boolean isCanceled();

   @Override
   default boolean fire() {
      ICancelableEvent iCancelableEvent = EventBus.getInstance().post(this);
      return !iCancelableEvent.isCanceled();
   }

   void setCancelled(boolean var1);
}
