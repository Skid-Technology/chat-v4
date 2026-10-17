package gg.vape.module.render.search;

import gg.vape.ui.unmap.SearchBlock;
import gg.vape.utils.MathUtil;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.Chunk;
import gg.vape.wrapper.impl.ChunkSection;
import gg.vape.wrapper.impl.ClientChunkProvider;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class SearchBlockChunkScanner {
   public static final Queue<SearchBlockRenderEntry> entryPool = new LinkedList<>();

   public static SearchBlockRenderEntry obtain(int blockId, int metadata, int worldX, int worldY, int worldZ) {
      SearchBlockRenderEntry searchBlockRenderEntry = entryPool.poll();
      if (searchBlockRenderEntry == null) {
         searchBlockRenderEntry = new SearchBlockRenderEntry(blockId, metadata, worldX, worldY, worldZ);
      } else {
         searchBlockRenderEntry.reset(blockId, metadata, worldX, worldY, worldZ);
      }

      return searchBlockRenderEntry;
   }

   public static void recycle(SearchBlockRenderEntry searchBlockRenderEntry) {
      entryPool.offer(searchBlockRenderEntry);
   }

   private static long packPosition(int x, int y, int z) {
      return (x & 2097151L) << 43 | (z & 2097151L) << 22 | y & 4095L;
   }

   private static void scanSection(
      char[] blockStates,
      int chunkX,
      int sectionBaseY,
      int chunkZ,
      SearchBlock[] targets,
      ArrayList<SearchBlockRenderEntry> results,
      Set<Long> airPositions,
      boolean onlyCaves
   ) {
      for (int i = 0; i < blockStates.length; i++) {
         char state = blockStates[i];
         int blockId = state >> 4;
         if (blockId != 0) {
            int metadata = state & 15;
            boolean matched = false;

            for (int localX = 0; localX < targets.length; localX++) {
               SearchBlock searchBlock = targets[localX];
               if (searchBlock.M() == blockId && (searchBlock.i() == -1 || searchBlock.i() == metadata)) {
                  matched = true;
                  break;
               }

               Predicate<Character> predicate = searchBlock.E();
               if (predicate != null && predicate.test(state)) {
                  matched = true;
                  break;
               }
            }

            if (matched) {
               int var20 = i % 16;
               int worldY = i / 256 + sectionBaseY;
               int localZ = i / 16 % 16;
               int worldX = (chunkX << 4) + var20;
               int worldZ = (chunkZ << 4) + localZ;
               if (!onlyCaves || hasAdjacentAir(worldX, worldY, worldZ, airPositions)) {
                  SearchBlockRenderEntry searchBlockRenderEntry = obtain(blockId, metadata, worldX, worldY, worldZ);
                  results.add(searchBlockRenderEntry);
               }
            }
         }
      }
   }

   private static boolean hasAdjacentAir(int x, int y, int z, Set<Long> airPositions) {
      for (int[] offset : new int[][]{{0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}}) {
         int neighborX = x + offset[0];
         int neighborY = y + offset[1];
         int neighborZ = z + offset[2];
         long key = packPosition(neighborX, neighborY, neighborZ);
         if (airPositions.contains(key)) {
            return true;
         }
      }

      return false;
   }

   public static ArrayList<SearchBlockRenderEntry> scanLoadedChunks(List<SearchBlock> searchBlocks, int maxDistance, boolean onlyCaves) {
      ArrayList<SearchBlockRenderEntry> results = new ArrayList<>();
      HashSet<Long> airPositions = new HashSet<>();
      WorldClient worldClient = Minecraft.theWorld();
      ClientChunkProvider clientChunkProvider = worldClient.U();
      List<Chunk> loadedChunks = clientChunkProvider.L();
      EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
      double playerX = entityPlayerSP.z();
      double playerZ = entityPlayerSP.h();
      if (onlyCaves) {
         for (Chunk chunk : loadedChunks) {
            for (Object section : chunk.U()) {
               if (section != null && !((Wrapper)section).isNull() && ((ChunkSection)section).C() != null) {
                  int sectionBaseY = ((ChunkSection)section).l();
                  char[] blockStates = ((ChunkSection)section).C();
                  int cx = chunk.a();
                  int chunkX;
                  int chunkZ = (int)MathUtil.Z(playerX, 0.0, playerZ, cx << 4, 0.0, (chunkX = chunk.j()) << 4);
                  if (chunkZ <= maxDistance) {
                     markAirBlocks(blockStates, cx, sectionBaseY, chunkX, airPositions);
                  }
               }
            }
         }
      }

      SearchBlock[] targets = searchBlocks.toArray(new SearchBlock[0]);

      for (Chunk chunk : loadedChunks) {
         for (ChunkSection chunkSection : chunk.U()) {
            if (chunkSection != null && !chunkSection.isNull() && chunkSection.C() != null) {
               int sectionBaseY = chunkSection.l();
               char[] blockStates = chunkSection.C();
               int chunkX = chunk.a();
               int chunkZ;
               int distance = (int)MathUtil.Z(playerX, 0.0, playerZ, (chunkX << 4) + 8, 0.0, ((chunkZ = chunk.j()) << 4) + 8);
               if (distance <= maxDistance) {
                  scanSection(blockStates, chunkX, sectionBaseY, chunkZ, targets, results, airPositions, onlyCaves);
               }
            }
         }
      }

      return results;
   }

   private static void markAirBlocks(char[] blockStates, int chunkX, int sectionBaseY, int chunkZ, Set<Long> airPositions) {
      for (int i = 0; i < blockStates.length; i++) {
         char state = blockStates[i];
         int blockId = state >> 4;
         if (blockId == 0 || blockId == 8 || blockId == 9 || blockId == 30) {
            int localX = i % 16;
            int worldY = i / 256 + sectionBaseY;
            int localZ = i / 16 % 16;
            int worldX = (chunkX << 4) + localX;
            int worldZ = (chunkZ << 4) + localZ;
            long key = packPosition(worldX, worldY, worldZ);
            airPositions.add(key);
         }
      }
   }
}
