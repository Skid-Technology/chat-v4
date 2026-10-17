package gg.vape.event;

public interface IEvent {
   EventListeners getListeners();

   default boolean fire() {
      EventBus.getInstance().post(this);
      return true;
   }
}
