package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingMethod;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.ForgeVersion;

public class MRenderBatchFlushTarget extends Mapping {
   private static int glCommandEncoderControlFlowState;
   private static final String DRAW_FROM_BUFFERS_METHOD_NAME = "drawFromBuffers";
   public MappingMethod drawFromBuffersMethod;

   public static int getInitialControlFlowState() {
      int n = getGlCommandEncoderControlFlowState();
      return n == 0 ? 64 : 0;
   }

   public static void setGlCommandEncoderControlFlowState(int state) {
      glCommandEncoderControlFlowState = state;
   }

   public MRenderBatchFlushTarget() {
      this(getInitialControlFlowState());
   }

   private MRenderBatchFlushTarget(int n) {
      super(MappedClasses.zg);
      if (n != 0) {
         Class[] classArray = ForgeVersion.MC_26_2.d()
            ? new Class[]{MappedClasses.uq, int.class, int.class, int.class, MappedClasses.Yz, MappedClasses.FA, int.class, int.class}
            : new Class[]{MappedClasses.uq, int.class, int.class, int.class, MappedClasses.Yz, MappedClasses.FA, int.class};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = DRAW_FROM_BUFFERS_METHOD_NAME;
         this.drawFromBuffersMethod = this.Y(string, bl, clazz, classArray);
      } else {
         Class[] classArray = ForgeVersion.MC_26_2.d()
            ? new Class[]{MappedClasses.uq, int.class, int.class, int.class, MappedClasses.Yz, MappedClasses.FA, int.class, int.class}
            : new Class[]{MappedClasses.uq, int.class, int.class, int.class, MappedClasses.Yz, MappedClasses.FA, int.class};
         Class<Void> clazz = void.class;
         boolean bl = true;
         String string = DRAW_FROM_BUFFERS_METHOD_NAME;
         this.drawFromBuffersMethod = this.Y(string, bl, clazz, classArray);
         GuiComponent.setLegacyComponentState(new GuiComponent[3]);
      }
   }

   public static int getGlCommandEncoderControlFlowState() {
      return glCommandEncoderControlFlowState;
   }

   static {
      setGlCommandEncoderControlFlowState(0);
   }
}
