/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.none;

import gg.vape.Vape;
import gg.vape.module.MinecraftVersionConstraint;
import gg.vape.module.Mod;
import gg.vape.module.none.ConfigSettingsModule;
import gg.vape.module.none.textgui.TextGuiModuleWidthComparator;
import gg.vape.unmap.ModeOption;
import gg.vape.utils.NameComparator;
import gg.vape.value.BooleanValue;
import gg.vape.value.ColorValue;
import gg.vape.value.ModeValue;
import gg.vape.value.ModuleNameSuggestionProvider;
import gg.vape.value.NumberValue;
import gg.vape.value.OptionalLimitValue;
import gg.vape.value.StringValue;
import java.awt.Color;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextGuiSettings
extends ConfigSettingsModule {
    final public BooleanValue smoothFont;
    final public ModeOption basicSuffixMode;
    final public BooleanValue rescale;
    final public ModeValue colorMode;
    final public ModeValue suffixMode;
    final public ModeOption sortByLength;
    final public ModeValue sortMode;
    final public BooleanValue addCustomText;
    final public ModeOption matchGuiColor;
    final public ModeOption extendedSuffixMode;
    final public NumberValue scale;
    final public BooleanValue clickDisable;
    final public ModeOption moduleColor;
    final public ColorValue textGuiColor;
    final public StringValue customText;
    final public ModeOption customColor;
    final public BooleanValue shadow;
    final public BooleanValue hideModules;
    final public ModeOption noSuffix;
    public static TextGuiSettings INSTANCE;
    final public OptionalLimitValue hiddenModules;
    final public BooleanValue customTextColorEnabled;
    final public ModeOption alphabeticalSort = new ModeOption("Alphabetical");
    final public BooleanValue watermark;
    final public ColorValue customTextColor;
    final public BooleanValue renderBackground;
    final public BooleanValue animations;

    public int getSuffixModeIndex() {
        return this.suffixMode.getValue() == this.basicSuffixMode ? 0 : (this.suffixMode.getValue() == this.extendedSuffixMode ? 1 : 2);
    }

    public String getEnabledModuleNames() {
        CopyOnWriteArrayList<Mod> copyOnWriteArrayList = new CopyOnWriteArrayList<Mod>(Vape.INSTANCE.getModManager().collectMods());
        if (this.sortMode.getValue() == this.alphabeticalSort) {
            copyOnWriteArrayList.sort(new NameComparator());
        } else if (this.sortMode.getValue() == this.sortByLength) {
            copyOnWriteArrayList.sort(new TextGuiModuleWidthComparator());
        }
        String string = "  ";
        for (Mod mod : copyOnWriteArrayList) {
            if (!mod.isEnabled() || mod.getGuiColor() == 0) continue;
            string = string + mod.getName() + ", ";
        }
        if (string.length() > 2) {
            string = string.substring(0, string.length() - 2);
        }
        return string;
    }

    public TextGuiSettings() {
        super("Text GUI");
        this.sortByLength = new ModeOption("Length");
        this.sortMode = ModeValue.create((Object)this, "Sort", this.sortByLength, this.sortByLength, this.alphabeticalSort);
        this.moduleColor = new ModeOption("Module color");
        this.matchGuiColor = new ModeOption("Match GUI color");
        this.customColor = new ModeOption("Custom color");
        this.colorMode = ModeValue.create((Object)this, "Color Mode", this.moduleColor, this.moduleColor, this.matchGuiColor, this.customColor);
        this.textGuiColor = ColorValue.create(this, "Text GUI color", new Color(206, 7, 7));
        this.clickDisable = BooleanValue.create(this, "Click disable", false, "Click modules in text gui to toggle them");
        this.shadow = BooleanValue.create(this, "Shadow", true, "Renders shadowed text");
        this.animations = BooleanValue.create(this, "Animations", true, "Use animations on text gui");
        this.watermark = BooleanValue.create(this, "Watermark", false, "Renders a vape watermark");
        this.renderBackground = BooleanValue.create(this, "Render background", true);
        this.hideModules = BooleanValue.create(this, "Hide modules", false, "Allows you to blacklist certain modules from being shown");
        this.hiddenModules = (OptionalLimitValue)OptionalLimitValue.createWithDescription(this, "module-show-blacklist", "Hidden Modules", "Name of module to hide", OptionalLimitValue.BLOCK_LIST_COLOR, Arrays.asList("ESP", "NameTags", "StorageESP")).setSuggestionProvider(new ModuleNameSuggestionProvider());
        this.rescale = BooleanValue.create(this, "Rescale", true, "Rescales text GUI");
        this.scale = NumberValue.create((Object)this, "Scale", "#.#", "", 0.1, 1.0, 2.0, 0.1);
        this.addCustomText = BooleanValue.create(this, "Add custom text", false);
        this.customText = StringValue.create(this, "Custom text", "");
        this.customTextColorEnabled = BooleanValue.create(this, "Set custom text color", false);
        this.customTextColor = ColorValue.create(this, "Color of custom text", new Color(206, 7, 7));
        this.smoothFont = BooleanValue.create(this, "Smooth font", true);
        this.basicSuffixMode = new ModeOption("Basic");
        this.extendedSuffixMode = new ModeOption("Extended");
        this.noSuffix = new ModeOption("None");
        this.suffixMode = ModeValue.create((Object)this, "Suffix mode", this.basicSuffixMode, this.basicSuffixMode, this.extendedSuffixMode, this.noSuffix);
        INSTANCE = this;
        this.addValue(this.sortMode, this.colorMode, this.textGuiColor, this.suffixMode, this.clickDisable, this.shadow, this.animations, this.watermark, this.renderBackground, this.hideModules, this.hiddenModules, this.rescale, this.scale, this.addCustomText, this.customText, this.customTextColorEnabled, this.customTextColor);
        this.U(this.smoothFont, new MinecraftVersionConstraint[0]);
        this.customText.setBase64Encoded(true);
        this.colorMode.addActiveMode(this.textGuiColor, this.customColor);
        this.hideModules.addDependentValues(this.hiddenModules);
        this.addCustomText.addDependentValues(this.customText);
        this.addCustomText.addDependentValues(this.customTextColorEnabled);
        this.customTextColorEnabled.addDependentValues(this.customTextColor);
    }
}

