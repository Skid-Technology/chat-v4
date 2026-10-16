package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MRayTraceResult;
import gg.vape.wrapper.Wrapper;

public class RayTraceResult extends Wrapper {
   private static int[] g;

   public int g() {
      if (ForgeVersion.MC_1_7_10.L()) {
         return vapeInstance.getMappings().C0.a(this.I);
      } else {
         BlockPos blockPos = this.getBlockPos();
         return blockPos.getX();
      }
   }

   public static RayTraceResult create(RayTraceResult_type rayTraceResult_type, Vec3 vec3, EnumFacing enumFacing, BlockPos blockPos) {
      if (ForgeVersion.MC_1_16_5.d()) {
         Object object = vapeInstance.getMappings().C0.o(vec3.getObject(), enumFacing.getObject(), blockPos.getObject(), false);
         return new RayTraceResult(object);
      } else {
         return new RayTraceResult(
            vapeInstance.getMappings().C0.m(rayTraceResult_type.getObject(), vec3.getObject(), enumFacing.getObject(), blockPos.getObject())
         );
      }
   }

   public RayTraceResult(Object object) {
      super(object);
   }

   public int Z() {
      if (ForgeVersion.MC_1_7_10.L()) {
         return vapeInstance.getMappings().C0.n(this.I);
      } else {
         EnumFacing enumFacing = this.getSideHit();
         return enumFacing.isNotNull() ? enumFacing.Y() : 0;
      }
   }

   public Block Z$src$Lgg_vape_wrapper_impl_Block_$6x2c9a() {
      return Minecraft.theWorld().getBlockByPos(this.g(), this.T(), this.a$src$I$8nuo9d());
   }

   public int a$src$I$8nuo9d() {
      if (ForgeVersion.MC_1_7_10.L()) {
         return vapeInstance.getMappings().C0.z(this.I);
      } else {
         BlockPos blockPos = this.getBlockPos();
         return blockPos.getZ();
      }
   }

   public EnumFacing getSideHit() {
      return ForgeVersion.MC_1_7_10.L() ? EnumFacing.T(vapeInstance.getMappings().C0.n(this.I)) : new EnumFacing(vapeInstance.getMappings().C0.P(this.I));
   }

   public Entity getEntity() {
      return ForgeVersion.MC_1_16_5.d() ? this.g$src$Lgg_vape_wrapper_impl_Entity_$5c9han() : new Entity(vapeInstance.getMappings().C0.F(this.I));
   }

   public static int[] O() {
      return g;
   }

   public static void B(int[] nArray) {
      g = nArray;
   }

   public RayTraceResult_type getTypeOfHit() {
      return new RayTraceResult_type(MRayTraceResult.Z(vapeInstance.getMappings().C0, this.I));
   }

   public Vec3 getHitVec() {
      return new Vec3(vapeInstance.getMappings().C0.o(this.I));
   }

   public boolean isEntityHit() {
      return this.isNotNull() && this.getTypeOfHit().equals(RayTraceResult_type.entity());
   }

   private Entity g$src$Lgg_vape_wrapper_impl_Entity_$5c9han() {
      return MappedClasses.zl.isInstance(this.I) ? new Entity(vapeInstance.getMappings().C0.F(this.I)) : new Entity(null);
   }

   public boolean isBlockHit() {
      return this.isNotNull() && this.getTypeOfHit().equals(RayTraceResult_type.block());
   }

   public void setEntity(Entity entity) {
      vapeInstance.getMappings().C0.a(this.I, entity.getObject());
   }

   public BlockPos getBlockPos() {
      if (ForgeVersion.MC_1_16_5.d()) {
         return MappedClasses.qF.isInstance(this.I) ? new BlockPos(vapeInstance.getMappings().C0.D(this.I)) : null;
      } else {
         return new BlockPos(vapeInstance.getMappings().C0.D(this.I));
      }
   }

   public int T() {
      if (ForgeVersion.MC_1_7_10.L()) {
         return vapeInstance.getMappings().C0.w(this.I);
      } else {
         BlockPos blockPos = this.getBlockPos();
         return blockPos.getY();
      }
   }

   public static RayTraceResult create(Entity entity, Vec3 vec3) {
      if (ForgeVersion.MC_1_16_5.d()) {
         Object object = vapeInstance.getMappings().C0.M(entity.getObject(), vec3.getObject());
         return new RayTraceResult(object);
      } else {
         return new RayTraceResult(vapeInstance.getMappings().C0.y(entity.getObject(), vec3.getObject()));
      }
   }

   static {
      if (O() == null) {
         B(new int[2]);
      }
   }
}
