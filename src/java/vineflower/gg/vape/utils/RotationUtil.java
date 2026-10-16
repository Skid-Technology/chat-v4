package gg.vape.utils;

import gg.vape.Vape;
import gg.vape.combat.AttackStrengthTracker;
import gg.vape.config.ClientSettings;
import gg.vape.mapping.MappedClasses;
import gg.vape.rotation.RotationManager;
import gg.vape.utils.datas.BlockCoordinate;
import gg.vape.utils.datas.DirectionalPosition;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.AttributeInstance;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Block;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.DamageSource;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumCreatureAttribute;
import gg.vape.wrapper.impl.EnumFacing;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GameSettings;
import gg.vape.wrapper.impl.GuiPlayerTabOverlayBridge;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.KeyBinding;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.NetHandlerPlayClientImpl;
import gg.vape.wrapper.impl.PlayerInfo;
import gg.vape.wrapper.impl.PotionRegistry;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.Vec3;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public class RotationUtil {
   private static final boolean t = ForgeVersion.MC_1_7_10.Y();

   public static boolean x() {
      GameSettings gameSettings = Minecraft.gameSettings();
      return gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg().isKeyDown()
         || gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3().isKeyDown()
         || gameSettings.Y().isKeyDown()
         || gameSettings.s().isKeyDown();
   }

   public static ItemStack Z() {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      if (entityPlayerSP.isNull()) {
         return new ItemStack(null);
      } else {
         return ForgeVersion.MC_1_20_6.d()
            ? entityPlayerSP.F$src$Lgg_vape_wrapper_impl_Container_$152y6lm().getCarried()
            : entityPlayerSP.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().A();
      }
   }

   public static Vec3d M(Vec3 vec3, AxisAlignedBB axisAlignedBB, double d, double d2, double d3) {
      double d4 = axisAlignedBB.getMinX() + d;
      double d5 = axisAlignedBB.getMinY() + d2;
      double d6 = axisAlignedBB.getMinZ() + d3;
      double d7 = axisAlignedBB.getMaxX() + d;
      double d8 = axisAlignedBB.getMaxY() + d2;
      double d9 = axisAlignedBB.getMaxZ() + d3;
      double d10 = vec3.getX();
      double d11 = vec3.getY();
      double d12 = vec3.getZ();
      double d13 = k(d10, d4, d7);
      double d14 = k(d11, d5, d8);
      double d15 = k(d12, d6, d9);
      if (d13 == d10) {
         d13 = d10 + 0.01;
      }

      if (d15 == d12) {
         d15 = d12 + 0.01;
      }

      return new Vec3d(d13, d14, d15);
   }

   public static boolean p(double d, double d2, double d3, double d4, double d5) {
      double d6 = 0.0;
      double d7 = d4 - d;
      double d8 = d5 - d2;
      if (d8 > 0.0 && d7 > 0.0) {
         d6 = Math.toDegrees(-Math.atan(d7 / d8));
      } else if (d8 > 0.0 && d7 < 0.0) {
         d6 = Math.toDegrees(-Math.atan(d7 / d8));
      } else if (d8 < 0.0 && d7 > 0.0) {
         d6 = -90.0 + Math.toDegrees(Math.atan(d8 / d7));
      } else if (d8 < 0.0 && d7 < 0.0) {
         d6 = 90.0 + Math.toDegrees(Math.atan(d8 / d7));
      }

      int n = (int)MathUtil.wrapAngleTo180((d6 - d3) % 360.0);
      return n < 0;
   }

   public static float k(double d, double d2, double d3, double d4) {
      double d5 = d3 - d;
      double d6 = d4 - d2;
      float f = (float)Math.toDegrees(-Math.atan(d5 / d6));
      if (d6 < 0.0 && d5 < 0.0) {
         f = (float)(90.0 + Math.toDegrees(Math.atan(d6 / d5)));
      } else if (d6 < 0.0 && d5 > 0.0) {
         f = (float)(-90.0 + Math.toDegrees(Math.atan(d6 / d5)));
      }

      return f;
   }

   public static EntityOtherPlayerMP m(Entity entity, double d, double d2, boolean bl) {
      for (Object e : Minecraft.theWorld().X()) {
         EntityOtherPlayerMP entityOtherPlayerMP;
         if (MappedClasses.lG.isInstance(e)
            && !(entityOtherPlayerMP = new EntityOtherPlayerMP(e)).equals(entity)
            && (!bl || !Vape.INSTANCE.getFriendManager().isFriend(entityOtherPlayerMP))
            && !(entityOtherPlayerMP.getDistanceToEntity(entity) >= d)
            && a(entity, entityOtherPlayerMP) < d2) {
            return entityOtherPlayerMP;
         }
      }

      return null;
   }

   private static int D(ItemStack[] itemStackArray, DamageSource damageSource) {
      if (ForgeVersion.MC_1_16_5.d()) {
         return EnchantmentHelper.B(itemStackArray, damageSource);
      } else {
         EnchantmentHelper.getDamageModifier().setDamageModifier(0);
         EnchantmentHelper.getDamageModifier().setSource(damageSource);
         EnchantmentHelper.J(EnchantmentHelper.getDamageModifier(), itemStackArray);
         if (EnchantmentHelper.getDamageModifier().getDamageModifierValue() > 25) {
            EnchantmentHelper.getDamageModifier().setDamageModifier(25);
         } else if (EnchantmentHelper.getDamageModifier().getDamageModifierValue() < 0) {
            EnchantmentHelper.getDamageModifier().setDamageModifier(0);
         }

         return (EnchantmentHelper.getDamageModifier().getDamageModifierValue() + 1 >> 1)
            + ((EnchantmentHelper.getDamageModifier().getDamageModifierValue() >> 1) + 1) / 2;
      }
   }

   public static float y(EntityPlayer entityPlayer, DamageSource damageSource, float f, boolean bl, boolean bl2) {
      float f2 = m(entityPlayer, f);
      f2 = u(entityPlayer, f2, damageSource);
      if (bl2) {
         f2 = Math.max(f2 - entityPlayer.p(), 0.0F);
      }

      return f2;
   }

   public static float j(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2) {
      float f = b(entityLivingBase);
      f = m(entityLivingBase2, f);
      return j(entityLivingBase2, f);
   }

   public static float x(EntityLivingBase entityLivingBase) {
      return entityLivingBase.isInstance(MappedClasses.Yl)
         ? AttackStrengthTracker.INSTANCE.getEstimatedHealth(new EntityPlayer(entityLivingBase))
         : entityLivingBase.w$src$F$15l9epb();
   }

   public static boolean H(Entity entity) {
      return P(entity, 6.0, 60.0, true);
   }

   public static float E(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2) {
      float f = g(entityLivingBase);
      float f2 = g(entityLivingBase2);
      return f - f2;
   }

   public static float c() {
      float f = Minecraft.thePlayer().J() % 360.0F;
      return f < 0.0F ? 360.0F + f : f;
   }

   public static float b(EntityLivingBase entityLivingBase) {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      ItemStack itemStack = entityLivingBase.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt();
      AttributeInstance attributeInstance = null;
      attributeInstance = ForgeVersion.MC_1_21_4.d() ? entityPlayerSP.t(MonsterAttributesBridge.V()) : entityPlayerSP.h(MonsterAttributesBridge.t());
      if (attributeInstance.isNull()) {
         return 0.0F;
      } else {
         float f = (float)attributeInstance.W();
         float f2 = 0.0F;
         f2 += EnchantmentHelper.C(itemStack, EnumCreatureAttribute.undefined());
         if (itemStack.isNotNull()) {
            f2 += ItemStackScoreUtil.a$src$F$2aw1mh(itemStack);
         }

         if (f > 0.0F || f2 > 0.0F) {
            f += f2;
         }

         return f;
      }
   }

   public static void w() {
      GameSettings gameSettings = Minecraft.gameSettings();
      KeyBinding keyBinding = gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg();
      KeyBinding keyBinding2 = gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3();
      KeyBinding keyBinding3 = gameSettings.Y();
      KeyBinding keyBinding4 = gameSettings.s();
      KeyBinding keyBinding5 = gameSettings.r();
      KeyBinding keyBinding6 = gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0();
      KeyBinding keyBinding7 = gameSettings.O();
      boolean bl = ClientSettings.isPhysicalKeyDown(gameSettings.x$src$Lgg_vape_wrapper_impl_KeyBinding_$1cf7isg());
      boolean bl2 = ClientSettings.isPhysicalKeyDown(gameSettings.g$src$Lgg_vape_wrapper_impl_KeyBinding_$qqn5n3());
      boolean bl3 = ClientSettings.isPhysicalKeyDown(gameSettings.Y());
      boolean bl4 = ClientSettings.isPhysicalKeyDown(gameSettings.s());
      boolean bl5 = ClientSettings.isPhysicalKeyDown(gameSettings.r());
      boolean bl6 = ClientSettings.isPhysicalKeyDown(gameSettings.d$src$Lgg_vape_wrapper_impl_KeyBinding_$adn2z0());
      boolean bl7 = ClientSettings.isPhysicalKeyDown(gameSettings.O());
      KeyBinding.setKeyBindState(keyBinding, bl);
      if (bl) {
         KeyBinding.onTick(keyBinding);
      }

      KeyBinding.setKeyBindState(keyBinding2, bl2);
      if (bl2) {
         KeyBinding.onTick(keyBinding2);
      }

      KeyBinding.setKeyBindState(keyBinding3, bl3);
      if (bl3) {
         KeyBinding.onTick(keyBinding3);
      }

      KeyBinding.setKeyBindState(keyBinding4, bl4);
      if (bl4) {
         KeyBinding.onTick(keyBinding4);
      }

      KeyBinding.setKeyBindState(keyBinding5, bl5);
      if (bl5) {
         KeyBinding.onTick(keyBinding5);
      }

      KeyBinding.setKeyBindState(keyBinding6, bl6);
      if (bl6) {
         KeyBinding.onTick(keyBinding6);
      }

      KeyBinding.setKeyBindState(keyBinding7, bl7);
      if (bl7) {
         KeyBinding.onTick(keyBinding7);
      }
   }

   public static boolean g(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2, double d) {
      return x(entityLivingBase, entityLivingBase2, 6.0, d, 360.0);
   }

   public static double Z(double d, double d2, double d3, double d4, double d5, double d6) {
      double d7 = d - d4;
      double d8 = d3 - d6;
      double d9 = d2 - d5;
      if (ForgeVersion.MC_1_7_10.Y()) {
         d9 = d2 + 1.6 - d5;
      }

      double d10 = MathUtil.sqrt(d7 * d7 + d8 * d8);
      return Math.atan2(d9, d10) * 180.0 / Math.PI;
   }

   public static boolean k(EntityLivingBase entityLivingBase) {
      if (entityLivingBase == null) {
         return false;
      } else if (MappedClasses.Yl.isInstance(entityLivingBase)) {
         EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
         boolean bl = false;

         for (Object object : entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i()) {
            ItemStack itemStack = new ItemStack(object);
            if (!itemStack.isNull()) {
               bl = true;
            }
         }

         return entityLivingBase.J$src$Z$fdev5g() && entityLivingBase.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt().isNull() && !bl;
      } else {
         return entityLivingBase.J$src$Z$fdev5g() && entityLivingBase.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt().isNull();
      }
   }

   public static int V(Entity entity) {
      double d2 = entity.t();
      double d3 = entity.T();
      if (d2 > d3) {
         if (d2 > 0.0) {
            return 2;
         }

         if (d2 < 0.0) {
            return 4;
         }
      } else {
         if (d3 > 0.0) {
            return 3;
         }

         if (d3 < 0.0) {
            return 1;
         }
      }

      return 0;
   }

   public static int N(EntityPlayer entityPlayer) {
      int n = 0;
      if (ClientSettings.IS_LEGACY_1_7) {
         NetHandlerPlayClientImpl netHandlerPlayClientImpl = Minecraft.thePlayer().sendQueue();

         for (Object e : GuiPlayerTabOverlayBridge.O().sortedCopy(netHandlerPlayClientImpl.getPlayerInfoMap())) {
            PlayerInfo playerInfo = new PlayerInfo(e);
            if (playerInfo.v().isNotNull()
               && (
                  entityPlayer.c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937().equals(playerInfo.v())
                     || entityPlayer.getName().equals(playerInfo.v().getName())
               )) {
               n = Math.max(playerInfo.z(), 0);
               break;
            }
         }
      }

      return n;
   }

   public static int a(Entity entity, Entity entity2) {
      double d = 0.0;
      double d2 = entity2.z() - entity.z();
      double d3 = entity2.h() - entity.h();
      if (d3 > 0.0 && d2 > 0.0) {
         d = Math.toDegrees(-Math.atan(d2 / d3));
      } else if (d3 > 0.0 && d2 < 0.0) {
         d = Math.toDegrees(-Math.atan(d2 / d3));
      } else if (d3 < 0.0 && d2 > 0.0) {
         d = -90.0 + Math.toDegrees(Math.atan(d3 / d2));
      } else if (d3 < 0.0 && d2 < 0.0) {
         d = 90.0 + Math.toDegrees(Math.atan(d3 / d2));
      }

      int n = (int)(Math.abs(d - entity.J()) % 360.0);
      return n > 180 ? 360 - n : n;
   }

   public static double T(Entity entity, double d, double d2) {
      return C(entity.z(), entity.h(), entity.J(), d, d2);
   }

   public static Vec3d T(EntityLivingBase entityLivingBase, AxisAlignedBB axisAlignedBB, double d, double d2, double d3) {
      double d4 = axisAlignedBB.getMinX() + d;
      double d5 = axisAlignedBB.getMinY() + d2;
      double d6 = axisAlignedBB.getMinZ() + d3;
      double d7 = axisAlignedBB.getMaxX() + d;
      double d8 = axisAlignedBB.getMaxY() + d2;
      double d9 = axisAlignedBB.getMaxZ() + d3;
      double d10 = entityLivingBase.z();
      double d11 = entityLivingBase.O(0.0F).getY();
      double d12 = entityLivingBase.h();
      double d13 = k(d10, d4, d7);
      double d14 = k(d11, d5, d8);
      double d15 = k(d12, d6, d9);
      if (d13 == d10) {
         d13 = d10 + 0.01;
      }

      if (d15 == d12) {
         d15 = d12 + 0.01;
      }

      return new Vec3d(d13, d14, d15);
   }

   public static boolean C(Entity entity) {
      if (!(entity instanceof EntityLivingBase)) {
         return entity.M$src$Z$ff28xj();
      } else {
         EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
         return entity.M$src$Z$ff28xj() || entityLivingBase.w$src$F$15l9epb() <= 0.0F;
      }
   }

   public static EntityLivingBase K(double d, float f, float f2, float f3) {
      if (Minecraft.F().getObject() != null && Minecraft.theWorld().getObject() != null) {
         EntityLivingBase entityLivingBase = Minecraft.F();
         EntityOtherPlayerMP entityOtherPlayerMP = EntityOtherPlayerMP.create(
            entityLivingBase.getWorld(), Minecraft.thePlayer().c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937()
         );
         entityOtherPlayerMP.D(f2);
         entityOtherPlayerMP.l(f3);
         entityOtherPlayerMP.H(f2);
         entityOtherPlayerMP.C(f3);
         return b(d, f, entityLivingBase, entityOtherPlayerMP);
      } else {
         return null;
      }
   }

   public static double M(Entity entity, Entity entity2) {
      double d = entity.getDistanceToEntity(entity2);
      double d2 = Math.cos(Math.toRadians(entity.J() + 90.0F)) * d;
      double d3 = Math.sin(Math.toRadians(entity.J() + 90.0F)) * d;
      double d4 = entity.z() + d2;
      double d5 = entity.h() + d3;
      return y(d4, 0.0, d5, entity2.z(), 0.0, entity2.h());
   }

   public static float m(EntityLivingBase entityLivingBase, float f) {
      int n = (int)(25.0F - g(entityLivingBase));
      float f2 = f * n;
      return f2 / 25.0F;
   }

   public static boolean m(Entity entity) {
      return !entity.isInstance(MappedClasses.zm) ? false : !W(entity) && !entity.isInstance(MappedClasses.Yl);
   }

   public static double h(Entity entity, double d, double d2, double d3) {
      double d4 = entity.z() - d;
      double d5 = entity.h() - d3;
      double d6 = entity.N() - d2;
      if (ForgeVersion.MC_1_7_10.Y()) {
         d6 = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY() + 1.6 - d2;
      }

      double d7 = MathUtil.sqrt(d4 * d4 + d5 * d5);
      return Math.atan2(d6, d7) * 180.0 / Math.PI;
   }

   public static double b(double[] dArray, double[] dArray2) {
      return y(dArray[0], dArray[1], dArray[2], dArray2[0], dArray2[1], dArray2[2]);
   }

   public static float G(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2) {
      float f = b(entityLivingBase);
      f = m(entityLivingBase2, f);
      f = j(entityLivingBase2, f);
      return (int)Math.ceil(entityLivingBase2.w$src$F$15l9epb() / f);
   }

   public static double g(Entity entity, double d, double d2, double d3) {
      double d4 = d - entity.z();
      double d5 = d2 - entity.N();
      if (ForgeVersion.MC_1_7_10.Y()) {
         d5 = d2 - 0.3 - entity.N();
      }

      double d6 = d3 - entity.h();
      double d7 = MathUtil.sqrt(d4 * d4 + d6 * d6);
      float f = (float)(-(Math.atan2(d5, d7) * 180.0 / Math.PI));
      float f2 = (float)MathUtil.wrapAngleTo180((double)(entity.V() - f));
      return f2;
   }

   public static int N(Entity entity) {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      double d = 0.0;
      double d2 = entity.z() - entityPlayerSP.z();
      double d3 = entity.h() - entityPlayerSP.h();
      if (d3 > 0.0 && d2 > 0.0) {
         d = Math.toDegrees(-Math.atan(d2 / d3));
      } else if (d3 > 0.0 && d2 < 0.0) {
         d = Math.toDegrees(-Math.atan(d2 / d3));
      } else if (d3 < 0.0 && d2 > 0.0) {
         d = -90.0 + Math.toDegrees(Math.atan(d3 / d2));
      } else if (d3 < 0.0 && d2 < 0.0) {
         d = 90.0 + Math.toDegrees(Math.atan(d3 / d2));
      }

      float f = RotationManager.INSTANCE.getManagedYaw();
      int n = (int)(Math.abs(d - f) % 360.0);
      return n > 180 ? 360 - n : n;
   }

   public static double V(double d, double d2, double d3, double d4) {
      double d5 = d - d3;
      double d6 = d2 - d4;
      return Math.sqrt(d5 * d5 + d6 * d6);
   }

   public static boolean S(Entity entity, Entity entity2) {
      double d = entity2.z() - entity.z();
      double d2 = entity2.N() - entity.N() + entity2.Y() / 2.0F + 0.2;
      if (ForgeVersion.MC_1_7_10.Y()) {
         d2 = entity2.N() - 0.3 - entity.N();
      }

      double d3 = entity2.h() - entity.h();
      double d4 = MathUtil.sqrt(d * d + d3 * d3);
      float f = (float)(-(Math.atan2(d2, d4) * 180.0 / Math.PI));
      float f2 = (float)MathUtil.wrapAngleTo180((double)(entity.V() - f));
      return f2 < 0.0F;
   }

   public static EntityLivingBase L(Entity entity, double d, double d2, boolean bl) {
      for (Object e : Minecraft.theWorld().z()) {
         EntityLivingBase entityLivingBase;
         if (MappedClasses.zm.isInstance(e)
            && !(entityLivingBase = new EntityLivingBase(e)).equals(entity)
            && !entityLivingBase.M$src$Z$ff28xj()
            && !(entityLivingBase.w$src$F$15l9epb() <= 0.0F)
            && (!bl || !Vape.INSTANCE.getFriendManager().isFriend(entityLivingBase))
            && !(entityLivingBase.getDistanceToEntity(entity) >= d)
            && a(entity, entityLivingBase) < d2) {
            return entityLivingBase;
         }
      }

      return null;
   }

   public static int H(Entity entity, double d, double d2, double d3) {
      double d4 = d - entity.z();
      double d5 = d2 - entity.N();
      if (ForgeVersion.MC_1_7_10.Y()) {
         d5 = d2 - entity.N();
      }

      double d6 = d3 - entity.h();
      double d7 = MathUtil.sqrt(d4 * d4 + d6 * d6);
      float f = (float)(-(Math.atan2(d5, d7) * 180.0 / Math.PI));
      float f2 = (float)MathUtil.wrapAngleTo180((double)(entity.V() - f));
      return (int)f2;
   }

   public static int D(Entity entity, int n) {
      return a(entity, n, true);
   }

   public static boolean n(EntityOtherPlayerMP entityOtherPlayerMP) {
      if (entityOtherPlayerMP == null || entityOtherPlayerMP.isNull()) {
         return false;
      } else if (!entityOtherPlayerMP.isBlocking()) {
         return false;
      } else if (ForgeVersion.MC_1_12_2.v()) {
         return true;
      } else {
         EnumHand enumHand = q(entityOtherPlayerMP);
         return enumHand != null;
      }
   }

   public static float j(EntityLivingBase entityLivingBase, float f) {
      return u(entityLivingBase, f, DamageSource.C(Minecraft.thePlayer()));
   }

   public static double N(double d, double d2, double d3, double d4, double d5) {
      double d6 = 0.0;
      float f = (float)(d4 - d);
      float f2 = (float)(d5 - d2);
      if (f2 > 0.0 && f > 0.0) {
         d6 = Math.toDegrees(-FastAtanMath.atanApproximation(f / f2));
      } else if (f2 > 0.0 && f < 0.0) {
         d6 = Math.toDegrees(-FastAtanMath.atanApproximation(f / f2));
      } else if (f2 < 0.0 && f > 0.0) {
         d6 = -90.0 + Math.toDegrees(FastAtanMath.atanApproximation(f2 / f));
      } else if (f2 < 0.0 && f < 0.0) {
         d6 = 90.0 + Math.toDegrees(FastAtanMath.atanApproximation(f2 / f));
      }

      double d7 = Math.abs(d6 - d3) % 360.0;
      return d7 > 180.0 ? 360.0 - d7 : d7;
   }

   public static float g(EntityLivingBase entityLivingBase) {
      float f = 0.0F;
      if (entityLivingBase.isInstance(MappedClasses.Yl)) {
         EntityPlayer entityPlayer = new EntityPlayer(entityLivingBase);

         for (Object object : entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i()) {
            ItemStack itemStack = new ItemStack(object);
            if (itemStack.isNotNull() && ItemStackScoreUtil.R(itemStack.getItem())) {
               double d = ItemStackScoreUtil.P(itemStack);
               f += (float)d;
            }
         }
      }

      return f;
   }

   public static float f(EntityOtherPlayerMP entityOtherPlayerMP, EnumHand enumHand) {
      if (entityOtherPlayerMP != null && !entityOtherPlayerMP.isNull()) {
         boolean isBlockingFallback = entityOtherPlayerMP.isBlocking() || entityOtherPlayerMP.l$src$Z$1io4duf();
         ItemStack itemStack;
         if (enumHand != null && (itemStack = entityOtherPlayerMP.i(enumHand)).isNotNull()) {
            float f = entityOtherPlayerMP.d(enumHand);
            if (f <= 0.0F && !isBlockingFallback) {
               return 0.0F;
            } else {
               int n = itemStack.getItem().I(itemStack, entityOtherPlayerMP);
               if (f > 0.0F) {
                  float f2 = n - f;
                  return Math.max(f2, 5.0F);
               } else {
                  return 5.0F;
               }
            }
         } else {
            return 0.0F;
         }
      } else {
         return 0.0F;
      }
   }

   public static float y(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2) {
      float f = Math.max(j(entityLivingBase, entityLivingBase2), 1.0F);
      float f2 = Math.max(j(entityLivingBase2, entityLivingBase), 1.0F);
      return f - f2;
   }

   public static float u(EntityLivingBase entityLivingBase, float f, DamageSource damageSource) {
      if (entityLivingBase.i(PotionRegistry.P)) {
         int n = (entityLivingBase.b(PotionRegistry.P).L() + 1) * 5;
         int n2 = 25 - n;
         float f2 = f * n2;
         f = f2 / 25.0F;
      }

      if (f <= 0.0F) {
         return 0.0F;
      } else {
         if (entityLivingBase.isInstance(MappedClasses.Yl)) {
            EntityPlayer entityPlayer = new EntityPlayer(entityLivingBase);
            ArrayList<ItemStack> arrayList = new ArrayList<>();

            for (Object object : entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i()) {
               arrayList.add(new ItemStack(object));
            }

            int n = D(arrayList.toArray(new ItemStack[0]), damageSource);
            if (n > 20) {
               n = 20;
            }

            if (n > 0) {
               int n3 = 25 - n;
               float f3 = f * n3;
               f = f3 / 25.0F;
            }
         }

         return f;
      }
   }

   public static double L(Entity entity) {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      double d = entityPlayerSP.getDistanceToEntity(entity);
      double d2 = RotationManager.INSTANCE.getManagedYaw();
      double d3 = Math.cos(Math.toRadians(d2 + 90.0)) * d;
      double d4 = Math.sin(Math.toRadians(d2 + 90.0)) * d;
      double d5 = entityPlayerSP.z() + d3;
      double d6 = entityPlayerSP.h() + d4;
      return y(d5, 0.0, d6, entity.z(), 0.0, entity.h());
   }

   public static boolean o(Entity entity, EntityLivingBase entityLivingBase, double d, double d2, boolean bl) {
      if (bl && Vape.INSTANCE.getFriendManager().isFriend(entityLivingBase)) {
         return false;
      } else {
         return entity.getDistanceToEntity(entityLivingBase) >= d ? false : a(entityLivingBase, entity) < d2;
      }
   }

   public static boolean o(Entity entity, double d, double d2, boolean bl) {
      for (Object e : Minecraft.theWorld().X()) {
         EntityOtherPlayerMP entityOtherPlayerMP;
         if (MappedClasses.lG.isInstance(e)
            && !(entityOtherPlayerMP = new EntityOtherPlayerMP(e)).equals(entity)
            && !entityOtherPlayerMP.M$src$Z$ff28xj()
            && !(entityOtherPlayerMP.w$src$F$15l9epb() <= 0.0F)
            && (!bl || !Vape.INSTANCE.getFriendManager().isFriend(entityOtherPlayerMP))
            && !(entityOtherPlayerMP.getDistanceToEntity(entity) >= d)
            && a(entity, entityOtherPlayerMP) < d2) {
            return true;
         }
      }

      return false;
   }

   public static BlockCoordinate x(AxisAlignedBB axisAlignedBB) {
      int n = MathUtil.floor(axisAlignedBB.getMinX());
      int n2 = MathUtil.floor(axisAlignedBB.getMaxX() + 1.0);
      int n3 = MathUtil.floor(axisAlignedBB.getMinY());
      int n4 = MathUtil.floor(axisAlignedBB.getMaxY() + 1.0);
      int n5 = MathUtil.floor(axisAlignedBB.getMinZ());
      int n6 = MathUtil.floor(axisAlignedBB.getMaxZ() + 1.0);

      for (int i = n4; i > n3; i--) {
         for (int j = n; j < n2; j++) {
            for (int k = n5; k < n6; k++) {
               Block block = Minecraft.theWorld().getBlockByPos(j, i, k);
               if (BlockUtil.e(block)) {
                  return new BlockCoordinate(j, i, k);
               }
            }
         }
      }

      return null;
   }

   public static boolean b$src$Z$reqs95(EntityLivingBase entityLivingBase) {
      if (entityLivingBase.isNull()) {
         return false;
      } else if (!entityLivingBase.isInstance(MappedClasses.Yl)) {
         return false;
      } else {
         EntityPlayer entityPlayer = new EntityPlayer(entityLivingBase);
         boolean bl = false;

         for (Object object : entityPlayer.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().i()) {
            if (object != null) {
               if (ForgeVersion.MC_1_12_2.d()) {
                  ItemStack itemStack = new ItemStack(object);
                  if (itemStack.getItem().P() != 0) {
                     bl = true;
                  }
               } else {
                  bl = true;
               }
            }
         }

         return entityLivingBase.B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt().isNull() && !bl;
      }
   }

   public static int R(Entity entity, Entity entity2, int n, int n2) {
      double d = entity.z();
      double d2 = entity.h();
      double d3 = entity.f();
      double d4 = entity.R();
      double d5 = entity2.z();
      double d6 = entity2.h();
      if (d3 - d == 0.0 && d4 - d2 == 0.0) {
         return -11;
      } else {
         double d7 = Math.abs((d3 - d) * (d2 - d6) - (d - d5) * (d4 - d2)) / Math.sqrt(Math.pow(d3 - d, 2.0) + Math.pow(d4 - d2, 2.0));
         double d8 = Math.sqrt(Math.pow(d - d5, 2.0) + Math.pow(d2 - d6, 2.0));
         double d9 = Math.sqrt(Math.pow(d3 - d5, 2.0) + Math.pow(d4 - d6, 2.0));
         return d9 > d8 && d7 < n && d8 < n2 ? (int)d8 : -99;
      }
   }

   public static boolean p(EnumFacing enumFacing, BlockCoordinate blockCoordinate) {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      BlockPos blockPos = BlockPos.create(
         MathUtil.floor(entityPlayerSP.z() - entityPlayerSP.t() * 1.0),
         MathUtil.floor(entityPlayerSP.N() - entityPlayerSP.q() * 1.0),
         MathUtil.floor(entityPlayerSP.h() - entityPlayerSP.T() * 1.0)
      );
      if (enumFacing.h().equals("north")) {
         return blockPos.getZ() > blockCoordinate.A();
      } else if (enumFacing.h().equals("south")) {
         return blockPos.getZ() < blockCoordinate.A();
      } else if (enumFacing.h().equals("west")) {
         return blockPos.getX() > blockCoordinate.B();
      } else {
         return enumFacing.h().equals("east") ? blockPos.getX() < blockCoordinate.B() : false;
      }
   }

   public static boolean x(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2, double d, double d2, double d3) {
      boolean bl = entityLivingBase2.M$src$Z$ff28xj()
         || entityLivingBase2.getName().equals("")
         || entityLivingBase.getDistanceToEntity(entityLivingBase2) > d
         || !entityLivingBase.canEntityBeSeen(entityLivingBase2);
      if (bl) {
         return false;
      } else {
         int n = a(entityLivingBase2, entityLivingBase);
         int n2 = P(entityLivingBase2, entityLivingBase);
         return n < d2 && n2 < d3;
      }
   }

   public static EntityLivingBase b(double d, float f, EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2) {
      Wrapper wrapper = null;
      if (Minecraft.F().getObject() != null && Minecraft.theWorld().getObject() != null) {
         Vec3 vec3 = entityLivingBase.O(0.0F);
         Vec3 vec32 = entityLivingBase2.J(0.0F);
         Vec3 vec33 = vec3.addVector(vec32.getX() * d, vec32.getY() * d, vec32.getZ() * d);
         float f2 = 1.0F;
         List list = Minecraft.theWorld()
            .F(
               entityLivingBase,
               entityLivingBase.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl()
                  .addCoord(vec32.getX() * d, vec32.getY() * d, vec32.getZ() * d)
                  .expand(f2, f2, f2)
            );
         double d4 = d;

         for (int i = 0; i < list.size(); i++) {
            Entity entity = new Entity(list.get(i));
            if (entity.n$src$Z$fx7gig()) {
               float f3 = entity.b() + f;
               AxisAlignedBB axisAlignedBB = entity.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(f3, f3, f3);
               RayTraceResult rayTraceResult = axisAlignedBB.calculateIntercept(vec3, vec33);
               if (axisAlignedBB.isVecInside(vec3)) {
                  if (0.0 < d4 || d4 == 0.0) {
                     wrapper = entity;
                     d4 = 0.0;
                  }
               } else {
                  double d5;
                  if (rayTraceResult.getObject() != null && ((d5 = vec3.distanceTo(rayTraceResult.getHitVec())) < d4 || d4 == 0.0)) {
                     if (entity.getObject() != entityLivingBase.S$src$Lgg_vape_wrapper_impl_Entity_$dgzs12().getObject() || entity.C$src$Z$f9kazx()) {
                        wrapper = entity;
                        d4 = d5;
                     } else if (d4 == 0.0) {
                        wrapper = entity;
                     }
                  }
               }
            }
         }

         EntityLivingBase entityLivingBase3;
         if (wrapper != null
            && (d4 < d || Minecraft.p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0().getObject() == null)
            && Minecraft.thePlayer().canEntityBeSeen((Entity)wrapper)
            && MappedClasses.zm.isInstance(wrapper.getObject())
            && !(entityLivingBase3 = new EntityLivingBase(wrapper.getObject())).M$src$Z$ff28xj()) {
            return entityLivingBase3;
         }
      }

      return null;
   }

   public static int a(Entity entity, int n, boolean bl) {
      int n2 = 0;

      for (Object e : Minecraft.theWorld().X()) {
         if (MappedClasses.lG.isInstance(e)) {
            EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP(e);
            if ((!bl || !Vape.INSTANCE.getFriendManager().isFriend(entityOtherPlayerMP)) && entityOtherPlayerMP.getDistanceToEntity(entity) <= n) {
               n2++;
            }
         }
      }

      return n2;
   }

   public static boolean d(Entity entity) {
      return entity.t() == 0.0 && entity.T() == 0.0;
   }

   public static double k(double d, double d2, double d3) {
      if (d < d3 && d > d2) {
         return d;
      } else if (d > d3) {
         return d3;
      } else {
         return d < d2 ? d2 : d;
      }
   }

   public static float S(double d, double d2, double d3, double d4) {
      double d5 = d3 - d;
      double d6 = d4 - d2;
      float f = (float)Math.toDegrees(-FastAtanMath.atanApproximation((float)(d5 / d6)));
      if (d6 < 0.0 && d5 < 0.0) {
         f = (float)(90.0 + Math.toDegrees(FastAtanMath.atanApproximation((float)(d6 / d5))));
      } else if (d6 < 0.0 && d5 > 0.0) {
         f = (float)(-90.0 + Math.toDegrees(FastAtanMath.atanApproximation((float)(d6 / d5))));
      }

      return f;
   }

   public static boolean h$src$Z$kozqzr(Entity entity, double d, double d2, double d3) {
      double d4 = d - entity.z();
      double d5 = d2 - entity.N();
      if (ForgeVersion.MC_1_7_10.Y()) {
         d5 = d2 - 0.3 - entity.N();
      }

      double d6 = d3 - entity.h();
      double d7 = MathUtil.sqrt(d4 * d4 + d6 * d6);
      float f = (float)(-(Math.atan2(d5, d7) * 180.0 / Math.PI));
      float f2 = (float)MathUtil.wrapAngleTo180((double)(entity.V() - f));
      return (int)f2 < 0;
   }

   public static DirectionalPosition E(AxisAlignedBB axisAlignedBB) {
      int n = MathUtil.floor(axisAlignedBB.getMinX());
      int n2 = MathUtil.floor(axisAlignedBB.getMaxX() + 1.0);
      int n3 = MathUtil.floor(axisAlignedBB.getMinY());
      int n4 = MathUtil.floor(axisAlignedBB.getMaxY() + 1.0);
      int n5 = MathUtil.floor(axisAlignedBB.getMinZ());
      int n6 = MathUtil.floor(axisAlignedBB.getMaxZ() + 1.0);

      for (int i = n4; i > n3; i--) {
         for (int j = n; j < n2; j++) {
            for (int k = n5; k < n6; k++) {
               Block block = Minecraft.theWorld().getBlockByPos(j, i, k);
               if (BlockUtil.e(block)) {
                  return new DirectionalPosition(j, i, k, 0);
               }
            }
         }
      }

      return null;
   }

   public static boolean k(Entity entity, double d, double d2) {
      return p(entity.z(), entity.h(), entity.J(), d, d2);
   }

   public static boolean u(Entity entity) {
      double d = entity.N() - entity.W();
      boolean bl = d < 0.0;
      return bl && entity.M$src$F$ff28gb() >= 1.5;
   }

   public static boolean F(Entity entity) {
      return o(entity, 6.0, 60.0, true);
   }

   public static Double[] y(Entity entity, double d, double d2) {
      double d3 = Math.toRadians(d);
      double d4 = entity.z();
      double d5 = entity.h();
      double d6 = d4 - d2 * Math.sin(d3);
      double d7 = d5 + d2 * Math.cos(d3);
      return new Double[]{d6, d7};
   }

   public static EntityLivingBase u(double d, double d2) {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();

      for (Object e : Minecraft.theWorld().z()) {
         EntityLivingBase entityLivingBase;
         if (MappedClasses.zm.isInstance(e)
            && (entityLivingBase = new EntityLivingBase(e)).isInstance(MappedClasses.lG)
            && !entityLivingBase.M$src$Z$ff28xj()
            && !(entityLivingBase.w$src$F$15l9epb() <= 0.0F)
            && !(entityLivingBase.getDistanceToEntity(entityPlayerSP) >= d)
            && Vape.INSTANCE.getClientSettings().isValidTarget(entityLivingBase, false)
            && N(entityLivingBase) < d2) {
            return entityLivingBase;
         }
      }

      return null;
   }

   public static boolean isShield(@Nullable ItemStack itemStack) {
      if (itemStack != null && !itemStack.isNull() && !itemStack.getItem().isNull()) {
         if (MappedClasses.Y1 != null && itemStack.getItem().isInstance(MappedClasses.Y1)) {
            return true;
         } else {
            try {
               String className = itemStack.getItem().getObject().getClass().getName().toLowerCase();
               if (className.contains("shield")) {
                  return true;
               }
            } catch (Throwable var5) {
            }

            String unlocalizedName = itemStack.getItem().A();
            if (unlocalizedName != null && unlocalizedName.toLowerCase().contains("shield")) {
               return true;
            } else {
               try {
                  String displayName = itemStack.x();
                  if (displayName != null && displayName.toLowerCase().contains("shield")) {
                     return true;
                  }
               } catch (Throwable var4) {
               }

               try {
                  String str = itemStack.getItem().getObject().toString().toLowerCase();
                  if (str.contains("shield")) {
                     return true;
                  }
               } catch (Throwable var3) {
               }

               return false;
            }
         }
      } else {
         return false;
      }
   }

   @Nullable
   public static EnumHand q(EntityOtherPlayerMP entityOtherPlayerMP) {
      if (entityOtherPlayerMP != null && !entityOtherPlayerMP.isNull()) {
         ItemStack itemStack = entityOtherPlayerMP.i(EnumHand.offHand());
         if (isShield(itemStack)) {
            return EnumHand.offHand();
         } else {
            ItemStack itemStack2 = entityOtherPlayerMP.i(EnumHand.mainHand());
            return isShield(itemStack2) ? EnumHand.mainHand() : null;
         }
      } else {
         return null;
      }
   }

   public static boolean isBlockingShield(EntityOtherPlayerMP entityOtherPlayerMP) {
      if (entityOtherPlayerMP != null && !entityOtherPlayerMP.isNull()) {
         EnumHand shieldHand = q(entityOtherPlayerMP);
         if (shieldHand == null) {
            return false;
         } else if (entityOtherPlayerMP.isBlocking()) {
            return true;
         } else {
            if (entityOtherPlayerMP.l$src$Z$1io4duf()) {
               ItemStack itemStack = entityOtherPlayerMP.i(shieldHand);
               if (itemStack.isNotNull() && isShield(itemStack)) {
                  float f = entityOtherPlayerMP.d(shieldHand);
                  if (f > 0.0F) {
                     int n = itemStack.getItem().I(itemStack, entityOtherPlayerMP);
                     return n - f >= 5.0F;
                  }

                  return true;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   public static float e(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2) {
      float f = b(entityLivingBase);
      float f2 = b(entityLivingBase2);
      return f - f2;
   }

   public static boolean W(Entity entity) {
      return entity.isInstance(MappedClasses.zm) ? entity.isInstance(MappedClasses.Y3) : false;
   }

   public static int P(Entity entity, Entity entity2) {
      double d = entity2.z() - entity.z();
      double d2 = entity2.N() - entity.N() + entity2.Y() / 2.0F + 0.2;
      if (ForgeVersion.MC_1_7_10.Y()) {
         d2 = entity2.N() - 0.3 - entity.N();
      }

      double d3 = entity2.h() - entity.h();
      double d4 = MathUtil.sqrt(d * d + d3 * d3);
      float f = (float)(-(Math.atan2(d2, d4) * 180.0 / Math.PI));
      float f2 = (float)MathUtil.wrapAngleTo180((double)(entity.V() - f));
      return (int)f2;
   }

   public static boolean C(Entity entity, double d, double d2, float f) {
      double d3 = 0.0;
      double d4 = d - (entity.M() + (entity.z() - entity.M()) * f);
      double d5 = d2 - (entity.m$src$D$fwnne5() + (entity.h() - entity.m$src$D$fwnne5()) * f);
      if (d5 > 0.0 && d4 > 0.0) {
         d3 = Math.toDegrees(-Math.atan(d4 / d5));
      } else if (d5 > 0.0 && d4 < 0.0) {
         d3 = Math.toDegrees(-Math.atan(d4 / d5));
      } else if (d5 < 0.0 && d4 > 0.0) {
         d3 = -90.0 + Math.toDegrees(Math.atan(d5 / d4));
      } else if (d5 < 0.0 && d4 < 0.0) {
         d3 = 90.0 + Math.toDegrees(Math.atan(d5 / d4));
      }

      double d6 = MathUtil.wrapAngleTo180((d3 - entity.J()) % 360.0);
      return d6 < 0.0;
   }

   public static EntityLivingBase l(double d, float f) {
      return Minecraft.F().getObject() != null && Minecraft.theWorld().getObject() != null ? b(d, f, Minecraft.F(), Minecraft.F()) : null;
   }

   public static double y(double d, double d2, double d3, double d4, double d5, double d6) {
      double d7 = d - d4;
      double d8 = d2 - d5;
      double d9 = d3 - d6;
      return MathUtil.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
   }

   public static double p(Entity entity, double d, double d2, double d3) {
      double d4 = entity.z() - d;
      double d5 = entity.N() - d2;
      double d6 = entity.h() - d3;
      return MathUtil.sqrt(d4 * d4 + d5 * d5 + d6 * d6);
   }

   public static double r(int n, int n2, int n3, int n4) {
      double d = n - n3;
      double d2 = n2 - n4;
      return Math.sqrt(d * d + d2 * d2);
   }

   public static void Z$src$V$1dox3k() {
   }

   public static double s(Entity entity, Entity entity2) {
      double d = entity.z() - entity2.z();
      double d2 = entity.h() - entity2.h();
      return MathUtil.sqrt(d * d + d2 * d2);
   }

   public static double C(double d, double d2, double d3, double d4, double d5) {
      double d6 = 0.0;
      double d7 = d4 - d;
      double d8 = d5 - d2;
      if (d8 > 0.0 && d7 > 0.0) {
         d6 = Math.toDegrees(-Math.atan(d7 / d8));
      } else if (d8 > 0.0 && d7 < 0.0) {
         d6 = Math.toDegrees(-Math.atan(d7 / d8));
      } else if (d8 < 0.0 && d7 > 0.0) {
         d6 = -90.0 + Math.toDegrees(Math.atan(d8 / d7));
      } else if (d8 < 0.0 && d7 < 0.0) {
         d6 = 90.0 + Math.toDegrees(Math.atan(d8 / d7));
      }

      double d9 = Math.abs(d6 - d3) % 360.0;
      return d9 > 180.0 ? 360.0 - d9 : d9;
   }

   public static boolean P(Entity entity, double d, double d2, boolean bl) {
      for (Object e : Minecraft.theWorld().X()) {
         if (MappedClasses.lG.isInstance(e) && o(entity, new EntityOtherPlayerMP(e), d, d2, bl)) {
            return true;
         }
      }

      return false;
   }
}
