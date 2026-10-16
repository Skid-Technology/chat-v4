/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.control;

import gg.vape.input.MouseButtonInputLock;
import gg.vape.input.MovementInputLock;
import gg.vape.module.control.MouseOverUpdateControlClaim;
import gg.vape.module.control.PrimaryActionControlClaim;
import gg.vape.module.control.RenderPassControlClaim;
import gg.vape.module.control.RightClickUseControlClaim;
import gg.vape.module.control.SecondaryActionControlClaim;
import gg.vape.rotation.RotationControlClaim;

public class SharedModuleControlClaims {
    final public static PrimaryActionControlClaim primaryAction = new PrimaryActionControlClaim();
    final public static MouseButtonInputLock mouseButtons = new MouseButtonInputLock();
    final public static SecondaryActionControlClaim secondaryAction = new SecondaryActionControlClaim();
    final public static RightClickUseControlClaim rightClickUse = new RightClickUseControlClaim();
    final public static MovementInputLock movementInput = new MovementInputLock();
    final public static RenderPassControlClaim renderPass = new RenderPassControlClaim();
    final public static RotationControlClaim rotation = new RotationControlClaim();
    final public static MouseOverUpdateControlClaim mouseOverUpdate = new MouseOverUpdateControlClaim();
}

