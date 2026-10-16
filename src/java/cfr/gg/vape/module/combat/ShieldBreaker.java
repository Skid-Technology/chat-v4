/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.combat;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.AttackKeyController;
import gg.vape.mapping.ItemMappingEntry;
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
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.LimitValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;

public class ShieldBreaker
extends Mod {
    private static final long MODULE_ID = 7954407336301342843L;
    private final NumberValue swapDelay = NumberValue.create(this, "Swap delay", "#", "ticks", 0.0, 0.0, 10.0, 1.0, "Delay between swapping to an axe and attacking");
    private final NumberValue swapBackDelay = NumberValue.create(this, "Swap back delay", "#", "ticks", 1.0, 2.0, 10.0, 1.0, "Delay between attacking and swapping back to the original slot");
    private final BooleanValue doubleClick = BooleanValue.create(this, "Double click", false, "Attacks again immediately after breaking the shield to knock the target back");
    private final BooleanValue limitToItems = BooleanValue.create(this, "Limit to items", false, "ShieldBreaker functions only while holding selected items");
    private final LimitValue allowedItems = LimitValue.create((Object)this, "shieldbreaker-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, new ItemLimitData("swords"));
    private boolean active;
    private boolean attackPending;
    private boolean releasePending;
    private boolean doubleClickPending;
    private int originalSlot = -1;
    private int axeSlot = -1;
    private int swapDelayTicks;
    private int swapBackDelayTicks;

    public ShieldBreaker() {
        super("ShieldBreaker", -16732037, Category.UTILITY, "Swaps to an axe when attacking a player with a raised shield");
        this.limitToItems.addDependentValues(this.allowedItems);
        this.addValue(this.swapDelay, this.swapBackDelay, this.doubleClick, this.limitToItems, this.allowedItems);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onMouseButton(EventMouseButton event) {
        if (!this.isEnabled()) {
            return;
        }
        if (!event.isKeybinding(Minecraft.gameSettings().F()) || !event.isDown() || event.isCanceled()) {
            return;
        }
        this.handleAttack(new PhysicalAttackCancellationAdapter(event));
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onKeyPress(EventKeyPress event) {
        if (!this.isEnabled()) {
            return;
        }
        if (!event.isKeybinding(Minecraft.gameSettings().F()) || !event.isDown() || event.isCanceled()) {
            return;
        }
        this.handleAttack(new PhysicalAttackCancellationAdapter(event));
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onSyntheticAttack(SyntheticAttackRequestEvent event) {
        if (!this.isEnabled()) {
            return;
        }
        if (event.isCanceled() || event.getSource() == this || event.getSource() instanceof HitSwap || event.getSource() instanceof AutoMace) {
            return;
        }
        this.handleAttack(new SyntheticAttackCancellationAdapter(event));
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onTick(EventPreTick event) {
        EntityPlayerSP player;
        if (!this.isEnabled()) {
            if (this.releasePending) {
                AttackKeyController.releaseAttackKey();
                this.releasePending = false;
            }
            this.reset();
            return;
        }
        if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
        }
        if ((player = event.getThePlayer()).isNull()) {
            this.reset();
            return;
        }
        if (this.doubleClickPending) {
            this.doubleClickPending = false;
            AttackKeyController.releaseAttackKey();
            this.releasePending = AttackKeyController.requestSyntheticAttack(this);
            this.swapBackDelayTicks = 0;
            return;
        }
        if (this.attackPending) {
            if (!this.canAttack(player)) {
                this.restoreSlot();
                this.reset();
                return;
            }
            if (this.swapDelayTicks++ >= this.swapDelay.getValue().intValue()) {
                this.attack();
                this.attackPending = false;
                this.swapBackDelayTicks = 0;
            }
        } else if (this.active && this.swapBackDelayTicks++ >= this.swapBackDelay.getValue().intValue()) {
            this.restoreSlot();
            this.reset();
        }
    }

    private void handleAttack(AttackCancellationAdapter event) {
        int currentSlot;
        if (this.active || this.attackPending) {
            return;
        }
        if (Minecraft.currentScreen().isNotNull()) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull() || !this.canUseHeldItem(player) || player.l$src$Z$1io4duf()) {
            return;
        }
        EntityLivingBase target = this.getTarget();
        if (target == null || target.isNull() || !this.isShieldBlocking(target)) {
            return;
        }
        InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        if (this.isAxe(inventory.c(currentSlot = inventory.v()))) {
            if (this.doubleClick.getEffectiveValue().booleanValue()) {
                this.active = true;
                this.doubleClickPending = true;
                this.originalSlot = -1;
                this.swapBackDelayTicks = 0;
            }
            return;
        }
        int foundAxeSlot = this.findAxeSlot(inventory);
        if (foundAxeSlot < 0) {
            return;
        }
        event.setCancelled(true);
        this.originalSlot = currentSlot;
        this.axeSlot = foundAxeSlot;
        inventory.g(this.axeSlot);
        this.active = true;
        this.attackPending = true;
        this.swapDelayTicks = 0;
        this.swapBackDelayTicks = 0;
    }

    private void attack() {
        AttackKeyController.releaseAttackKey();
        this.releasePending = AttackKeyController.requestSyntheticAttack(this);
        if (this.doubleClick.getEffectiveValue().booleanValue() && this.releasePending) {
            this.doubleClickPending = true;
        }
    }

    private boolean canAttack(EntityPlayerSP player) {
        if (Minecraft.currentScreen().isNotNull() || player.l$src$Z$1io4duf()) {
            return false;
        }
        InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        if (inventory.v() != this.axeSlot) {
            return false;
        }
        EntityLivingBase target = this.getTarget();
        return target != null && !target.isNull() && this.isShieldBlocking(target);
    }

    private void restoreSlot() {
        if (!this.active || this.originalSlot < 0) {
            return;
        }
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player.isNull()) {
            return;
        }
        InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        inventory.g(this.originalSlot);
    }

    private void reset() {
        this.active = false;
        this.attackPending = false;
        this.releasePending = false;
        this.doubleClickPending = false;
        this.originalSlot = -1;
        this.axeSlot = -1;
        this.swapDelayTicks = 0;
        this.swapBackDelayTicks = 0;
    }

    @Override
    public void onDisable() {
        if (this.releasePending) {
            AttackKeyController.releaseAttackKey();
            this.releasePending = false;
        }
        this.restoreSlot();
        this.reset();
    }

    private EntityLivingBase getTarget() {
        RayTraceResult rayTrace = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (rayTrace == null || rayTrace.isNull() || !rayTrace.getTypeOfHit().equals(RayTraceResult_type.entity())) {
            return null;
        }
        Entity entity = rayTrace.getEntity();
        if (entity == null || entity.isNull()) {
            return null;
        }
        if (!entity.isInstance(MappedClasses.zm)) {
            return null;
        }
        return new EntityLivingBase(entity.getObject());
    }

    public boolean isShieldBlocking(EntityLivingBase target) {
        if (target == null || target.isNull()) {
            return false;
        }
        EntityOtherPlayerMP targetPlayer = new EntityOtherPlayerMP(target.getObject());
        return RotationUtil.isBlockingShield(targetPlayer);
    }

    public static boolean isBlockedByShield(EntityPlayerSP player, Entity target) {
        if (player == null || player.isNull() || target == null || target.isNull()) {
            return false;
        }
        if (!target.isInstance(MappedClasses.zm)) {
            return false;
        }
        EntityOtherPlayerMP targetPlayer = new EntityOtherPlayerMP(target.getObject());
        return RotationUtil.isBlockingShield(targetPlayer);
    }

    public boolean canBreakShield(EntityPlayerSP player, Entity target) {
        if (!this.isEnabled() || player == null || player.isNull() || !this.canUseHeldItem(player)) {
            return false;
        }
        if (!this.hasAxeInHotbar()) {
            return false;
        }
        if (target == null || target.isNull() || !target.isInstance(MappedClasses.zm)) {
            return false;
        }
        return this.isShieldBlocking(new EntityLivingBase(target.getObject()));
    }

    public boolean hasAxeInHotbar() {
        EntityPlayerSP player = Minecraft.thePlayer();
        if (player == null || player.isNull()) {
            return false;
        }
        InventoryPlayer inventory = player.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        for (int slot = 0; slot < 9; ++slot) {
            if (!this.isAxe(inventory.c(slot))) continue;
            return true;
        }
        return false;
    }

    public boolean isSwapActive() {
        return this.active || this.attackPending;
    }

    private int findAxeSlot(InventoryPlayer inventory) {
        if (inventory == null || inventory.isNull()) {
            return -1;
        }
        int currentSlot = inventory.v();
        for (int slot = 0; slot < 9; ++slot) {
            if (slot == currentSlot || !this.isAxe(inventory.c(slot))) continue;
            return slot;
        }
        return -1;
    }

    private boolean isAxe(ItemStack stack) {
        if (stack == null || stack.isNull() || stack.getItem().isNull()) {
            return false;
        }
        if (ItemStackScoreUtil.T(stack.getItem())) {
            return true;
        }
        ItemMappingEntry entry = Vape.INSTANCE.getItemStackResolver().resolve(stack);
        if (entry != null) {
            String key = entry.getResourceKey();
            if (key != null && key.contains("axe")) {
                return true;
            }
            String modern = entry.getModernId();
            if (modern != null && modern.contains("axe")) {
                return true;
            }
        }
        try {
            String className = stack.getItem().getObject().getClass().getName().toLowerCase();
            if (className.contains("axe")) {
                return true;
            }
        }
        catch (Throwable className) {
            // empty catch block
        }
        String unlocalizedName = stack.getItem().A();
        if (unlocalizedName != null && unlocalizedName.toLowerCase().contains("axe")) {
            return true;
        }
        try {
            String str = stack.getItem().getObject().toString().toLowerCase();
            if (str.contains("axe")) {
                return true;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return false;
    }

    private boolean canUseHeldItem(EntityPlayerSP player) {
        return this.limitToItems.getEffectiveValue() == false || this.allowedItems.isValid(player.getHeldItemHand(), false);
    }

    public boolean isActive() {
        return this.isEnabled() && (this.active || this.attackPending);
    }
}

