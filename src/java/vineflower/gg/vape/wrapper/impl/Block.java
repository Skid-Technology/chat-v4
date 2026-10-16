package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.mappings.MBlock;
import gg.vape.wrapper.Wrapper;

public class Block extends Wrapper {
   public float c() {
      return MBlock.w(vapeInstance.getMappings().qg, this.I);
   }

   public static Block t(String string) {
      if (ForgeVersion.MC_1_16_5.d()) {
         if (!string.startsWith("block.minecraft.")) {
            string = "block.minecraft." + string;
         }

         for (Object t : (Iterable)MBlock.N(vapeInstance.getMappings().qg)) {
            Block block = new Block(t);
            if (block.U() != null && block.U().equalsIgnoreCase(string)) {
               return block;
            }
         }

         return null;
      } else {
         Object object = vapeInstance.getMappings().qg.a(string);
         return object == null ? null : new Block(object);
      }
   }

   public int d(int n, int n2, int n3) {
      if (ForgeVersion.MC_1_16_5.d()) {
         Vape.notifyNativeStackTrace();
         return 0;
      } else if (ForgeVersion.MC_1_7_10.L()) {
         return MBlock.Q(vapeInstance.getMappings().qg, this.I, Minecraft.theWorld().getObject(), n, n2, n3);
      } else if (ForgeVersion.MC_1_8_9.L()) {
         return MBlock.V(vapeInstance.getMappings().qg, this.I, Minecraft.theWorld().getObject(), BlockPos.create(n, n2, n3).getObject());
      } else {
         return ForgeVersion.MC_1_12_2.L()
            ? MBlock.W(vapeInstance.getMappings().qg, this.I, Minecraft.theWorld().getBlockState(BlockPos.create(n, n2, n3)).getObject())
            : 0;
      }
   }

   public Boolean J(BlockState blockState, boolean bl) {
      return ForgeVersion.MC_1_16_5.d() ? MBlock.X(vapeInstance.getMappings().qg, this.I) : vapeInstance.getMappings().qg.C(this.I, blockState, bl);
   }

