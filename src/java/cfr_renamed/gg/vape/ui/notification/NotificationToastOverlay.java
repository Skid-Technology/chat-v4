/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.notification;

import func.skidline.RectData;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.animation.DoubleAnimation;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.gui.WrappedTextComponent;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.notification.NotificationMessage;
import gg.vape.utils.MutableColor;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import java.awt.Color;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class NotificationToastOverlay
extends GuiComponent {
    TimerUtil var_gg_vape_utils_TimerUtil_I;
    private long var_long_b;
    WrappedTextComponent var_gg_vape_ui_click_component_gui_WrappedTextComponent_O;
    NotificationMessage var_gg_vape_ui_notification_NotificationMessage_Q;
    Queue<NotificationMessage> var_java_util_Queue_gg_vape_ui_notification_NotificationMessage__R = new ConcurrentLinkedQueue<NotificationMessage>();
    private boolean var_boolean_K;
    private final DoubleAnimation var_gg_vape_ui_click_animation_DoubleAnimation_i;
    FrameComponent var_gg_vape_ui_click_frame_FrameComponent_G;

    private void Y$src$V$1in99ri() {
        this.var_gg_vape_ui_click_animation_DoubleAnimation_i.Z();
        this.var_boolean_K = true;
    }

    @Override
    public double C() {
        return 0.0;
    }

    public void k(NotificationMessage notificationMessage) {
        this.R();
        this.T(notificationMessage);
    }

    private void Z$src$V$1int2cv() {
        if (this.var_gg_vape_ui_notification_NotificationMessage_Q == null) {
            return;
        }
        this.var_gg_vape_ui_click_animation_DoubleAnimation_i.setEndValue(Math.max(14.5, this.var_gg_vape_ui_click_component_gui_WrappedTextComponent_O.C() + 8.0));
        int n = (int)(255.0 * this.var_gg_vape_ui_click_animation_DoubleAnimation_i.X());
        int n2 = this.var_gg_vape_ui_click_animation_DoubleAnimation_i.l() ? n : (!this.var_gg_vape_ui_click_animation_DoubleAnimation_i.n() ? n : (int)(255.0 * this.var_gg_vape_ui_click_animation_DoubleAnimation_i.X() / 4.0));
        RectData rectData = this.getBounds();
        MutableColor mutableColor = new MutableColor(this.var_gg_vape_ui_notification_NotificationMessage_Q.t().getRed(), this.var_gg_vape_ui_notification_NotificationMessage_Q.t().getGreen(), this.var_gg_vape_ui_notification_NotificationMessage_Q.t().getBlue(), n);
        MutableColor mutableColor2 = new MutableColor(this.var_gg_vape_ui_notification_NotificationMessage_Q.Y().getRed(), this.var_gg_vape_ui_notification_NotificationMessage_Q.Y().getGreen(), this.var_gg_vape_ui_notification_NotificationMessage_Q.Y().getBlue(), n);
        double d = 6.0;
        GuiRenderPrimitives.d(rectData.o(), rectData.W(), rectData.e(), rectData.R(), (Color)mutableColor);
        GuiRenderPrimitives.P(rectData.o() - 0.5, rectData.W() - 0.5, rectData.e() + 1.0, rectData.R() + 1.0, mutableColor2, 2.0f, 1.0f, 1.0f);
        this.var_gg_vape_ui_click_component_gui_WrappedTextComponent_O.K(rectData.o() + d);
        this.var_gg_vape_ui_click_component_gui_WrappedTextComponent_O.S(Math.min(rectData.W() + 4.0, this.var_gg_vape_ui_click_frame_FrameComponent_G.n() + this.var_gg_vape_ui_click_frame_FrameComponent_G.L() - 6.0));
        MutableColor mutableColor3 = new MutableColor(this.var_gg_vape_ui_notification_NotificationMessage_Q.Y().darker().getRGB(), n2);
        this.var_gg_vape_ui_click_component_gui_WrappedTextComponent_O.setTextColor(mutableColor3);
        RenderUtils.m(rectData.o(), rectData.W(), rectData.e(), rectData.R());
        this.var_gg_vape_ui_click_component_gui_WrappedTextComponent_O.c();
        RenderUtils.T();
    }

    private void T(NotificationMessage notificationMessage) {
        this.var_gg_vape_ui_notification_NotificationMessage_Q = notificationMessage;
        this.var_gg_vape_ui_click_component_gui_WrappedTextComponent_O = new WrappedTextComponent(this.var_gg_vape_ui_notification_NotificationMessage_Q.S(), 0.8f);
        this.var_gg_vape_ui_click_component_gui_WrappedTextComponent_O.setWrapWidth(this.var_gg_vape_ui_click_frame_FrameComponent_G.A() - 14.0);
        this.var_gg_vape_ui_click_animation_DoubleAnimation_i.c();
    }

    @Override
    public double x() {
        return 0.0;
    }

    private boolean L$src$Z$1ig3y59() {
        if (this.k().l()) {
            return false;
        }
        return !this.var_java_util_Queue_gg_vape_ui_notification_NotificationMessage__R.isEmpty();
    }

    private boolean R$src$Z$1ijeppf() {
        return this.var_long_b == Long.MAX_VALUE || this.var_long_b + (long)this.var_gg_vape_ui_notification_NotificationMessage_Q.c() < System.currentTimeMillis();
    }

    public NotificationToastOverlay(FrameComponent frameComponent) {
        this.var_long_b = Long.MAX_VALUE;
        this.var_gg_vape_utils_TimerUtil_I = new TimerUtil();
        this.var_gg_vape_ui_click_animation_DoubleAnimation_i = new DoubleAnimation(0.3, 0.0, 14.5);
        this.var_gg_vape_ui_click_frame_FrameComponent_G = frameComponent;
        this.Y(0.0);
    }

    private void t(long l) {
        this.var_long_b = l;
    }

    @Override
    public void H() {
        this.Z$src$V$1int2cv();
    }

    @Override
    public void c() {
        this.Y(0.0);
        super.c();
    }

    @Override
    public void I() {
    }

    public int C(NotificationMessage notificationMessage) {
        this.var_java_util_Queue_gg_vape_ui_notification_NotificationMessage__R.add(notificationMessage);
        return this.var_java_util_Queue_gg_vape_ui_notification_NotificationMessage__R.size() - 1;
    }

    @Override
    public void u() {
        if (!this.var_gg_vape_utils_TimerUtil_I.hasTimeElapsed(0L)) {
            return;
        }
        if (this.var_boolean_K && !this.var_gg_vape_ui_click_animation_DoubleAnimation_i.l()) {
            this.var_gg_vape_utils_TimerUtil_I.reset();
            this.var_gg_vape_ui_notification_NotificationMessage_Q = null;
            this.t(Long.MAX_VALUE);
            this.var_boolean_K = false;
            return;
        }
        if (this.var_gg_vape_ui_notification_NotificationMessage_Q == null) {
            if (!this.var_java_util_Queue_gg_vape_ui_notification_NotificationMessage__R.isEmpty()) {
                this.T(this.var_java_util_Queue_gg_vape_ui_notification_NotificationMessage__R.remove());
            }
            return;
        }
        if (this.w$src$J$1j3r3a0() == Long.MAX_VALUE) {
            if (this.k().N()) {
                this.s();
            }
            return;
        }
        if (!this.var_boolean_K && (this.R$src$Z$1ijeppf() || this.L$src$Z$1ig3y59())) {
            this.Y$src$V$1in99ri();
        }
    }

    public Queue<NotificationMessage> z() {
        return this.var_java_util_Queue_gg_vape_ui_notification_NotificationMessage__R;
    }

    public long w$src$J$1j3r3a0() {
        return this.var_long_b;
    }

    @Override
    public void g(GuiMouseEvent guiMouseEvent) {
    }

    public void R() {
        this.var_gg_vape_ui_notification_NotificationMessage_Q = null;
        this.t(Long.MAX_VALUE);
        this.var_boolean_K = false;
    }

    @Override
    public void F() {
    }

    private void s() {
        this.var_long_b = System.currentTimeMillis();
    }

    @Override
    public RectData getBounds() {
        return new RectData(this.var_gg_vape_ui_click_frame_FrameComponent_G.G$src$D$1b2f02a() + 2.5, this.var_gg_vape_ui_click_frame_FrameComponent_G.n() + this.var_gg_vape_ui_click_frame_FrameComponent_G.L() - this.var_gg_vape_ui_click_animation_DoubleAnimation_i.getInterpolatedValue(), this.var_gg_vape_ui_click_frame_FrameComponent_G.A() - 5.0, Math.max(Math.max(this.var_gg_vape_ui_click_animation_DoubleAnimation_i.getInterpolatedValue() - 0.5, 4.0), 0.0));
    }

    public DoubleAnimation k() {
        return this.var_gg_vape_ui_click_animation_DoubleAnimation_i;
    }
}

