/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import gg.vape.ui.click.component.GuiComponent;

public class MFoods
extends Mapping {
    private static String controlFlowMarker;
    private final MappingField appleField = this.registerFood("APPLE");
    private final MappingField bakedPotatoField = this.registerFood("BAKED_POTATO");
    private final MappingField beefField = this.registerFood("BEEF");
    private final MappingField beetrootField = this.registerFood("BEETROOT");
    private final MappingField beetrootSoupField = this.registerFood("BEETROOT_SOUP");
    private final MappingField breadField = this.registerFood("BREAD");
    private final MappingField carrotField = this.registerFood("CARROT");
    private final MappingField chickenField = this.registerFood("CHICKEN");
    private final MappingField chorusFruitField = this.registerFood("CHORUS_FRUIT");
    private final MappingField codField = this.registerFood("COD");
    private final MappingField cookedBeefField = this.registerFood("COOKED_BEEF");
    private final MappingField cookedChickenField = this.registerFood("COOKED_CHICKEN");
    private final MappingField cookedCodField = this.registerFood("COOKED_COD");
    private final MappingField cookedMuttonField = this.registerFood("COOKED_MUTTON");
    private final MappingField cookedPorkchopField = this.registerFood("COOKED_PORKCHOP");
    private final MappingField cookedRabbitField = this.registerFood("COOKED_RABBIT");
    private final MappingField cookedSalmonField = this.registerFood("COOKED_SALMON");
    private final MappingField cookieField = this.registerFood("COOKIE");
    private final MappingField driedKelpField = this.registerFood("DRIED_KELP");
    private final MappingField enchantedGoldenAppleField = this.registerFood("ENCHANTED_GOLDEN_APPLE");
    private final MappingField goldenAppleField = this.registerFood("GOLDEN_APPLE");
    private final MappingField goldenCarrotField = this.registerFood("GOLDEN_CARROT");
    private final MappingField honeyBottleField = this.registerFood("HONEY_BOTTLE");
    private final MappingField melonSliceField = this.registerFood("MELON_SLICE");
    private final MappingField mushroomStewField = this.registerFood("MUSHROOM_STEW");
    private final MappingField muttonField = this.registerFood("MUTTON");
    private final MappingField poisonousPotatoField = this.registerFood("POISONOUS_POTATO");
    private final MappingField porkchopField = this.registerFood("PORKCHOP");
    private final MappingField potatoField = this.registerFood("POTATO");
    private final MappingField pufferfishField = this.registerFood("PUFFERFISH");
    private final MappingField pumpkinPieField = this.registerFood("PUMPKIN_PIE");
    private final MappingField rabbitField = this.registerFood("RABBIT");
    private final MappingField rabbitStewField = this.registerFood("RABBIT_STEW");
    private final MappingField rottenFleshField = this.registerFood("ROTTEN_FLESH");
    private final MappingField salmonField = this.registerFood("SALMON");
    private final MappingField spiderEyeField = this.registerFood("SPIDER_EYE");
    private final MappingField suspiciousStewField = this.registerFood("SUSPICIOUS_STEW");
    private final MappingField sweetBerriesField = this.registerFood("SWEET_BERRIES");
    private final MappingField glowBerriesField = this.registerFood("GLOW_BERRIES");
    private final MappingField tropicalFishField = this.registerFood("TROPICAL_FISH");

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public MFoods() {
        super(MappedClasses.foodsClass);
        if (MFoods.getControlFlowMarker() == null) {
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
        }
    }

    private MappingField registerFood(String fieldName) {
        return this.registerStaticField(fieldName, true, MappedClasses.foodPropertiesClass);
    }

    public Object apple() {
        return this.appleField.getObject(null);
    }

    public Object bakedPotato() {
        return this.bakedPotatoField.getObject(null);
    }

    public Object beef() {
        return this.beefField.getObject(null);
    }

    public Object beetroot() {
        return this.beetrootField.getObject(null);
    }

    public Object beetrootSoup() {
        return this.beetrootSoupField.getObject(null);
    }

    public Object bread() {
        return this.breadField.getObject(null);
    }

    public Object carrot() {
        return this.carrotField.getObject(null);
    }

    public Object chicken() {
        return this.chickenField.getObject(null);
    }

    public Object chorusFruit() {
        return this.chorusFruitField.getObject(null);
    }

    public Object cod() {
        return this.codField.getObject(null);
    }

    public Object cookedBeef() {
        return this.cookedBeefField.getObject(null);
    }

    public Object cookedChicken() {
        return this.cookedChickenField.getObject(null);
    }

    public Object cookedCod() {
        return this.cookedCodField.getObject(null);
    }

    public Object cookedMutton() {
        return this.cookedMuttonField.getObject(null);
    }

    public Object cookedPorkchop() {
        return this.cookedPorkchopField.getObject(null);
    }

    public Object cookedRabbit() {
        return this.cookedRabbitField.getObject(null);
    }

    public Object cookedSalmon() {
        return this.cookedSalmonField.getObject(null);
    }

    public Object cookie() {
        return this.cookieField.getObject(null);
    }

    public Object driedKelp() {
        return this.driedKelpField.getObject(null);
    }

    public Object enchantedGoldenApple() {
        return this.enchantedGoldenAppleField.getObject(null);
    }

    public Object goldenApple() {
        return this.goldenAppleField.getObject(null);
    }

    public Object goldenCarrot() {
        return this.goldenCarrotField.getObject(null);
    }

    public Object honeyBottle() {
        return this.honeyBottleField.getObject(null);
    }

    public Object melonSlice() {
        return this.melonSliceField.getObject(null);
    }

    public Object mushroomStew() {
        return this.mushroomStewField.getObject(null);
    }

    public Object mutton() {
        return this.muttonField.getObject(null);
    }

    public Object poisonousPotato() {
        return this.poisonousPotatoField.getObject(null);
    }

    public Object porkchop() {
        return this.porkchopField.getObject(null);
    }

    public Object potato() {
        return this.potatoField.getObject(null);
    }

    public Object pufferfish() {
        return this.pufferfishField.getObject(null);
    }

    public Object pumpkinPie() {
        return this.pumpkinPieField.getObject(null);
    }

    public Object rabbit() {
        return this.rabbitField.getObject(null);
    }

    public Object rabbitStew() {
        return this.rabbitStewField.getObject(null);
    }

    public Object rottenFlesh() {
        return this.rottenFleshField.getObject(null);
    }

    public Object salmon() {
        return this.salmonField.getObject(null);
    }

    public Object spiderEye() {
        return this.spiderEyeField.getObject(null);
    }

    public Object suspiciousStew() {
        return this.suspiciousStewField.getObject(null);
    }

    public Object sweetBerries() {
        return this.sweetBerriesField.getObject(null);
    }

    public Object glowBerries() {
        return this.glowBerriesField.getObject(null);
    }

    public Object tropicalFish() {
        return this.tropicalFishField.getObject(null);
    }

    static {
        MFoods.setControlFlowMarker("sLz4v");
    }
}

