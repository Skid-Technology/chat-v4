package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MChatMessageRenderTarget extends Mapping {
   private static boolean chatMessageControlFlowState;
   public MappingMethod addMessageMethod;
   private static final String ADD_MESSAGE_METHOD_NAME = "addMessage";

   public static boolean getChatMessageControlFlowState() {
      return chatMessageControlFlowState;
   }

   public static boolean getDisabledControlFlowState() {
      boolean bl = getChatMessageControlFlowState();
      return false;
   }

   public static void setChatMessageControlFlowState(boolean state) {
      chatMessageControlFlowState = state;
   }

   public MChatMessageRenderTarget() {
      this(getDisabledControlFlowState());
   }

   private MChatMessageRenderTarget(boolean bl) {
      super(MappedClasses.d);
      if (bl) {
         if (ForgeVersion.MC_26_1.v()) {
            Class[] classArray = new Class[]{MappedClasses.Yr, MappedClasses.uh, MappedClasses.zF};
            Class<Void> clazz = void.class;
            boolean bl2 = true;
            String string = ADD_MESSAGE_METHOD_NAME;
            this.addMessageMethod = this.Y(string, bl2, clazz, classArray);
         }

         GuiComponent.setLegacyComponentState(new GuiComponent[2]);
      } else {
         if (ForgeVersion.MC_26_1.v()) {
            Class[] classArray = new Class[]{MappedClasses.Yr, MappedClasses.uh, MappedClasses.zF};
            Class<Void> clazz = void.class;
            boolean bl3 = true;
            String string = ADD_MESSAGE_METHOD_NAME;
            this.addMessageMethod = this.Y(string, bl3, clazz, classArray);
         }
      }
   }

   static {
      setChatMessageControlFlowState(true);
   }
}
