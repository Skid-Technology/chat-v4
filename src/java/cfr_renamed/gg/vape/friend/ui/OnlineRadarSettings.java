/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.friend.ui;

import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import java.awt.Color;

public class OnlineRadarSettings {
    final public ModeOption customColorMode;
    final public ColorValue enemyColor;
    final public ModeValue colorMode;
    final public ModeValue radarMode;
    final public NumberValue dotSize;
    final public ModeOption twoDimensionalRadarMode = new ModeOption("2D Radar");
    final public BooleanValue clampRadar;
    final public ColorValue friendlyColor;
    final public ModeOption circleRadarStyle;
    final public NumberValue radarSize;
    final public BooleanValue showBackground;
    final public ModeOption relationshipColorMode;
    final public NumberValue radarScale;
    final public ModeValue radarStyle;
    final public ModeOption teamColorMode;
    final public ModeOption squareDotStyle;
    final public NumberValue maxDistance;
    final public ModeValue dotStyle;
    final public NumberValue maxShown;
    final public ColorValue customColor;
    final public ModeOption textRadarMode = new ModeOption("Text Radar");
    final public BooleanValue showCross;
    final public ModeOption squareRadarStyle;
    final public ModeOption circleDotStyle;

    public OnlineRadarSettings() {
        this.radarMode = ModeValue.create((Object)this, "Mode", this.twoDimensionalRadarMode, this.twoDimensionalRadarMode, this.textRadarMode);
        this.radarSize = NumberValue.createWithDescription(this, "Radar Size", "#.#", "", 25.0, 110.0, 500.0, "The size of the radar.");
        this.dotSize = NumberValue.create(this, "Dot Size", "#.#", "", 0.5, 3.0, 10.0, 0.1, "The size of the radar.");
        this.radarScale = NumberValue.create(this, "Radar Scale", "#.##", "", 0.1, 0.5, 5.0, 0.01, "The size of the radar.");
        this.maxDistance = NumberValue.create(this, "Max Distance", "#", "m", 0.0, 0.0, 100.0, 5.0, "Maximum distance to show.\nUse 0 to ignore distance requirement.");
        this.maxShown = NumberValue.create(this, "Max Shown", "#", "", 0.0, 25.0, 50.0, 1.0, "Maximum players to show at once.\nUse 0 to show all players.");
        this.teamColorMode = new ModeOption("Team");
        this.customColorMode = new ModeOption("Custom");
        this.relationshipColorMode = new ModeOption("Relationship");
        this.colorMode = ModeValue.create((Object)this, "ColorMode1", "Color Mode", "", (ModeSelection)this.teamColorMode, this.teamColorMode, this.customColorMode, this.relationshipColorMode);
        this.circleDotStyle = new ModeOption("Circles");
        this.squareDotStyle = new ModeOption("Squares");
        this.dotStyle = ModeValue.create((Object)this, "DotStyle1", "Dot Style", "", (ModeSelection)this.circleDotStyle, this.circleDotStyle, this.squareDotStyle);
        this.squareRadarStyle = new ModeOption("Square");
        this.circleRadarStyle = new ModeOption("Circle");
        this.radarStyle = ModeValue.create((Object)this, "Radar Style", this.squareRadarStyle, this.squareRadarStyle, this.circleRadarStyle);
        this.customColor = ColorValue.create(this, "Custom Color", Color.WHITE);
        this.friendlyColor = ColorValue.create(this, "Friendly Color", Color.GREEN);
        this.enemyColor = ColorValue.create(this, "Enemy Color", Color.RED);
        this.showCross = BooleanValue.create(this, "Show Cross", true, "Render a center cross on the radar.");
        this.showBackground = BooleanValue.create(this, "Show Background", true, "Render a background behind the radar.");
        this.clampRadar = BooleanValue.create(this, "Clamp Radar", true, "Clamps dots to the given size and shape.");
        this.radarMode.addModeDependentValues(this.twoDimensionalRadarMode, this.radarSize, this.radarScale, this.colorMode, this.radarStyle, this.dotStyle, this.dotSize, this.showCross, this.clampRadar);
        this.radarMode.addModeDependentValues(this.textRadarMode, this.maxDistance, this.maxShown);
        this.colorMode.addModeDependentValues(this.customColorMode, this.customColor);
        this.colorMode.addModeDependentValues(this.relationshipColorMode, this.friendlyColor, this.enemyColor);
    }
}

