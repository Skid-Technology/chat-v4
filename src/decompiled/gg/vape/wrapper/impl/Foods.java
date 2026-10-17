/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.wrapper.impl;

import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.FoodProperties;

public class Foods
extends Wrapper {
    public Foods(Object handle) {
        super(handle);
    }

    public static FoodProperties apple() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.apple());
    }

    public static FoodProperties bakedPotato() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.bakedPotato());
    }

    public static FoodProperties beef() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.beef());
    }

    public static FoodProperties beetroot() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.beetroot());
    }

    public static FoodProperties beetrootSoup() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.beetrootSoup());
    }

    public static FoodProperties bread() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.bread());
    }

    public static FoodProperties carrot() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.carrot());
    }

    public static FoodProperties chicken() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.chicken());
    }

    public static FoodProperties chorusFruit() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.chorusFruit());
    }

    public static FoodProperties cod() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cod());
    }

    public static FoodProperties cookedBeef() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookedBeef());
    }

    public static FoodProperties cookedChicken() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookedChicken());
    }

    public static FoodProperties cookedCod() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookedCod());
    }

    public static FoodProperties cookedMutton() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookedMutton());
    }

    public static FoodProperties cookedPorkchop() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookedPorkchop());
    }

    public static FoodProperties cookedRabbit() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookedRabbit());
    }

    public static FoodProperties cookedSalmon() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookedSalmon());
    }

    public static FoodProperties cookie() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.cookie());
    }

    public static FoodProperties driedKelp() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.driedKelp());
    }

    public static FoodProperties enchantedGoldenApple() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.enchantedGoldenApple());
    }

    public static FoodProperties goldenApple() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.goldenApple());
    }

    public static FoodProperties goldenCarrot() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.goldenCarrot());
    }

    public static FoodProperties honeyBottle() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.honeyBottle());
    }

    public static FoodProperties melonSlice() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.melonSlice());
    }

    public static FoodProperties mushroomStew() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.mushroomStew());
    }

    public static FoodProperties mutton() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.mutton());
    }

    public static FoodProperties poisonousPotato() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.poisonousPotato());
    }

    public static FoodProperties porkchop() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.porkchop());
    }

    public static FoodProperties potato() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.potato());
    }

    public static FoodProperties pufferfish() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.pufferfish());
    }

    public static FoodProperties pumpkinPie() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.pumpkinPie());
    }

    public static FoodProperties rabbit() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.rabbit());
    }

    public static FoodProperties rabbitStew() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.rabbitStew());
    }

    public static FoodProperties rottenFlesh() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.rottenFlesh());
    }

    public static FoodProperties salmon() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.salmon());
    }

    public static FoodProperties spiderEye() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.spiderEye());
    }

    public static FoodProperties suspiciousStew() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.suspiciousStew());
    }

    public static FoodProperties sweetBerries() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.sweetBerries());
    }

    public static FoodProperties glowBerries() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.glowBerries());
    }

    public static FoodProperties tropicalFish() {
        return Foods.wrap(Foods.vapeInstance.getMappings().foods.tropicalFish());
    }

    private static FoodProperties wrap(Object handle) {
        return new FoodProperties(handle);
    }
}

