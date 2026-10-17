/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.unmap;

import gg.vape.Vape;
import gg.vape.config.PublicProfileSettings;
import gg.vape.module.Mod;
import gg.vape.module.SubModule;
import gg.vape.module.none.ClientSettings;
import gg.vape.notification.NotificationType;
import gg.vape.notification.ReusableTextNotification;
import gg.vape.unmap.Bendable;
import gg.vape.unmap.BendableInputDispatcher;
import gg.vape.unmap.ModeSelection;
import gg.vape.value.BooleanValue;
import gg.vape.value.ModeValue;
import gg.vape.value.Value;

public class ValueBendable
extends Bendable {
    private final Value<?, ?> value;
    private final boolean allowed;

    public ValueBendable(Value<?, ?> value, boolean allowed) {
        this.value = value;
        this.allowed = allowed;
        BendableInputDispatcher.register(this);
    }

    @Override
    public boolean activateIfMatched(int inputCode) {
        if (ClientSettings.INSTANCE != null && !ClientSettings.INSTANCE.allowSettingKeybinds.isEnabled()) {
            return false;
        }
        if (this.value instanceof BooleanValue && !((BooleanValue)this.value).isBindable()) {
            return false;
        }
        if (this.value instanceof ModeValue && !((ModeValue)this.value).isBindable()) {
            return false;
        }
        return super.activateIfMatched(inputCode);
    }

    @Override
    public void onBindActivated() {
        if (!this.allowed) {
            return;
        }
        if (ClientSettings.INSTANCE != null && !ClientSettings.INSTANCE.allowSettingKeybinds.isEnabled()) {
            return;
        }
        if (this.value instanceof BooleanValue) {
            BooleanValue boolVal = (BooleanValue)this.value;
            if (!boolVal.isBindable()) {
                return;
            }
            boolVal.setValue((Boolean)boolVal.getValue() == false);
            this.dispatchToggleAlert(boolVal.getName() + " " + ((Boolean)boolVal.getValue() != false ? "\u00a72ON" : "\u00a7cOFF"));
        } else if (this.value instanceof ModeValue) {
            ModeValue modeVal = (ModeValue)this.value;
            if (!modeVal.isBindable()) {
                return;
            }
            int nextIndex = (modeVal.getSelectedIndex() + 1) % modeVal.getModes().length;
            modeVal.setValue(modeVal.getModes()[nextIndex]);
            this.dispatchToggleAlert(modeVal.getName() + " " + ((ModeSelection)modeVal.getValue()).getName());
        }
    }

    private void dispatchToggleAlert(String message) {
        PublicProfileSettings settings = Vape.INSTANCE.getPublicProfileSettings();
        if (settings == null || !settings.notifications.getEffectiveValue().booleanValue() || !settings.settingToggleAlerts.getEffectiveValue().booleanValue()) {
            return;
        }
        if (ClientSettings.INSTANCE == null || !ClientSettings.INSTANCE.allowSettingKeybinds.isEnabled()) {
            return;
        }
        Mod mod = null;
        if (this.value.getOwner() instanceof Mod && (mod = (Mod)this.value.getOwner()) instanceof SubModule) {
            mod = ((SubModule)mod).getParent();
        }
        if (mod == null) {
            return;
        }
        String title = "\u00a7f" + mod.getName();
        ReusableTextNotification notification = new ReusableTextNotification(NotificationType.INFO, title, message, 1000L);
        Vape.INSTANCE.getNotificationManager().show(notification);
    }

    @Override
    public boolean isActive() {
        if (this.value instanceof BooleanValue) {
            return (Boolean)((BooleanValue)this.value).getValue();
        }
        return false;
    }

    @Override
    public String getDisplayText() {
        return this.value.getName();
    }
}

