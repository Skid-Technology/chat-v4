package gg.vape.utils;

import gg.vape.mapping.MappedClasses;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockRayTraceResult;
import gg.vape.wrapper.impl.DataComponents;
import gg.vape.wrapper.impl.Direction;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EntityRayTraceBridge;
import gg.vape.wrapper.impl.EntityRayTraceResult;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.PlayerControllerMP;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.World;
import gg.vape.wrapper.impl.WorldClient;
import java.util.List;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public class RayTraceUtil {
   public static RayTraceResult b(
      Vec3 vec3, Vec3 vec32, World world, Entity entity, boolean bl, boolean bl2, boolean bl3, @Nullable Predicate<Entity> predicate
   ) {
      RayTraceResult rayTraceResult = new RayTraceResult(null);
      if (!vec3.isNull() && !vec32.isNull() && !world.isNull()) {
         double d = vec3.distanceTo(vec32);
         rayTraceResult = world.K(vec3, vec32, bl, bl2, bl3, entity);
         double d2 = d;
         if (rayTraceResult != null && rayTraceResult.isNotNull()) {
            d2 = rayTraceResult.getHitVec().distanceTo(vec3);
         }

         double d3 = d2 / d;
         Vec3 vec33 = vec32.q(vec3).scale(d3 / d);
         Vec3 vec34 = vec3.addVector(vec33.getX() * d, vec33.getY() * d, vec33.getZ() * d);
         float f = 1.0F;
         double d4 = vec3.getX() - entity.z();
         double d5 = vec3.getY() - (entity.N() + entity.X());
         double d6 = vec3.getZ() - entity.h();
         AxisAlignedBB axisAlignedBB = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().addCoord(d4, d5, d6);
         List list = world.F(entity, axisAlignedBB.addCoord(vec33.getX() * d, vec33.getY() * d, vec33.getZ() * d).expand(f, f, f));
         double d7 = d2;
         Wrapper wrapper = null;
         Vec3 vec35 = null;
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();

         for (Object e : list) {
            Entity entity2 = new Entity(e);
            if (!entity2.equals(entityPlayerSP) && (predicate == null || predicate.test(entity2)) && entity2.n$src$Z$fx7gig()) {
               float f2 = entity2.b();
               AxisAlignedBB axisAlignedBB2 = entity2.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(f2, f2, f2);
               RayTraceResult rayTraceResult2 = axisAlignedBB2.calculateIntercept(vec3, vec34);
               if (axisAlignedBB2.isVecInside(vec3)) {
                  if (0.0 < d7 || d7 == 0.0) {
                     wrapper = entity2;
                     vec35 = rayTraceResult2 != null && !rayTraceResult2.isNull() ? rayTraceResult2.getHitVec() : vec3;
                     d7 = 0.0;
                  }
               } else {
                  double d8;
                  if (rayTraceResult2 != null && rayTraceResult2.isNotNull() && ((d8 = vec3.distanceTo(rayTraceResult2.getHitVec())) < d7 || d7 == 0.0)) {
                     if (!entity2.equals(entity.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12()) || entity2.C$src$Z$f9kazx()) {
                        wrapper = entity2;
                        vec35 = rayTraceResult2.getHitVec();
                        d7 = d8;
                     } else if (d7 == 0.0) {
                        wrapper = entity2;
                        vec35 = rayTraceResult2.getHitVec();
                     }
                  }
               }
            }
         }

         if (wrapper != null && wrapper.isNotNull() && (d7 < d2 || rayTraceResult.isNull() || rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.miss()))
            )
          {
            rayTraceResult = RayTraceResult.create((Entity)wrapper, vec35);
         }

         return rayTraceResult;
      } else {
         return rayTraceResult;
      }
   }

   public static RayTraceResult q(double d, float f, boolean bl, @Nullable Predicate<Entity> predicate) {
      return U(Minecraft.F(), d, f, bl, predicate);
   }

   private static RayTraceResult l(EntityLivingBase entityLivingBase, float f, @Nullable Predicate<Entity> predicate) {
      if (ForgeVersion.MC_1_21_11.v()) {
         return null;
      } else {
         ItemStack itemStack = Minecraft.thePlayer().Y$src$Lgg_vape_wrapper_impl_ItemStack_$1e6807m();
         if (itemStack.isNull()) {
            return null;
         } else {
            Object object = itemStack.w(DataComponents.l());
            if (object == null) {
               return null;
            } else {
               EntityRayTraceBridge entityRayTraceBridge = new EntityRayTraceBridge(object);
               return entityRayTraceBridge.getClosestHit(entityLivingBase, f, entityHandle -> lambda$getAttackRangeHit121$0(predicate, entityHandle));
            }
         }
      }
   }

   private static boolean lambda$getAttackRangeHit121$0(Predicate predicate, Object object) {
      Entity entity = new Entity(object);
      return !entity.O$src$Z$fg5u49() && entity.c$src$Z$fr5pzh() ? predicate == null || predicate.test(entity) : false;
   }

   private static RayTraceResult y(EntityLivingBase entityLivingBase, float f, float f2, boolean bl, @Nullable Predicate<Entity> predicate) {
      if (ForgeVersion.MC_1_20_6.d()) {
         return m(entityLivingBase, f, f2, bl, predicate);
      } else {
         float f3 = 1.0F;
         RayTraceResult rayTraceResult = new RayTraceResult(null);
         if (!entityLivingBase.isNotNull()) {
            return rayTraceResult;
         } else {
            double d = Minecraft.playerController().N();
            rayTraceResult = entityLivingBase.W(d, f3);
            Vec3 vec3 = entityLivingBase.k(f3);
            boolean bl2 = false;
            if (d > 3.0) {
               bl2 = true;
            }

            double d2 = f;
            d = f;
            double var24;
            double var25;
            d2 = ForgeVersion.MC_1_20_6.d() ? (var24 = d2 * d2) : (var25 = d2 * d2);
            if (rayTraceResult.isNotNull()) {
               double d3 = Minecraft.playerController().N() + (f - 3.0);
               d2 = entityLivingBase.W(d3, 1.0F).getHitVec().A(vec3);
               if (ForgeVersion.MC_1_20_6.d()) {
                  d2 *= d2;
               } else if (ForgeVersion.MC_1_17.d()) {
                  d2 *= d2;
               }
            }

            Vec3 vec32 = entityLivingBase.J(1.0F);
            Vec3 vec33 = vec3.addVector(vec32.getX() * d, vec32.getY() * d, vec32.getZ() * d);
            double d4 = 1.0;
            AxisAlignedBB axisAlignedBB = entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().c(vec32.r(d)).z(d4, d4, d4);
            EntityRayTraceResult entityRayTraceResult = MouseOverRayTraceUpdater.o(entityLivingBase, vec3, vec33, axisAlignedBB, d2, f2, predicate);
            if (entityRayTraceResult.isNotNull()) {
               Vec3 vec34 = entityRayTraceResult.getHitVec();
               double d5 = vec3.H(vec34);
               if (bl2 && d5 > f * f) {
                  rayTraceResult = BlockRayTraceResult.createMiss(vec34, Direction.i(vec32.getX(), vec32.getY(), vec32.getZ()), BlockPos.P(vec34));
               } else if (d5 < d2 || rayTraceResult.isNull()) {
                  rayTraceResult = entityRayTraceResult;
               }
            }

            return rayTraceResult;
         }
      }
   }

   public static RayTraceResult d(EntityLivingBase entityLivingBase, double d, float f, boolean bl) {
      return U(entityLivingBase, d, f, bl, null);
   }

   public static Vec3d X(Entity entity) {
      return new Vec3d(entity.z(), entity.N() + 1.54, entity.h());
   }

   public static RayTraceResult p(World world, EntityPlayer entityPlayer, boolean bl) {
      float f = entityPlayer.V();
      float f2 = entityPlayer.J();
      double d = entityPlayer.z();
      double d2 = entityPlayer.N() + entityPlayer.X();
      double d3 = entityPlayer.h();
      Vec3 vec3 = Vec3.create(d, d2, d3);
      float f3 = MathUtil.cos(-f2 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f4 = MathUtil.sin(-f2 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f5 = -MathUtil.cos(-f * (float) (Math.PI / 180.0));
      float f6 = MathUtil.sin(-f * (float) (Math.PI / 180.0));
      float f7 = f4 * f5;
      float f8 = f3 * f5;
      double d4 = 5.0;
      Vec3 vec32 = vec3.addVector(f7 * d4, f6 * d4, f8 * d4);
      return world.K(vec3, vec32, bl, !bl, false, entityPlayer);
   }

   public static RayTraceResult F(double d, float f, boolean bl) {
      return U(Minecraft.F(), d, f, bl, null);
   }

   private static RayTraceResult U(RayTraceResult rayTraceResult, Vec3 vec3, double d) {
      Vec3 vec32 = rayTraceResult.getHitVec();
      if (vec32.A(vec3) >= d * d) {
         Direction direction = Direction.i(vec32.getX() - vec3.getX(), vec32.getY() - vec3.getY(), vec32.getZ() - vec3.getZ());
         return BlockRayTraceResult.createMiss(vec32, direction, BlockPos.P(vec32));
      } else {
         return rayTraceResult;
      }
   }

   public static RayTraceResult o() {
      if (ForgeVersion.MC_1_20_6.d()) {
         return y(Minecraft.F(), 3.0F, 0.0F, false, null);
      } else {
         return ForgeVersion.MC_1_16_5.d()
            ? y(Minecraft.F(), Minecraft.playerController().N(), 0.0F, false, null)
            : F(Minecraft.playerController().N(), 0.0F, false);
      }
   }

   public static EntityLivingBase l(EntityLivingBase entityLivingBase, double d, double d2) {
      float f = 1.0F;
      WorldClient worldClient = Minecraft.theWorld();
      if (!entityLivingBase.isNull() && !worldClient.isNull()) {
         float f2 = (float)d;
         Vec3 vec3 = entityLivingBase.O(f);
         double d3 = entityLivingBase.W(f2, f).getHitVec().distanceTo(vec3);
         if (d3 > d2) {
            d3 = d2;
         }

         Vec3 vec32 = entityLivingBase.J(f);
         Vec3 vec33 = vec3.addVector(vec32.getX() * f2, vec32.getY() * f2, vec32.getZ() * f2);
         float f3 = 1.0F;
         List list = worldClient.F(
            entityLivingBase,
            entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
               .addCoord(vec32.getX() * f2, vec32.getY() * f2, vec32.getZ() * f2)
               .expand(f3, f3, f3)
         );
         double d4 = d3;
         EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
         EntityLivingBase entityLivingBase3 = null;

         for (Object e : list) {
            EntityLivingBase entityLivingBase2;
            if (MappedClasses.zm.isInstance(e) && !(entityLivingBase2 = new EntityLivingBase(e)).equals(entityPlayerSP)) {
               boolean bl = entityLivingBase2.isInstance(MappedClasses.Yl);
               if ((!entityLivingBase2.J$src$Z$fdev5g() || bl) && entityLivingBase2.n$src$Z$fx7gig()) {
                  boolean bl2 = bl && (entityLivingBase2.t() != 0.0 || entityLivingBase2.q() != 0.0 || entityLivingBase2.T() != 0.0);
                  double d6 = RotationUtil.s(entityLivingBase, entityLivingBase2);
                  float f4 = (float)Math.min(d6 / 100.0, 1.0);
                  if (bl2) {
                     f4 *= 2.0F;
                  }

                  float f5 = entityLivingBase2.b() + f4;
                  AxisAlignedBB axisAlignedBB = entityLivingBase2.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
                     .expand(f5, f5, f5)
                     .addCoord(entityLivingBase2.t(), entityLivingBase2.q(), entityLivingBase2.T());
                  RayTraceResult rayTraceResult = axisAlignedBB.calculateIntercept(vec3, vec33);
                  if (axisAlignedBB.isVecInside(vec3)) {
                     if (0.0 < d4 || d4 == 0.0) {
                        entityLivingBase3 = entityLivingBase2;
                        d4 = 0.0;
                     }
                  } else {
                     double d5;
                     if (rayTraceResult.isNotNull() && ((d5 = vec3.distanceTo(rayTraceResult.getHitVec())) < d4 || d4 == 0.0)) {
                        if (!entityLivingBase2.equals(entityLivingBase.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12()) || entityLivingBase2.C$src$Z$f9kazx()) {
                           entityLivingBase3 = entityLivingBase2;
                           d4 = d5;
                        } else if (d4 == 0.0) {
                           entityLivingBase3 = entityLivingBase2;
                        }
                     }
                  }
               }
            }
         }

         return d4 < d3 && entityLivingBase3 != null && !entityLivingBase3.isNull() ? entityLivingBase3 : null;
      } else {
         return null;
      }
   }

   private static RayTraceResult m(EntityLivingBase entityLivingBase, float f, float f2, boolean bl, @Nullable Predicate<Entity> predicate) {
      float f3 = 1.0F;
      RayTraceResult rayTraceResult = new RayTraceResult(null);
      if (entityLivingBase.isNotNull()) {
         double d = Minecraft.playerController().N();
         double d2 = f;
         double d3 = Math.max(d, d2);
         double d4 = d3 * d3;
         Vec3 vec33 = entityLivingBase.k(f3);
         rayTraceResult = entityLivingBase.W(d3, f3);
         double d5 = rayTraceResult.getHitVec().A(vec33);
         if (!rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.miss())) {
            d4 = d5;
            d3 = Math.sqrt(d5);
         }

         Vec3 vec3;
         EntityRayTraceResult entityRayTraceResult;
         return (entityRayTraceResult = MouseOverRayTraceUpdater.o(
                     entityLivingBase,
                     vec33,
                     vec33.addVector((vec3 = entityLivingBase.J(f3)).getX() * d3, vec3.getY() * d3, vec3.getZ() * d3),
                     entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().c(vec3.r(d3)).z(1.0, 1.0, 1.0),
                     d4,
                     f2,
                     predicate
                  ))
                  .isNotNull()
               && entityRayTraceResult.getHitVec().A(vec33) < d5
            ? U(entityRayTraceResult, vec33, d2)
            : U(rayTraceResult, vec33, d);
      } else {
         return rayTraceResult;
      }
   }

   private static RayTraceResult b(EntityLivingBase entityLivingBase, float f, float f2, boolean bl, @Nullable Predicate<Entity> predicate) {
      float f3 = 1.0F;
      RayTraceResult rayTraceResult = new RayTraceResult(null);
      if (entityLivingBase.isNotNull()) {
         double d = Minecraft.playerController().N();
         RayTraceResult rayTraceResult2 = l(entityLivingBase, f3, predicate);
         if (rayTraceResult2 != null && rayTraceResult2.isNotNull()) {
            rayTraceResult = rayTraceResult2;
            if (MappedClasses.qF.isInstance(rayTraceResult2.getObject())) {
               rayTraceResult = U(rayTraceResult2, entityLivingBase.k(f3), d);
            }

            if (!rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.miss())) {
               return rayTraceResult;
            }
         }

         double d2 = f;
         double d3 = Math.max(d, d2);
         double d4 = d3 * d3;
         Vec3 vec33 = entityLivingBase.k(f3);
         rayTraceResult = entityLivingBase.W(d3, f3);
         double d5 = rayTraceResult.getHitVec().A(vec33);
         if (!rayTraceResult.getTypeOfHit().equals(RayTraceResult_type.miss())) {
            d4 = d5;
            d3 = Math.sqrt(d5);
         }

         Vec3 vec3;
         EntityRayTraceResult entityRayTraceResult;
         return (entityRayTraceResult = MouseOverRayTraceUpdater.o(
                     entityLivingBase,
                     vec33,
                     vec33.addVector((vec3 = entityLivingBase.J(f3)).getX() * d3, vec3.getY() * d3, vec3.getZ() * d3),
                     entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().c(vec3.r(d3)).z(1.0, 1.0, 1.0),
                     d4,
                     f2,
                     predicate
                  ))
                  .isNotNull()
               && entityRayTraceResult.getHitVec().A(vec33) < d5
            ? U(entityRayTraceResult, vec33, d2)
            : U(rayTraceResult, vec33, d);
      } else {
         return rayTraceResult;
      }
   }

   public static RayTraceResult U(EntityLivingBase entityLivingBase, double d, float f, boolean bl, @Nullable Predicate<Entity> predicate) {
      if (ForgeVersion.MC_1_16_5.d()) {
         return y(entityLivingBase, (float)d, f, bl, predicate);
      } else {
         RayTraceResult rayTraceResult = new RayTraceResult(null);
         float f2 = 1.0F;
         WorldClient worldClient = Minecraft.theWorld();
         PlayerControllerMP playerControllerMP = Minecraft.playerController();
         if (entityLivingBase.isNotNull() && worldClient.isNotNull()) {
            double d2 = playerControllerMP.N();
            rayTraceResult = entityLivingBase.W(d2, f2);
            double d3 = d2;
            Vec3 vec3 = entityLivingBase.O(f2);
            double d4 = d;
            boolean bl2 = false;
            if (!bl) {
               if (playerControllerMP.X()) {
                  d4 = 6.0;
               } else {
                  bl2 = d2 > d;
               }
            }

            double d5 = Math.max(d2, d4);
            if (rayTraceResult != null && rayTraceResult.isNotNull()) {
               d3 = rayTraceResult.getHitVec().distanceTo(vec3);
            }

            Vec3 vec32 = entityLivingBase.J(f2);
            Vec3 vec33 = vec3.addVector(vec32.getX() * d5, vec32.getY() * d5, vec32.getZ() * d5);
            Wrapper wrapper = null;
            Vec3 vec34 = null;
            float f3 = 1.0F;
            List list = worldClient.F(
               entityLivingBase,
               entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
                  .addCoord(vec32.getX() * d5, vec32.getY() * d5, vec32.getZ() * d5)
                  .expand(1.0, 1.0, 1.0)
            );
            double d6 = d3;
            EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();

            for (Object e : list) {
               Entity entity = new Entity(e);
               if (!entity.equals(entityPlayerSP) && (predicate == null || predicate.test(entity)) && entity.n$src$Z$fx7gig()) {
                  float f4 = entity.b() + f;
                  AxisAlignedBB axisAlignedBB = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(f4, f4, f4);
                  RayTraceResult rayTraceResult2 = axisAlignedBB.calculateIntercept(vec3, vec33);
                  if (axisAlignedBB.isVecInside(vec3)) {
                     if (0.0 < d6 || d6 == 0.0) {
                        wrapper = entity;
                        vec34 = rayTraceResult2 != null && !rayTraceResult2.isNull() ? rayTraceResult2.getHitVec() : vec3;
                        d6 = 0.0;
                     }
                  } else {
                     double d7;
                     if (rayTraceResult2 != null && rayTraceResult2.isNotNull() && ((d7 = vec3.distanceTo(rayTraceResult2.getHitVec())) < d6 || d6 == 0.0)) {
                        if (!entity.equals(entityLivingBase.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12()) || entity.C$src$Z$f9kazx()) {
                           wrapper = entity;
                           vec34 = rayTraceResult2.getHitVec();
                           d6 = d7;
                        } else if (d6 == 0.0) {
                           wrapper = entity;
                           vec34 = rayTraceResult2.getHitVec();
                        }
                     }
                  }
               }
            }

            if (wrapper != null && wrapper.isNotNull() && (d6 < d3 || rayTraceResult == null || rayTraceResult.isNull())) {
               rayTraceResult = bl2 && vec3.distanceTo(vec34) > d4
                  ? RayTraceResult.create(RayTraceResult_type.miss(), vec34, new EnumFacing(null), BlockPos.P(vec34))
                  : RayTraceResult.create((Entity)wrapper, vec34);
            }

            return rayTraceResult;
         } else {
            return rayTraceResult;
         }
      }
   }
}
