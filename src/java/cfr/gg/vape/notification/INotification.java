/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.notification;

import gg.vape.notification.NotificationType;

public interface INotification {
    public void render();

    public void setTargetX(double var1);

    public void dismiss();

    public void handleClick(double var1, double var3);

    public double getCurrentY();

    public double getTargetY();

    public void setTargetY(double var1);

    public void setCurrentX(double var1);

    public void setCurrentY(double var1);

    public double getWidth();

    public double getHeight();

    public NotificationType getType();

    public double getCurrentX();

    public boolean shouldRemove();

    public double getTargetX();
}

