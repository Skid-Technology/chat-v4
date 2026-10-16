package gg.vape.wrapper.impl;

import gg.vape.mapping.mappings.MRenderItem;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.wrapper.Wrapper;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class RenderItem extends Wrapper {
   private static final Integer[] d = new Integer[2];
   private static final Map e = new HashMap(13);
   private static Object h;
   private static final long[] b;

   public static RenderItem d() {
      if (h == null && MRenderItem.V(vapeInstance.getMappings().DI) == null && ForgeVersion.MC_1_7_10.L() && isNativeAvailable) {
         h = MRenderItem.F(vapeInstance.getMappings().DI).newInstance();
      }

      return new RenderItem(h != null ? h : MRenderItem.a(vapeInstance.getMappings().DI));
   }

   public void z(float f) {
      MRenderItem.l(vapeInstance.getMappings().DI, this.I, f);
   }

   private static int a(Lookup lookup, MutableCallSite mutableCallSite, String string, Object[] objectArray) {
      int n = (Integer)objectArray[0];
      long l = (Long)objectArray[1];
      int n2 = a(n, l);
      MethodHandle methodHandle = MethodHandles.constant(int.class, n2);
      mutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, int.class, long.class));
      return n2;
   }

   public RenderItem(Object object) {
      super(object);
   }

   public void a(ItemStack itemStack, int n, int n2, MatrixStack matrixStack) {
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      Channel bakedModel = this.getBakedModel(itemStack, null, entityPlayerSP.getObject(), 0);
      this.e(this.T() + 50.0F);
      TextureManager textureManager = Minecraft.getTextureManager();
      textureManager.getTexture(TextureAtlas.getBlocksAtlasLocation()).setFilter(false, false);
      RenderSystem.k(0, textureManager.getTexture(TextureAtlas.getBlocksAtlasLocation()).getId());
      RenderSystem.x();
      RenderSystem.s(770, 771);
      RenderSystem.U(1.0F, 1.0F, 1.0F, 1.0F);
      MatrixStack matrixStack2 = RenderSystem.p();
      matrixStack2.H();
      matrixStack2.V(n, n2, 100.0F + this.T());
      matrixStack2.S(1.0F, -1.0F, 1.0F);
      matrixStack2.S(16.0F, 16.0F, 16.0F);
      RenderSystem.v();
      RenderItemFontBridge renderItemFontBridge = Minecraft.H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv().getBufferSource();
      boolean bl = !bakedModel.usesBlockLight();
      if (bl) {
         RenderHelper.l();
         this.I(itemStack, RenderItemContext.gui(), false, matrixStack, renderItemFontBridge, 15728880, 0, bakedModel);
         renderItemFontBridge.X();
         RenderSystem.n();
         RenderHelper.e();
         matrixStack2 = RenderSystem.p();
         matrixStack2.U();
         RenderSystem.v();
         this.e(this.T() - 50.0F);
      } else {
         this.I(itemStack, RenderItemContext.gui(), false, matrixStack, renderItemFontBridge, 15728880, 0, bakedModel);
         renderItemFontBridge.X();
         RenderSystem.n();
         matrixStack2 = RenderSystem.p();
         matrixStack2.U();
         RenderSystem.v();
         this.e(this.T() - 50.0F);
      }
   }

   public float b() {
      return MRenderItem.I(vapeInstance.getMappings().DI, this.I);
   }

   public void I(
      ItemStack itemStack,
      RenderItemContext renderItemContext,
      boolean bl,
      MatrixStack matrixStack,
      RenderItemFontBridge renderItemFontBridge,
      int n,
      int n2,
      Channel bakedModel
   ) {
      MRenderItem.x(
         vapeInstance.getMappings().DI,
         this.I,
         itemStack.getObject(),
         renderItemContext.getObject(),
         bl,
         matrixStack.getObject(),
         renderItemFontBridge.getObject(),
         n,
         n2,
         bakedModel.getObject()
      );
   }

   public void e(float f) {
      if (!ForgeVersion.MC_1_20_6.d()) {
         MRenderItem.G(vapeInstance.getMappings().DI, this.I, f);
      }
   }

   public Channel getBakedModel(ItemStack itemStack, World world, Object entityHandle, int seed) {
      Object worldHandle = world == null ? null : world.getObject();
      return new Channel(MRenderItem.A(vapeInstance.getMappings().DI, this.I, itemStack.getObject(), worldHandle, entityHandle, seed));
   }

   public void c(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int n, int n2) {
      if (ForgeVersion.MC_1_7_10.L()) {
         MRenderItem.z(vapeInstance.getMappings().DI, this.I, fontRenderer.getObject(), textureManager.getObject(), itemStack.getObject(), n, n2);
      } else if (ForgeVersion.MC_1_20_6.d()) {
         MatrixStack matrixStack = MatrixStack.A();
         matrixStack.H();
         matrixStack.i(BufferedGuiRenderPrimitives.matrixStack.peek().toMinecraftMatrix());
         this.o(RenderItemTextBridge.t(matrixStack), itemStack, n, n2);
      } else {
         vapeInstance.getMappings().DI.M(this.I, itemStack.getObject(), n, n2);
      }
   }

   private static int a(int n, long l) {
      int n2 = n ^ (int)(l & 32767L) ^ 26973;
      if (d[n2] == null) {
         byte[] byArray2 = new byte[]{
            (byte)(l >>> 56), (byte)(l >>> 48), (byte)(l >>> 40), (byte)(l >>> 32), (byte)(l >>> 24), (byte)(l >>> 16), (byte)(l >>> 8), (byte)l
         };
         long l2 = b[n2];
         byte[] byArray3 = new byte[]{
            (byte)(l2 >>> 56), (byte)(l2 >>> 48), (byte)(l2 >>> 40), (byte)(l2 >>> 32), (byte)(l2 >>> 24), (byte)(l2 >>> 16), (byte)(l2 >>> 8), (byte)l2
         };
         Long l3 = Thread.currentThread().getId();
         Object[] objectArray = (Object[])e.get(l3);

         byte[] byArray;
         try {
            if (objectArray == null) {
               objectArray = new Object[]{Cipher.getInstance("DES/CBC/NoPadding"), SecretKeyFactory.getInstance("DES"), new IvParameterSpec(new byte[8])};
               e.put(l3, objectArray);
            }

            DESKeySpec dESKeySpec = new DESKeySpec(byArray2);
            SecretKey secretKey = ((SecretKeyFactory)objectArray[1]).generateSecret(dESKeySpec);
            Cipher cipher = (Cipher)objectArray[0];
            cipher.init(2, secretKey, (IvParameterSpec)objectArray[2]);
            byArray = cipher.doFinal(byArray3);
         } catch (Exception var14) {
            throw new RuntimeException("a/w_", var14);
         }

         int n3 = (byArray[4] & 255) << 24 | (byArray[5] & 255) << 16 | (byArray[6] & 255) << 8 | byArray[7] & 255;
         d[n2] = n3;
      }

      return d[n2];
   }

   private static CallSite a(Lookup lookup, String string, MethodType methodType) {
      MutableCallSite mutableCallSite = new MutableCallSite(methodType);

      try {
         mutableCallSite.setTarget(
            MethodHandles.explicitCastArguments(
               MethodHandles.insertArguments(cfr_ldc_0().asCollector(Object[].class, methodType.parameterCount()), 0, lookup, mutableCallSite, string),
               methodType
            )
         );
         return mutableCallSite;
      } catch (Exception var5) {
         throw new RuntimeException("a/w_ : " + string + " : " + methodType.toString(), var5);
      }
   }

   public float T() {
      return ForgeVersion.MC_1_20_6.d() ? 100.0F : MRenderItem.x(vapeInstance.getMappings().DI, this.I);
   }

   public void o(RenderItemTextBridge renderItemTextBridge, ItemStack itemStack, int n, int n2) {
      vapeInstance.getMappings().DI.M(renderItemTextBridge.getObject(), itemStack.getObject(), n, n2);
   }

   public void P(FontRenderer fontRenderer, TextureManager textureManager, ItemStack itemStack, int n, int n2) {
      if (ForgeVersion.MC_1_7_10.L()) {
         MRenderItem.P(vapeInstance.getMappings().DI, this.I, fontRenderer.getObject(), textureManager.getObject(), itemStack.getObject(), n, n2);
      } else {
         MRenderItem.Y(vapeInstance.getMappings().DI, this.I, fontRenderer.getObject(), itemStack.getObject(), n, n2);
      }
   }

   static MethodHandle cfr_ldc_0() {
      try {
         return MethodHandles.lookup()
            .findStatic(
               RenderItem.class,
               "a",
               MethodType.fromMethodDescriptorString(
                  "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/invoke/MutableCallSite;Ljava/lang/String;[Ljava/lang/Object;)I", null
               )
            );
      } catch (IllegalAccessException | NoSuchMethodException var1) {
         throw new IllegalArgumentException(var1);
      }
   }

   static {
      long[] lArray = new long[]{6381959226204642166L, 4564068455244414596L};
      b = lArray;
   }
}
