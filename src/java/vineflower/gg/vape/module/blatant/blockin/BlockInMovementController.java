package gg.vape.module.blatant.blockin;

public interface BlockInMovementController {
   void setInput(boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, boolean var6);

   void applySnapshot(BlockPlacementGraph var1);

   void tick();

   void initialize();
}
