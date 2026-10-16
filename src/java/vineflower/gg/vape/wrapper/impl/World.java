package gg.vape.wrapper.impl;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.mappings.MWorld;
import gg.vape.wrapper.Wrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class World extends Wrapper {
   private static int[] w;

   public ChunkWorldBridge C() {
      return ForgeVersion.MC_1_16_5.d() ? null : new ChunkWorldBridge(vapeInstance.getMappings().Cy.g(this.I));
   }

   public float V() {
      return vapeInstance.getMappings().Cy.a(this.I);
   }

   private List P() {
      Iterable iterable = vapeInstance.getMappings().Cy.m(this.I);
      ArrayList arrayList = new ArrayList();

      for (Object t : iterable) {
         arrayList.add(t);
      }

      return arrayList;
   }

   public Object q() {
      return vapeInstance.getMappings().Cy.y(this.I);
   }

   public RayTraceResult K(Vec3 vec3, Vec3 vec32, boolean bl, boolean bl2, boolean bl3, Entity entity) {
      if (ForgeVersion.MC_1_16_5.d()) {
         RayTraceContext$FluidMode blockMode = bl2
            ? RayTraceContext$FluidMode.collider()
            : (bl3 ? RayTraceContext$FluidMode.visual() : RayTraceContext$FluidMode.outline());
         RayTraceContext$BlockMode fluidMode = bl ? RayTraceContext$BlockMode.any() : RayTraceContext$BlockMode.none();
         RayTraceContext rayTraceContext = RayTraceContext.b(vec3, vec32, blockMode, fluidMode, entity);
         return new RayTraceResult(vapeInstance.getMappings().Cy.i(this.I, rayTraceContext.getObject()));
      } else {
         return new RayTraceResult(vapeInstance.getMappings().Cy.l(this.I, vec3.getObject(), vec32.getObject(), bl, bl2, bl3));
      }
   }

   private static UnsupportedOperationException a(UnsupportedOperationException unsupportedOperationException) {
      return unsupportedOperationException;
   }

   public List X() {
      return ForgeVersion.MC_1_16_5.d() ? this.u() : vapeInstance.getMappings().Cy.Q(this.I);
   }

   public void T(float f) {
      vapeInstance.getMappings().Cy.M(this.I, f);
   }

   public int R() {
      return ForgeVersion.MC_1_21_4.d() ? MWorld.i(vapeInstance.getMappings().Cy, this.I) : 0;
   }

   private static boolean lambda$getEntitiesWithinAABBExcludingEntity$0(Object object) {
      return true;
   }

   public void o(float f) {
      vapeInstance.getMappings().Cy.L(this.I, f);
   }

   private static boolean lambda$getEntitiesWithinAABBExcludingEntity$2(Object object) {
      return true;
   }

   public GlStateManager$BlendState E() {
      Object object = vapeInstance.getMappings().Cy.j(this.I);
      return MappedClasses.I.equals(object.getClass()) ? new GlStateManager$BlendState(object) : null;
   }

   public List i(Entity entity, AxisAlignedBB axisAlignedBB) {
      ArrayList<AxisAlignedBB> arrayList = new ArrayList<>();
      if (ForgeVersion.MC_1_20_6.d()) {
         for (Object t : (Iterable)vapeInstance.getMappings().Cy.y(this.I, entity.getObject(), axisAlignedBB.getObject())) {
            EntityFishHook entityFishHook = new EntityFishHook(t);
            arrayList.add(new AxisAlignedBB(entityFishHook.n().getObject()));
         }

         return arrayList;
      } else if (!ForgeVersion.MC_1_16_5.d()) {
         return (List)vapeInstance.getMappings().Cy.y(this.I, entity.getObject(), axisAlignedBB.getObject());
      } else {
         Stream stream = (Stream)vapeInstance.getMappings().Cy.y(this.I, entity.getObject(), axisAlignedBB.getObject());

         for (Object object : stream.toArray()) {
            EntityFishHook entityFishHook = new EntityFishHook(object);
            arrayList.add(new AxisAlignedBB(entityFishHook.n().getObject()));
         }

         return arrayList;
      }
   }

   public boolean M(Entity entity, AxisAlignedBB axisAlignedBB) {
      return ForgeVersion.MC_1_21_4.d() ? MWorld.x(vapeInstance.getMappings().Cy, this.I, entity.getObject(), axisAlignedBB.getObject()) : false;
   }

   public boolean z(Entity entity, AxisAlignedBB axisAlignedBB, BiPredicate biPredicate) {
      return vapeInstance.getMappings().Cy.u(this.I, entity.getObject(), axisAlignedBB.getObject(), biPredicate);
   }

   public BlockStateWorldBridge o(BlockPos blockPos) {
      return new BlockStateWorldBridge(vapeInstance.getMappings().Cy.v(this.I, blockPos.getObject()));
   }

   public int A(int n, int n2, int n3) {
      return MWorld.X(vapeInstance.getMappings().Cy, this.I, n, n2, n3);
   }

   public Biome Y(BlockPos blockPos) {
      return new Biome(vapeInstance.getMappings().Cy.a(this.I, blockPos.getObject()));
   }

   public boolean j$src$Z$11aji0a(BlockPos blockPos) {
      return ForgeVersion.MC_1_17.d()
         ? vapeInstance.getMappings().Cy.d(this.I, blockPos.getX() >> 4, blockPos.getZ() >> 4)
         : vapeInstance.getMappings().Cy.r(this.I, blockPos.getObject());
   }

   public Chunk j(BlockPos blockPos) {
      return new Chunk(vapeInstance.getMappings().Cy.Z(this.I, blockPos.getObject()));
   }

   public ChunkProvider getChunkProvider() {
      return new ChunkProvider(vapeInstance.getMappings().Cy.E(this.I));
   }

   public List z() {
      return ForgeVersion.MC_1_16_5.d() ? this.P() : vapeInstance.getMappings().Cy.y$src$Ljava_util_List_$1xik31o(this.I);
   }

   public List getEntityList() {
      return this.z();
   }

   public float N() {
      return vapeInstance.getMappings().Cy.X(this.I);
   }

   public Block getBlockByPos(int n, int n2, int n3) {
      if (ForgeVersion.MC_1_7_10.L()) {
         return new Block(vapeInstance.getMappings().Cy.U(this.I, n, n2, n3));
      } else {
         BlockPos blockPos = BlockPos.create(n, n2, n3);
         BlockState blockState = new BlockState(vapeInstance.getMappings().Cy.u(this.I, blockPos.getObject()));
         return blockState.getBlock();
      }
   }

   public Entity V(int n) {
      return new Entity(MWorld.b(vapeInstance.getMappings().Cy, this.I, n));
   }

   public static int[] a() {
      return w;
   }

   public boolean h(AxisAlignedBB axisAlignedBB, Material material) {
      return vapeInstance.getMappings().Cy.H(this.getObject(), axisAlignedBB.getObject(), material.getObject());
   }

   public void f(float f) {
      vapeInstance.getMappings().Cy.R(this.I, f);
   }

   public static void F(int[] nArray) {
      w = nArray;
   }

   public void i(long l) {
      if (!ForgeVersion.MC_1_16_5.v()) {
         vapeInstance.getMappings().Cy.V(this.I, l);
      }
   }

   public WorldProvider getWorldProvider() {
      return new WorldProvider(MWorld.getWorldProvider(vapeInstance.getMappings().Cy, this.I));
   }

   public float n() {
      return vapeInstance.getMappings().Cy.Z(this.I);
   }

   public void M(Entity entity) {
      if (ForgeVersion.MC_1_17.d()) {
         entity.k();
         MWorld.a(vapeInstance.getMappings().Cy, this.I, entity.S(), EntityRemovalReason.P().getObject());
      } else if (ForgeVersion.MC_1_16_5.d()) {
         entity.k();
         MWorld.c(vapeInstance.getMappings().Cy, this.I, entity.getObject());
      } else {
         MWorld.c(vapeInstance.getMappings().Cy, this.I, entity.getObject());
      }
   }

   private List u() {
      Iterable iterable = vapeInstance.getMappings().Cy.m(this.I);
      ArrayList arrayList = new ArrayList();

      for (Object t : iterable) {
         if (MappedClasses.Yl.isInstance(t)) {
            arrayList.add(t);
         }
      }

      return arrayList;
   }

   public RegistryAccess e() {
      if (ForgeVersion.MC_1_21_4.d()) {
         return new RegistryAccess(vapeInstance.getMappings().Cy.u(this.I));
      } else {
         throw new UnsupportedOperationException("unavailable in this version");
      }
   }

   public float y() {
      return vapeInstance.getMappings().Cy.w(this.I);
   }

   public LevelEntityGetter getEntityGetter() {
      return new LevelEntityGetter(vapeInstance.getMappings().Cy.getEntityGetter(this.I));
   }

   public BlockState getBlockState(BlockPos blockPos) {
      return new BlockState(vapeInstance.getMappings().Cy.u(this.I, blockPos.getObject()));
   }

   public List R$src$Ljava_util_List_$1ycbpra() {
      if (!ForgeVersion.MC_1_17.d()) {
         return vapeInstance.getMappings().Cy.j$src$Ljava_util_List_$jm6ihn(this.I);
      } else {
         ArrayList arrayList = new ArrayList();
         ArrayList<Chunk> arrayList2 = new ArrayList<>();
         AtomicReferenceArray atomicReferenceArray = this.E().X().getChunks();
         int n = 0;

         while (n < atomicReferenceArray.length()) {
            Object chunkHandle;
            if ((chunkHandle = atomicReferenceArray.get(n++)) != null) {
               arrayList2.add(new Chunk(chunkHandle));
            }
         }

         for (Chunk chunk : arrayList2) {
            arrayList.addAll(chunk.l().values());
         }

         return arrayList;
      }
   }

   public List F(Entity entity, AxisAlignedBB axisAlignedBB) {
      if (ForgeVersion.MC_1_20_6.d()) {
         Predicate<Object> predicate = World::lambda$getEntitiesWithinAABBExcludingEntity$0;
         return vapeInstance.getMappings().Cy.T(this.I, entity.getObject(), axisAlignedBB.getObject(), predicate);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         com.google.common.base.Predicate predicate = World::lambda$getEntitiesWithinAABBExcludingEntity$1;
         return vapeInstance.getMappings().Cy.T(this.I, entity.getObject(), axisAlignedBB.getObject(), ForgeVersion.MC_1_17.d() ? predicate : null);
      } else if (ForgeVersion.MC_1_12_2.d()) {
         com.google.common.base.Predicate predicate = World::lambda$getEntitiesWithinAABBExcludingEntity$2;
         return vapeInstance.getMappings().Cy.T(this.I, entity.getObject(), axisAlignedBB.getObject(), ForgeVersion.MC_1_17.d() ? predicate : null);
      } else {
         return vapeInstance.getMappings().Cy.K(this.I, entity.getObject(), axisAlignedBB.getObject());
      }
   }

   public List i(Entity entity, AxisAlignedBB axisAlignedBB, Object object) {
      return vapeInstance.getMappings().Cy.X(this.I, entity.getObject(), axisAlignedBB.getObject(), object);
   }

   public World(Object object) {
      super(object);
   }

   private static boolean lambda$getEntitiesWithinAABBExcludingEntity$1(Object object) {
      return true;
   }

   public boolean I() {
      return MWorld.w(vapeInstance.getMappings().Cy, this.I);
   }

   public Chunk z(int n, int n2) {
      return new Chunk(MWorld.V(vapeInstance.getMappings().Cy, this.I, n, n2));
   }

   public TileEntity G(BlockPos blockPos) {
      return new TileEntity(vapeInstance.getMappings().Cy.j(this.I, blockPos.getObject()));
   }

   public void g(float f) {
      vapeInstance.getMappings().Cy.D(this.I, f);
   }

   public Chunk P(int n, int n2) {
      return ForgeVersion.MC_1_7_10.Y() ? this.j(BlockPos.create(n, 0, n2)) : new Chunk(vapeInstance.getMappings().Cy.H(this.I, n, n2));
   }

   public Block getBlock(double d, double d2, double d3) {
      if (ForgeVersion.MC_1_7_10.L()) {
         return new Block(vapeInstance.getMappings().Cy.U(this.I, (int)d, (int)d2, (int)d3));
      } else {
         BlockPos blockPos = BlockPos.D(d, d2, d3);
         BlockState blockState = new BlockState(vapeInstance.getMappings().Cy.u(this.I, blockPos.getObject()));
         return blockState.getBlock();
      }
   }

   public void Z(int n, int n2, int n3, int n4, int n5, int n6) {
      MWorld.f(vapeInstance.getMappings().Cy, this.I, n, n2, n3, n4, n5, n6);
   }

   public boolean M(int n, int n2, int n3) {
      return MWorld.v(vapeInstance.getMappings().Cy, this.I, n, n2, n3);
   }

   public boolean o(int n, int n2, int n3, int n4, int n5, int n6) {
      return ForgeVersion.MC_1_7_10.L()
         ? MWorld.q(vapeInstance.getMappings().Cy, this.I, n, n2, n3, n4, n5, n6)
         : MWorld.K(vapeInstance.getMappings().Cy, this.I, BlockPos.create(n, n2, n3), BlockPos.create(n4, n5, n6));
   }

   public BlockRayTraceResult r(RayTraceContextFactory rayTraceContextFactory) {
      if (ForgeVersion.MC_1_20_6.d()) {
         return new BlockRayTraceResult(vapeInstance.getMappings().Cy.t(this.I, rayTraceContextFactory.getObject()));
      } else {
         throw new UnsupportedOperationException("Clip method unavailable in this version");
      }
   }

   static {
      F(null);
   }
}
