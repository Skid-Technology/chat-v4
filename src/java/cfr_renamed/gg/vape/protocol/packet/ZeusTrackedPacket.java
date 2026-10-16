/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.ZeusSerializablePacket;
import gg.vape.ui.click.component.GuiComponent;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public abstract class ZeusTrackedPacket<R extends ZeusTrackedPacket>
implements ZeusSerializablePacket {
    private UUID var_java_util_UUID_A;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_p;

    @Override
    public final void S(ZeusPacketBuffer gx_12) {
        this.var_java_util_UUID_A = gx_12.readUuid();
        this.x(gx_12);
    }

    public abstract void T(ZeusPacketBuffer var1);

    public static void B(GuiComponent[] upArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_p = upArray;
    }

    public static GuiComponent[] q$src$ALgg_vape_ui_click_component_GuiComponent_$1592yx4() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_p;
    }

    public abstract void x(ZeusPacketBuffer var1);

    @Override
    public final void o(ZeusPacketBuffer gx_12) {
        gx_12.writeUuid(this.var_java_util_UUID_A);
        this.T(gx_12);
    }

    public ZeusTrackedPacket(@Nullable R r) {
        this.var_java_util_UUID_A = r != null ? ((ZeusTrackedPacket)r).o$src$Ljava_util_UUID_$1pm4r8s() : UUID.randomUUID();
    }

    public ZeusTrackedPacket() {
        this.var_java_util_UUID_A = UUID.randomUUID();
    }

    public UUID o$src$Ljava_util_UUID_$1pm4r8s() {
        return this.var_java_util_UUID_A;
    }

    static {
        if (ZeusTrackedPacket.q$src$ALgg_vape_ui_click_component_GuiComponent_$1592yx4() == null) {
            ZeusTrackedPacket.B(new GuiComponent[3]);
        }
    }
}

