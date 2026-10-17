/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.friend;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.friend.Friend;
import gg.vape.friend.TargetEntry;
import gg.vape.friend.TargetType;

public abstract class FriendEntry
extends TargetEntry {
    private boolean targeted = true;
    private static int obfuscationState;

    public static void setFriendEntryObfuscationState(int state) {
        obfuscationState = state;
    }

    public String getAlias() {
        return this.getDisplayName();
    }

    public String getDisplayName() {
        return this.getName();
    }

    public static int getObfuscationConstant() {
        int state = FriendEntry.getFriendEntryObfuscationState();
        return 0;
    }

    public static int getFriendEntryObfuscationState() {
        return obfuscationState;
    }

    public FriendEntry() {
        super(TargetType.FRIEND);
    }

    public boolean isPersistent() {
        return true;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
        Vape.INSTANCE.getFriendManager().refreshPlayerNames();
    }

    public abstract Friend loadJson(JsonObject var1);

    public boolean isTargeted() {
        return this.targeted;
    }

    public abstract JsonObject toJson();

    public abstract String getName();

    static {
        if (FriendEntry.getFriendEntryObfuscationState() == 0) {
            FriendEntry.setFriendEntryObfuscationState(29);
        }
    }
}

