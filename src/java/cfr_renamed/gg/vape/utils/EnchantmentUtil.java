/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.utils;

import gg.vape.wrapper.impl.Enchantment;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.EnchantmentHelperBridge;
import gg.vape.wrapper.impl.EnchantmentHolder;
import gg.vape.wrapper.impl.EnchantmentRegistry;
import gg.vape.wrapper.impl.EnchantmentRegistryAccess;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Holder;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Registry;
import gg.vape.wrapper.impl.RegistryAccess;
import gg.vape.wrapper.impl.ResourceKeyEnchantmentBridge;
import gg.vape.wrapper.impl.ResourceLocation;
import gg.vape.wrapper.impl.TagList;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

public class EnchantmentUtil {
    private final static Map<Short, Enchantment> var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P;
    @Nullable
    private static EnchantmentRegistryAccess var_gg_vape_wrapper_impl_EnchantmentRegistryAccess_j;
    @Nullable
    private static Enchantment[] var_gg_vape_wrapper_impl_Enchantment_arr_A;
    private final static Map<Integer, Short> var_java_util_Map_java_lang_Integer,_java_lang_Short__u;
    private final static Enchantment[] var_gg_vape_wrapper_impl_Enchantment_arr_q;
    final public static Enchantment[] var_gg_vape_wrapper_impl_Enchantment_arr_Y;

    public static int[] e(ItemStack itemStack) {
        if (itemStack == null || itemStack.isNull() || itemStack.getItem().P() == 0) {
            return new int[0];
        }
        if (ForgeVersion.MC_1_21_4.d()) {
            ArrayList<Enchantment> arrayList = itemStack.p();
            int[] nArray = new int[arrayList.size() * 2];
            for (int i = 0; i < arrayList.size(); ++i) {
                Enchantment enchantment = arrayList.get(i);
                int n = enchantment.getId();
                if (n == -1) {
                    n = 99;
                }
                int n2 = EnchantmentHelper.q(enchantment, itemStack);
                nArray[i * 2] = n;
                nArray[i * 2 + 1] = n2;
            }
            return nArray;
        }
        TagList tagList = itemStack.a();
        if (tagList.isNotNull()) {
            int[] nArray = new int[tagList.tagCount() * 2];
            for (int i = 0; i < tagList.tagCount(); ++i) {
                short s = tagList.getCompoundTagAt(i).getShort("id");
                short s2 = tagList.getCompoundTagAt(i).getShort("lvl");
                Enchantment enchantment = EnchantmentUtil.A(s);
                if (enchantment == null) continue;
                nArray[i * 2] = s;
                nArray[i * 2 + 1] = s2;
            }
            return nArray;
        }
        return null;
    }

    @Nullable
    public static Enchantment A(short s) {
        if (s < 0 || s > var_gg_vape_wrapper_impl_Enchantment_arr_Y.length - 1) {
            return null;
        }
        return var_gg_vape_wrapper_impl_Enchantment_arr_Y[s];
    }

    private static EnchantmentRegistryAccess z() {
        if (var_gg_vape_wrapper_impl_EnchantmentRegistryAccess_j == null) {
            var_gg_vape_wrapper_impl_EnchantmentRegistryAccess_j = EnchantmentHelperBridge.createLookup();
        }
        return var_gg_vape_wrapper_impl_EnchantmentRegistryAccess_j;
    }

    @Nullable
    public static Short c(Enchantment enchantment) {
        return var_java_util_Map_java_lang_Integer,_java_lang_Short__u.get(enchantment.getId());
    }

    public static Optional<Holder> K(String string) {
        if (!ForgeVersion.MC_1_21_4.d()) {
            return Optional.empty();
        }
        WorldClient worldClient = Minecraft.theWorld();
        if (worldClient.isNull()) {
            return Optional.empty();
        }
        String string2 = string.contains(":") ? string : "minecraft:" + string;
        ResourceLocation resourceLocation = ResourceLocation.create(string2);
        RegistryAccess registryAccess = worldClient.e();
        Registry registry = registryAccess.lookupOrThrow(ResourceKeyEnchantmentBridge.enchantment());
        return registry.t(resourceLocation);
    }

    private static boolean lambda$getEnchantmentByName$2(String string, EnchantmentHolder enchantmentHolder) {
        return string.equals(enchantmentHolder.Z());
    }

    private static Enchantment[] lambda$getVanillaEnchantments_54$1(int n) {
        return new Enchantment[n];
    }

    private static Enchantment lambda$getVanillaEnchantments_54$0(EnchantmentHolder enchantmentHolder) {
        return new Enchantment(enchantmentHolder.N());
    }

    public static Enchantment[] B() {
        if (var_gg_vape_wrapper_impl_Enchantment_arr_A != null) {
            return var_gg_vape_wrapper_impl_Enchantment_arr_A;
        }
        EnchantmentRegistryAccess enchantmentRegistryAccess = EnchantmentUtil.z();
        EnchantmentRegistry enchantmentRegistry = enchantmentRegistryAccess.lookupOrThrow(ResourceKeyEnchantmentBridge.enchantment());
        Stream<EnchantmentHolder> stream = enchantmentRegistry.listElements();
        Stream<Enchantment> stream2 = stream.map(EnchantmentUtil::lambda$getVanillaEnchantments_54$0);
        Enchantment[] enchantmentArray = (Enchantment[])stream2.toArray(EnchantmentUtil::lambda$getVanillaEnchantments_54$1);
        var_gg_vape_wrapper_impl_Enchantment_arr_A = enchantmentArray;
        return enchantmentArray;
    }

