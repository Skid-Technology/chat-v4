/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.combat;

import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.AttackKeyController;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.combat.HitSwap;
import gg.vape.module.control.AttackCancellationAdapter;
import gg.vape.module.control.PhysicalAttackCancellationAdapter;
import gg.vape.module.control.SyntheticAttackCancellationAdapter;
import gg.vape.module.utility.AutoMace;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ItemLimitData;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;

public class ShieldBreaker
extends Mod {
    private static final long MODULE_ID = 7954407336301342843L;
    private final NumberValue swapBackDelay;
    private final BooleanValue doubleClick;
    private int axeSlot = -1;
    private boolean attackPending;
    private int swapDelayTicks;
    private boolean releasePending;
    private final BooleanValue limitToItems;
    private int originalSlot = -1;
    private final NumberValue swapDelay = NumberValue.create(this, "Swap delay", "#", "ticks", 0.0, 0.0, 10.0, 1.0, "Delay between swapping to an axe and attacking");
    private int swapBackDelayTicks;
    private boolean active;
    private final LimitValue allowedItems;

    public ShieldBreaker() {
        super("ShieldBreaker", -16732037, Category.UTILITY, "Swaps to an axe when attacking a player with a raised shield");
        this.swapBackDelay = NumberValue.create(this, "Swap back delay", "#", "ticks", 1.0, 2.0, 10.0, 1.0, "Delay between attacking and swapping back to the original slot");
        this.doubleClick = BooleanValue.create(this, "Double click", false, "Attacks again immediately after breaking the shield to knock the target back");
        this.limitToItems = BooleanValue.create(this, "Limit to items", false, "ShieldBreaker functions only while holding selected items");
        this.allowedItems = LimitValue.create((Object)this, "shieldbreaker-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, new ItemLimitData("swords"));
        this.limitToItems.addDependentValues(this.allowedItems);
        this.limitToItems.setCompactListValue(this.allowedItems);
        this.addValue(this.swapDelay, this.swapBackDelay, this.doubleClick, this.limitToItems, this.allowedItems);
    }

    private void handleAttack(AttackCancellationAdapter event) {
        if (event.isCancelled() || this.active || this.attackPending || Minecraft.currentScreen().isNotNull()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        EntityOtherPlayerMP target = this.getTarget();
        if (player.isNull() || !this.canUseHeldItem(player) || player.isDead() || target == null || !this.isShieldRaised(target)) {
            return;
        }
        ItemStack held = player.getHeldItemHand();
        if (this.isAxe(held)) {
            if (this.doubleClick.getEffectiveValue().booleanValue()) {
                this.releasePending = AttackKeyController.requestSyntheticAttack(this);
            }
            return;
        }
        int foundAxeSlot = this.findAxeSlot();
        if (foundAxeSlot < 0) {
            return;
        }
        event.setCancelled(true);
        this.originalSlot = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
        this.axeSlot = foundAxeSlot;
        player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.axeSlot);
        this.active = true;
        this.attackPending = true;
        this.swapDelayTicks = 0;
        this.swapBackDelayTicks = 0;
    }

    @Override
    public void onDisable() {
        if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
        }
        this.restoreSlot();
        this.reset();
    }

    private EntityOtherPlayerMP getTarget() {
        RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTrace == null || rayTrace.isNull() || rayTrace.getEntity().isNull()) {
            return null;
        }
        Entity entity = rayTrace.getEntity();
        if (!(MappedClasses.Yl != null && entity.isInstance(MappedClasses.Yl) || MappedClasses.lG != null && entity.isInstance(MappedClasses.lG))) {
            return null;
        }
        return new EntityOtherPlayerMP(entity.getObject());
    }

    private boolean isShieldRaised(EntityOtherPlayerMP target) {
        EnumHand hand = RotationUtil.q(target);
        if (hand == null) {
            return false;
        }
        ItemStack stack = target.i(hand);
        if (stack.isNull() || stack.getItem().isNull()) {
            return false;
        }
        float remaining = target.d(hand);
        if (remaining <= 0.0f) {
            return false;
        }
        int maxUse = stack.getItem().I(stack, target);
        return (float)maxUse - remaining >= 5.0f;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
        if (event.getSource() == this || event.getSource() instanceof HitSwap || event.getSource() instanceof AutoMace) {
            return;
        }
        this.handleAttack(new SyntheticAttackCancellationAdapter(event));
    }

    private boolean canAttack(EntityPlayerSP player) {
        if (Minecraft.currentScreen().isNotNull() || player.isDead() || player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v() != this.axeSlot) {
            return false;
        }
        EntityOtherPlayerMP target = this.getTarget();
        return target != null && this.isShieldRaised(target);
    }

    private int findAxeSlot() {
        InventoryPlayer inventory = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        int currentSlot = inventory.v();
        for (int slot = 0; slot < 9; ++slot) {
            if (slot == currentSlot || !this.isAxe(inventory.c(slot))) continue;
            return slot;
        }
        return -1;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onMouseButton(EventMouseButton event) {
        if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown()) {
            this.handleAttack(new PhysicalAttackCancellationAdapter(event));
        }
    }

    private void restoreSlot() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (this.active && this.originalSlot >= 0 && !player.isNull()) {
            player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.originalSlot);
        }
    }

    private boolean isAxe(ItemStack stack) {
        return stack.isNotNull() && stack.getItem().isNotNull() && MappedClasses.YP != null && stack.getItem().isInstance(MappedClasses.YP);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onKeyPress(EventKeyPress event) {
        if (event.isKeybinding(Minecraft.gameSettings().F()) && event.isDown()) {
            this.handleAttack(new PhysicalAttackCancellationAdapter(event));
        }
    }

    private void reset() {
        this.active = false;
        this.attackPending = false;
        this.releasePending = false;
        this.originalSlot = -1;
        this.axeSlot = -1;
        this.swapDelayTicks = 0;
        this.swapBackDelayTicks = 0;
    }

    private boolean canUseHeldItem(EntityPlayerSP player) {
        return this.limitToItems.getEffectiveValue() == false || this.allowedItems.isValid(player.getHeldItemHand(), false);
    }

    private void attack() {
        AttackKeyController.releaseAttackKey();
        this.releasePending = AttackKeyController.requestSyntheticAttack(this);
        if (this.doubleClick.getEffectiveValue().booleanValue() && this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = AttackKeyController.requestSyntheticAttack(this);
        }
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onTick(EventPreTick event) {
        if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
        }
        if (event.getThePlayer().isNull()) {
            this.reset();
            return;
        }
        if (this.attackPending) {
            if (!this.canAttack(event.getThePlayer())) {
                this.restoreSlot();
                this.reset();
                return;
            }
            if (this.swapDelayTicks++ >= this.swapDelay.getValue().intValue()) {
                this.attack();
                this.attackPending = false;
                this.swapBackDelayTicks = 0;
            }
        }
        if (this.active && !this.attackPending && this.swapBackDelayTicks++ >= this.swapBackDelay.getValue().intValue()) {
            this.restoreSlot();
            this.reset();
        }
    }

    private int getAxeSlot() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return -1;
        }
        InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        for (int slot = 0; slot < 9; ++slot) {
            if (!this.isAxe(inventory.c(slot))) continue;
            return slot;
        }
        return -1;
    }

    public boolean hasAxeInHotbar() {
        return this.getAxeSlot() >= 0;
    }
}