   public Material H() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return null;
      } else if (ForgeVersion.MC_1_16_5.d()) {
         return new Material(MBlock.k(vapeInstance.getMappings().qg, this.I));
      } else {
         return ForgeVersion.MC_1_12_2.d()
            ? new Material(MBlock.z(vapeInstance.getMappings().qg, this.I, null))
            : new Material(MBlock.h(vapeInstance.getMappings().qg, this.I));
      }
   }

   public ItemStack Z(World world, BlockPos blockPos, BlockState blockState) {
      return new ItemStack(MBlock.Q(vapeInstance.getMappings().qg, this.I, world.getObject(), blockPos.getObject(), blockState.getObject()));
   }

   public AxisAlignedBB Q(World world, int n, int n2, int n3) {
      if (ForgeVersion.MC_1_16_5.d()) {
         Vape.notifyNativeStackTrace();
         return null;
      } else {
         return new AxisAlignedBB(MBlock.J(vapeInstance.getMappings().qg, this.I, world.getObject(), n, n2, n3));
      }
   }

   public boolean p(BlockState blockState) {
      if (!ForgeVersion.MC_1_20_6.d()) {
         if (ForgeVersion.MC_1_16_5.d()) {
            return vapeInstance.getMappings().qg.A(this.I);
         } else {
            return ForgeVersion.MC_1_12_2.d()
               ? vapeInstance.getMappings().qg.E(this.I, blockState.getObject())
               : vapeInstance.getMappings().qg.F$src$Z$6w97vr(this.I);
         }
      } else {
         return blockState != null && !blockState.isNull() ? blockState.g() && blockState.Y() : false;
      }
   }

   public String U() {
      return MBlock.l(vapeInstance.getMappings().qg, this.I);
   }

   public AxisAlignedBB M(World world, int n, int n2, int n3) {
      return new AxisAlignedBB(MBlock.d(vapeInstance.getMappings().qg, this.I, world.getObject(), n, n2, n3));
   }

   public BlockStatePredicate a() {
      return new BlockStatePredicate(MBlock.N(vapeInstance.getMappings().qg, this.I));
   }

   public boolean y(World world, BlockPos blockPos, BlockState blockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float f, float f2, float f3) {
      if (ForgeVersion.MC_1_16_5.d()) {
         RayTraceResult rayTraceResult = RayTraceResult.create(null, Vec3.create(f, f2, f3), enumFacing, blockPos);
         Object object = MBlock.E(
            vapeInstance.getMappings().qg,
            this.I,
            Minecraft.theWorld().getBlockState(blockPos).getObject(),
            world.getObject(),
            blockPos.getObject(),
            entityPlayer.getObject(),
            enumFacing.getObject(),
            rayTraceResult.getObject()
         );
         EnumActionResult enumActionResult = new EnumActionResult(object);
         return enumActionResult.equals(EnumActionResult.success());
      } else if (ForgeVersion.MC_1_12_2.d()) {
         return MBlock.G(
            vapeInstance.getMappings().qg,
            this.I,
            world.getObject(),
            blockPos.getObject(),
            blockState.getObject(),
            entityPlayer.getObject(),
            EnumHand.mainHand().getObject(),
            enumFacing.getObject(),
            f,
            f2,
            f3
         );
      } else {
         return ForgeVersion.MC_1_7_10.Y()
            ? MBlock.L(
               vapeInstance.getMappings().qg,
               this.I,
               world.getObject(),
               blockPos.getObject(),
               blockState.getObject(),
               entityPlayer.getObject(),
               enumFacing.getObject(),
               f,
               f2,
               f3
            )
            : MBlock.w(
               vapeInstance.getMappings().qg,
               this.I,
               world.getObject(),
               blockPos.getX(),
               blockPos.getY(),
               blockPos.getZ(),
               entityPlayer.getObject(),
               enumFacing.Y(),
               f,
               f2,
               f3
            );
      }
   }

   public Block(Object object) {
      super(object);
   }

   public void W(World world, int n, int n2, int n3, Entity entity) {
      MBlock.v(vapeInstance.getMappings().qg, this.I, world.getObject(), n, n2, n3, entity.getObject());
   }

   public static boolean r(Entity entity) {
      return ForgeVersion.MC_1_21_4.d() ? MBlock.k$src$Z$8ngnn6(vapeInstance.getMappings().qg, entity.getObject()) : true;
   }

   public int M() {
      return MBlock.E(vapeInstance.getMappings().qg, this.I);
   }

   public void r(World world, Entity entity) {
      if (ForgeVersion.MC_1_21_4.d()) {
         if (ForgeVersion.MC_26_2.d()) {
            BlockPos blockPos = entity.C$src$Lgg_vape_wrapper_impl_BlockPos_$y7f4vu();
            MBlock.u(
               vapeInstance.getMappings().qg,
               this.I,
               world.getObject(),
               world.getBlockState(blockPos).getObject(),
               blockPos.getObject(),
               entity.getObject(),
               entity.M$src$F$ff28gb()
            );
         } else {
            MBlock.y(vapeInstance.getMappings().qg, this.I, world.getObject(), entity.getObject());
         }
      }
   }

   public float P() {
      if (ForgeVersion.MC_1_20_6.d()) {
         throw new UnsupportedOperationException("Unimplemented");
      } else {
         return vapeInstance.getMappings().qg.F(this.I);
      }
   }

   public BlockState Z() {
      return (BlockState)(ForgeVersion.c() >= 35 ? this.a() : new BlockState(MBlock.N(vapeInstance.getMappings().qg, this.I)));
   }

   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
   }

   public AxisAlignedBB Y(BlockState blockState, World world, BlockPos blockPos) {
      return new AxisAlignedBB(MBlock.y(vapeInstance.getMappings().qg, this.I, blockState.getObject(), world.getObject(), blockPos.getObject()));
   }

   public static int R(Block block) {
      if (ForgeVersion.MC_1_16_5.d()) {
         Object object = MBlock.N(vapeInstance.getMappings().qg, block.getObject());
         return vapeInstance.getMappings().qg.h(object);
      } else {
         return vapeInstance.getMappings().qg.h(block.getObject());
      }
   }

   public boolean X(BlockState blockState) {
      if (!ForgeVersion.MC_1_20_6.d()) {
         if (ForgeVersion.MC_1_16_5.d()) {
            return vapeInstance.getMappings().qg.A(this.I);
         } else {
            return ForgeVersion.MC_1_12_2.d() ? vapeInstance.getMappings().qg.r(this.I, blockState.getObject()) : vapeInstance.getMappings().qg.Z(this.I);
         }
      } else {
         return blockState != null && !blockState.isNull() ? blockState.g() && blockState.Y() : false;
      }
   }

   public AxisAlignedBB Q(World world, BlockPos blockPos) {
      return new AxisAlignedBB(MBlock.d(vapeInstance.getMappings().qg, this.I, world.getObject(), blockPos.getObject()));
   }
}