    public static Map<Enchantment, Short> A(ItemStack itemStack) {
        HashMap<Enchantment, Short> hashMap;
        block4: {
            block3: {
                hashMap = new HashMap<Enchantment, Short>();
                if (itemStack == null || itemStack.isNull() || itemStack.getItem().P() == 0) {
                    return hashMap;
                }
                if (!ForgeVersion.MC_1_20_6.d()) break block3;
                ArrayList<Enchantment> arrayList = itemStack.p();
                for (Enchantment enchantment : arrayList) {
                    short s = (short)EnchantmentHelper.q(enchantment, itemStack);
                    hashMap.put(enchantment, s);
                }
                break block4;
            }
            TagList tagList = itemStack.a();
            if (!tagList.isNotNull() || tagList.tagCount() <= 0) break block4;
            for (int i = 0; i < tagList.tagCount(); ++i) {
                short s = tagList.getCompoundTagAt(i).getShort("id");
                short s2 = tagList.getCompoundTagAt(i).getShort("lvl");
                Enchantment enchantment = EnchantmentUtil.A(s);
                if (enchantment == null) continue;
                hashMap.put(enchantment, s2);
            }
        }
        return hashMap;
    }

    @Nullable
    public static Enchantment k(short s) {
        return var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.get(s);
    }

    @Nullable
    public static Enchantment h(String string) {
        if (!ForgeVersion.MC_1_21_4.d()) {
            return null;
        }
        String string2 = string.contains(":") ? string : "minecraft:" + string;
        EnchantmentRegistryAccess enchantmentRegistryAccess = EnchantmentUtil.z();
        EnchantmentRegistry enchantmentRegistry = enchantmentRegistryAccess.lookupOrThrow(ResourceKeyEnchantmentBridge.enchantment());
        Stream<EnchantmentHolder> stream = enchantmentRegistry.listElements();
        Optional<EnchantmentHolder> optional = stream.filter(arg_0 -> EnchantmentUtil.lambda$getEnchantmentByName$2(string2, arg_0)).findFirst();
        return optional.map(EnchantmentUtil::lambda$getEnchantmentByName$3).orElse(null);
    }

    public static List<String> E(ItemStack itemStack) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Map<Enchantment, Short> map = EnchantmentUtil.A(itemStack);
        block0: for (Map.Entry<Enchantment, Short> entry : map.entrySet()) {
            Enchantment enchantment = entry.getKey();
            short s = entry.getValue();
            for (Enchantment enchantment2 : var_gg_vape_wrapper_impl_Enchantment_arr_q) {
                if (!enchantment.equals(enchantment2)) continue;
                String string = enchantment.getTranslatedName(s).substring(0, 1).toLowerCase();
                string = s > 99 ? string + "99+" : string + s;
                arrayList.add(string);
                continue block0;
            }
        }
        if (arrayList.isEmpty() && itemStack.x$src$Z$1nwfctq()) {
            arrayList.add("e*");
        }
        return arrayList;
    }

    private static Enchantment lambda$getEnchantmentByName$3(EnchantmentHolder enchantmentHolder) {
        return new Enchantment(enchantmentHolder.N());
    }

    static {
        var_gg_vape_wrapper_impl_EnchantmentRegistryAccess_j = null;
        var_gg_vape_wrapper_impl_Enchantment_arr_A = null;
        var_gg_vape_wrapper_impl_Enchantment_arr_Y = Enchantment.getEnchantments();
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P = new LinkedHashMap<Short, Enchantment>();
        var_java_util_Map_java_lang_Integer,_java_lang_Short__u = new LinkedHashMap<Integer, Short>();
        var_gg_vape_wrapper_impl_Enchantment_arr_q = new Enchantment[]{Enchantment.protection(), Enchantment.unbreaking(), Enchantment.sharpness(), Enchantment.fireAspect(), Enchantment.efficiency(), Enchantment.featherFalling(), Enchantment.power(), Enchantment.flame(), Enchantment.punch(), Enchantment.fortune(), Enchantment.infinity(), Enchantment.thorns(), Enchantment.knockback()};
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(0, Enchantment.protection());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(1, Enchantment.unbreaking());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(2, Enchantment.sharpness());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(3, Enchantment.fireAspect());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(4, Enchantment.efficiency());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(5, Enchantment.featherFalling());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(6, Enchantment.power());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(7, Enchantment.flame());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(8, Enchantment.punch());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(9, Enchantment.fortune());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(10, Enchantment.infinity());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(11, Enchantment.knockback());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(12, Enchantment.projectileProtection());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(13, Enchantment.thorns());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(14, Enchantment.fireProtection());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(15, Enchantment.blastProtection());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(16, Enchantment.looting());
        var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(17, Enchantment.silkTouch());
        if (ForgeVersion.MC_1_12_2.d()) {
            var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(18, Enchantment.mending());
            var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(19, Enchantment.sweepingEdge());
        }
        if (ForgeVersion.MC_1_21_0.d()) {
            var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(20, Enchantment.i());
            var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(21, Enchantment.a());
            var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.put(22, Enchantment.h());
        }
        for (Map.Entry<Short, Enchantment> entry : var_java_util_Map_java_lang_Short,_gg_vape_wrapper_impl_Enchantment__P.entrySet()) {
            Enchantment enchantment = entry.getValue();
            if (enchantment == null || enchantment.isNull()) continue;
            var_java_util_Map_java_lang_Integer,_java_lang_Short__u.put(enchantment.getId(), entry.getKey());
        }
    }
}

