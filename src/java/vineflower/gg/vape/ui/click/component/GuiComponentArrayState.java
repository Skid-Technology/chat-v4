package gg.vape.ui.click.component;

public class GuiComponentArrayState {
   private static GuiComponent[] components;

   public static GuiComponent[] getComponents() {
      return components;
   }

   public static void setComponents(GuiComponent[] components) {
      GuiComponentArrayState.components = components;
   }

   static {
      if (getComponents() != null) {
         setComponents(new GuiComponent[4]);
      }
   }
}
