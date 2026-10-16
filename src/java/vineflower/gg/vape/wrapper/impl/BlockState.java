package gg.vape.wrapper.impl;

import com.google.common.collect.ImmutableMap;
import gg.vape.mapping.mappings.MIBlockState;
import gg.vape.wrapper.Wrapper;

public class BlockState extends Wrapper {
   public boolean u() {
      return vapeInstance.getMappings().DE.d(this.I);
   }

   public boolean Y() {
      return vapeInstance.getMappings().DE.W(this.I);
   }

   public Object I(BlockProperty blockProperty) {
      if (ForgeVersion.MC_1_20_6.d()) {
         return vapeInstance.getMappings().DE.o(this.I, blockProperty.getObject());
      } else {
         ImmutableMap immutableMap = vapeInstance.getMappings().DE.w(this.I);

         for (Object e : immutableMap.keySet()) {
            if (e.getClass().equals(blockProperty.getObject().getClass())) {
               return immutableMap.get(e);
            }
         }

         return immutableMap.containsKey(blockProperty.getObject()) ? immutableMap.get(blockProperty.getObject()) : null;
      }
   }

   public boolean x() {
      return vapeInstance.getMappings().DE.e(this.I);
   }

   public Block getBlock() {
      return new Block(vapeInstance.getMappings().DE.v(this.I));
   }

   public float getPlayerRelativeDestroyProgress(EntityPlayer player, World world, BlockPos blockPos) {
      if (ForgeVersion.MC_1_16_5.d()) {
         return vapeInstance.getMappings().CJ.getDestroyProgress(this.I, player.getObject(), world.getObject(), blockPos.getObject());
      } else {
         return ForgeVersion.MC_1_12_2.d()
            ? vapeInstance.getMappings().DE.getPlayerRelativeBlockHardness(this.I, player.getObject(), world.getObject(), blockPos.getObject())
            : vapeInstance.getMappings()
               .qg
               .getPlayerRelativeBlockHardness(this.getBlock().getObject(), player.getObject(), world.getObject(), blockPos.getObject());
      }
   }

   public BlockState(Object object) {
      super(object);
   }

   public boolean g() {
      return vapeInstance.getMappings().DE.I(this.I);
   }

   public BlockStateWorldBridge j() {
      return new BlockStateWorldBridge(MIBlockState.j(vapeInstance.getMappings().DE, this.I));
   }
}
