/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.render.bedplates;

import gg.vape.module.render.bedplates.BedPlateBlockStateKey;
import gg.vape.module.world.bedbreaker.BedTargetRenderPosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BedPlateCountState {
    private final BedTargetRenderPosition position;
    private final Map<Integer, Map<BedPlateBlockStateKey, Integer>> layerCounts = new HashMap<Integer, Map<BedPlateBlockStateKey, Integer>>();
    private final Map<Integer, List<BedPlateBlockStateKey>> sortedLayers = new HashMap<Integer, List<BedPlateBlockStateKey>>();

    public String toString() {
        return "BedData{position=" + this.position + ", layers=" + this.layerCounts + '}';
    }

    public void sortLayersByFrequency() {
        for (Map.Entry<Integer, Map<BedPlateBlockStateKey, Integer>> layerEntry : this.layerCounts.entrySet()) {
            ArrayList<Map.Entry<BedPlateBlockStateKey, Integer>> blockCounts = new ArrayList<Map.Entry<BedPlateBlockStateKey, Integer>>(layerEntry.getValue().entrySet());
            blockCounts.sort(Map.Entry.comparingByValue().reversed());
            ArrayList<BedPlateBlockStateKey> sortedBlockStates = new ArrayList<BedPlateBlockStateKey>();
            for (Map.Entry entry : blockCounts) {
                sortedBlockStates.add((BedPlateBlockStateKey)entry.getKey());
            }
            this.sortedLayers.put(layerEntry.getKey(), sortedBlockStates);
        }
    }

    public void incrementBlock(int layer, int itemId, int metadata) {
        Map blockCounts = this.layerCounts.computeIfAbsent(layer, ignored -> new HashMap());
        BedPlateBlockStateKey blockState = new BedPlateBlockStateKey(itemId, metadata);
        blockCounts.merge(blockState, 1, Integer::sum);
    }

    public void clearCounts() {
        this.layerCounts.clear();
        this.sortedLayers.clear();
    }

    public List<BedPlateBlockStateKey> getSortedLayer(int layer) {
        return this.sortedLayers.getOrDefault(layer, new ArrayList());
    }

    public BedPlateCountState(BedTargetRenderPosition position) {
        this.position = position;
    }

    public BedTargetRenderPosition getPosition() {
        return this.position;
    }
}

