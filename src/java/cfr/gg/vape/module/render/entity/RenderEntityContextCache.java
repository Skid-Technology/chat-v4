/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.module.render.entity;

import gg.vape.module.render.entity.RenderEntityContext;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ITextComponent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class RenderEntityContextCache {
    private static final HashMap<Integer, ITextComponent> customNames;
    private static final Object lock;
    private static final Map<Integer, RenderEntityContext> contexts;
    private static final HashMap<Integer, ITextComponent> displayNameCache;

    @Nullable
    public static void setCustomName(EntityPlayer player, ITextComponent customName) {
        customNames.put(player.S(), customName);
    }

    public static ITextComponent getCustomName(EntityPlayer player) {
        if (customNames.containsKey(player.S())) {
            return customNames.get(player.S());
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void clear() {
        if (contexts.isEmpty()) {
            return;
        }
        Object object = lock;
        synchronized (object) {
            contexts.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void remove(int ... entityIds) {
        Object object = lock;
        synchronized (object) {
            for (int entityId : entityIds) {
                contexts.remove(entityId);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Collection<RenderEntityContext> getContexts() {
        Object object = lock;
        synchronized (object) {
            return new ArrayList<RenderEntityContext>(contexts.values());
        }
    }

    public static void clearNameCaches() {
        displayNameCache.clear();
        customNames.clear();
    }

    private static RenderEntityContext createContext(EntityLivingBase entity, EntityPlayerSP viewer, Integer entityId) {
        RenderEntityContext context = new RenderEntityContext(entityId, entity, viewer);
        context.update(entity, viewer);
        return context;
    }

    @Nullable
    public static ITextComponent getDisplayName(EntityPlayer player) {
        if (displayNameCache.containsKey(player.S())) {
            return displayNameCache.get(player.S());
        }
        ITextComponent displayName = player.Q();
        displayNameCache.put(player.S(), displayName);
        return displayName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static RenderEntityContext getOrCreate(EntityLivingBase entity, EntityPlayerSP viewer) {
        Object object = lock;
        synchronized (object) {
            return contexts.computeIfAbsent(entity.S(), entityId -> RenderEntityContextCache.createContext(entity, viewer, entityId));
        }
    }

    static {
        lock = new Object();
        contexts = new LinkedHashMap<Integer, RenderEntityContext>();
        displayNameCache = new HashMap();
        customNames = new HashMap();
    }
}

