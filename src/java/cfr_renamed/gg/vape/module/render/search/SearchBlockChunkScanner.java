/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.render.search;

import gg.vape.module.render.search.SearchBlockRenderEntry;
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
    final public static Queue<SearchBlockRenderEntry> entryPool = new LinkedList<SearchBlockRenderEntry>();

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
        return ((long)x & 0x1FFFFFL) << 43 | ((long)z & 0x1FFFFFL) << 22 | (long)y & 0xFFFL;
    }

    private static void scanSection(char[] blockStates, int chunkX, int sectionBaseY, int chunkZ, SearchBlock[] targets, ArrayList<SearchBlockRenderEntry> results, Set<Long> airPositions, boolean onlyCaves) {
        for (int i = 0; i < blockStates.length; ++i) {
            int localX;
            char state = blockStates[i];
            int blockId = state >> 4;
            if (blockId == 0) continue;
            int metadata = state & 0xF;
            boolean matched = false;
            for (localX = 0; localX < targets.length; ++localX) {
                SearchBlock searchBlock = targets[localX];
                if (searchBlock.M() == blockId && (searchBlock.i() == -1 || searchBlock.i() == metadata)) {
                    matched = true;
                    break;
                }
                Predicate<Character> predicate = searchBlock.E();
                if (predicate == null || !predicate.test(Character.valueOf(state))) continue;
                matched = true;
                break;
            }
            if (!matched) continue;
            localX = i % 16;
            int worldY = i / 256 + sectionBaseY;
            int localZ = i / 16 % 16;
            int worldX = (chunkX << 4) + localX;
            int finalY = worldY;
            int worldZ = (chunkZ << 4) + localZ;
            if (onlyCaves && !SearchBlockChunkScanner.hasAdjacentAir(worldX, finalY, worldZ, airPositions)) continue;
            SearchBlockRenderEntry searchBlockRenderEntry = SearchBlockChunkScanner.obtain(blockId, metadata, worldX, finalY, worldZ);
            results.add(searchBlockRenderEntry);
        }
    }

    private static boolean hasAdjacentAir(int x, int y, int z, Set<Long> airPositions) {
        int[][] offsets = new int[][]{{0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}};
        for (int[] offset : offsets) {
            int neighborX = x + offset[0];
            int neighborY = y + offset[1];
            int neighborZ = z + offset[2];
            long key = SearchBlockChunkScanner.packPosition(neighborX, neighborY, neighborZ);
            if (!airPositions.contains(key)) continue;
            return true;
        }
        return false;
    }

    public static ArrayList<SearchBlockRenderEntry> scanLoadedChunks(List<SearchBlock> searchBlocks, int maxDistance, boolean onlyCaves) {
        int chunkZ;
        int chunkX;
        ArrayList<SearchBlockRenderEntry> results = new ArrayList<SearchBlockRenderEntry>();
        HashSet<Long> airPositions = new HashSet<Long>();
        WorldClient worldClient = Minecraft.theWorld();
        ClientChunkProvider clientChunkProvider = worldClient.U();
        List<Chunk> loadedChunks = clientChunkProvider.L();
        EntityPlayerSP entityPlayerSP = Minecraft.thePlayer();
        double playerX = entityPlayerSP.z();
        double playerZ = entityPlayerSP.h();
        if (onlyCaves) {
            for (Chunk chunk : loadedChunks) {
                List<ChunkSection> sections = chunk.U();
                for (ChunkSection section : sections) {
                    if (section == null || ((Wrapper)section).isNull() || section.C() == null) continue;
                    int sectionBaseY = section.l();
                    char[] blockStates = section.C();
                    int cx = chunk.a();
                    chunkZ = (int)MathUtil.Z(playerX, 0.0, playerZ, cx << 4, 0.0, (chunkX = chunk.j()) << 4);
                    if (chunkZ > maxDistance) continue;
                    SearchBlockChunkScanner.markAirBlocks(blockStates, cx, sectionBaseY, chunkX, airPositions);
                }
            }
        }
        SearchBlock[] targets = searchBlocks.toArray(new SearchBlock[0]);
        for (Chunk chunk : loadedChunks) {
            List<ChunkSection> sections = chunk.U();
            for (ChunkSection chunkSection : sections) {
                if (chunkSection == null || chunkSection.isNull() || chunkSection.C() == null) continue;
                int sectionBaseY = chunkSection.l();
                char[] blockStates = chunkSection.C();
                chunkX = chunk.a();
                int distance = (int)MathUtil.Z(playerX, 0.0, playerZ, (chunkX << 4) + 8, 0.0, ((chunkZ = chunk.j()) << 4) + 8);
                if (distance > maxDistance) continue;
                SearchBlockChunkScanner.scanSection(blockStates, chunkX, sectionBaseY, chunkZ, targets, results, airPositions, onlyCaves);
            }
        }
        return results;
    }

    private static void markAirBlocks(char[] blockStates, int chunkX, int sectionBaseY, int chunkZ, Set<Long> airPositions) {
        for (int i = 0; i < blockStates.length; ++i) {
            char state = blockStates[i];
            int blockId = state >> 4;
            if (blockId != 0 && blockId != 8 && blockId != 9 && blockId != 30) continue;
            int localX = i % 16;
            int worldY = i / 256 + sectionBaseY;
            int localZ = i / 16 % 16;
            int worldX = (chunkX << 4) + localX;
            int finalY = worldY;
            int worldZ = (chunkZ << 4) + localZ;
            long key = SearchBlockChunkScanner.packPosition(worldX, finalY, worldZ);
            airPositions.add(key);
        }
    }
}

