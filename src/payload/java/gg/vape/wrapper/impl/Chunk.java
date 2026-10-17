package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Chunk extends Wrapper {
   private Biome x(BlockPos blockPos, ChunkWorldBridge chunkWorldBridge) {
      return new Biome(vapeInstance.getMappings().qD.N(this.I, blockPos.getObject(), chunkWorldBridge.getObject()));
   }

   public int q(int n) {
      return ForgeVersion.MC_1_20_6.d() ? vapeInstance.getMappings().qD.I(this.I, n) : n >> 4;
   }

   public int j() {
      return vapeInstance.getMappings().qD.A(this.I);
   }

   public int a() {
      return vapeInstance.getMappings().qD.M(this.I);
   }

   private static boolean lambda$getEntitiesWithinAABBForEntity$0(Object object) {
      return true;
   }

   public boolean F() {
      return vapeInstance.getMappings().qD.c(this.I);
   }

   private static void lambda$getEntitiesWithinAABBForEntity$1(AxisAlignedBB axisAlignedBB, Predicate predicate, List list, Object object) {
      if (object != axisAlignedBB && predicate.test(object)) {
         list.add(new Entity(object));
      }
   }

   public Chunk(Object object) {
      super(object);
   }

   public Map l() {
      return (Map)vapeInstance.getMappings().qD.j(this.I);
   }

   public List<ChunkSection> U() {
      Object[] objectArray = vapeInstance.getMappings().qD.D(this.I);
      ArrayList<ChunkSection> arrayList = new ArrayList<>();

      for (Object object : objectArray) {
         arrayList.add(new ChunkSection(object));
      }

      return arrayList;
   }

   public Biome J(int n, int n2, ChunkWorldBridge chunkWorldBridge) {
      if (ForgeVersion.MC_1_16_5.d()) {
         return Minecraft.theWorld().Y(BlockPos.create(n, 0, n2));
      } else {
         return ForgeVersion.MC_1_7_10.Y()
            ? this.x(BlockPos.create(n, 0, n2), chunkWorldBridge)
            : new Biome(vapeInstance.getMappings().qD.D(this.I, n & 15, n2 & 15, chunkWorldBridge.getObject()));
      }
   }

   public Object[] R() {
      return vapeInstance.getMappings().qD.D(this.I);
   }

   public void d(Entity entity, AxisAlignedBB axisAlignedBB, List list, Object object) {
      if (ForgeVersion.MC_1_17.d()) {
         Predicate predicate = object == null ? Chunk::lambda$getEntitiesWithinAABBForEntity$0 : (Predicate)object;
         Minecraft.theWorld()
            .getEntityGetter()
            .forEachEntityInBounds(axisAlignedBB, entityObject -> lambda$getEntitiesWithinAABBForEntity$1(axisAlignedBB, predicate, list, entityObject));
      } else {
         vapeInstance.getMappings().qD.j(this.I, entity.getObject(), axisAlignedBB.getObject(), list, object);
      }
   }
}
