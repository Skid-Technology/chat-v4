/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.module.utility;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.EventPriority;
import gg.vape.event.impl.EventKeyPress;
import gg.vape.event.impl.EventMouseButton;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.SyntheticAttackRequestEvent;
import gg.vape.input.AttackKeyController;
import gg.vape.input.InputEventDispatcher;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.blatant.blockin.BlockPathPlanner;
import gg.vape.module.blatant.blockin.BlockPlacementGraph;
import gg.vape.module.combat.HitSwap;
import gg.vape.module.combat.Reach;
import gg.vape.module.combat.ShieldBreaker;
import gg.vape.module.combat.automace.AutoMaceRotationController;
import gg.vape.module.control.AttackCancellationAdapter;
import gg.vape.module.control.PhysicalAttackCancellationAdapter;
import gg.vape.module.control.SharedModuleControlClaims;
import gg.vape.module.control.SyntheticAttackCancellationAdapter;
import gg.vape.rotation.RotationControlClaim;
import gg.vape.rotation.RotationManager;
import gg.vape.unmap.ItemLimitData;
import gg.vape.unmap.ModeOption;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.AttackCooldownUtil;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.utils.MathUtil;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.value.BooleanValue;
import gg.vape.value.EntityTargetFilterValue;
import gg.vape.value.LimitValue;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.RandomValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.InventoryPlayer;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.MonsterAttributesBridge;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.WorldClient;
import java.util.ArrayList;
import java.util.Arrays;

public class AutoMace
extends Mod {
    public static final ModeOption SELECTION_MANUAL = new ModeOption("Manual");
    public static final ModeOption SELECTION_AUTO = new ModeOption("Auto");
    public static final ModeOption TYPE_DENSITY = new ModeOption("Density");
    public static final ModeOption TYPE_BREACH = new ModeOption("Breach");
    public final ModeValue Ol;
    private BlockPlacementGraph x;
    private boolean Y;
    private AutoMaceRotationController t;
    private final RandomValue d;
    public final EntityTargetFilterValue Or;
    private boolean P;
    public final BooleanValue Od;
    private final TimerUtil O6;
    private boolean p;
    public final LimitValue OA;
    private Item y;
    private final RotationControlClaim O5;
    public final BooleanValue b;
    public final BooleanValue k;
    private final BooleanValue Z;
    private int K;
    private int l;
    private int Ok = -1;
    private boolean a;
    public final BooleanValue w = BooleanValue.create(this, "Smash only", true, "Only swap to mace if will smash");
    private int v = -1;
    private final NumberValue S;
    private int A = -1;
    public final BooleanValue O;
    private int OP = -1;
    public final BooleanValue G;
    private ElytraState Oi;
    private final NumberValue OX;
    public final BooleanValue OY;
    private boolean r;
    private boolean Oz;
    private boolean O4;
    public final ModeValue q = ModeValue.create((Object)this, "Mace selection", "Manual uses Mace type. Auto chooses the best mace enchantment for your fall distance and target armor.", (ModeSelection)SELECTION_MANUAL, SELECTION_MANUAL, SELECTION_AUTO);
    private int Oj;
    private int e = -1;

    public AutoMace() {
        super("AutoMace", -16732037, Category.UTILITY);
        this.Ol = ModeValue.create((Object)this, "Mace type", "Selects which mace enchantment AutoMace should use. Bind this setting to cycle it in game.", (ModeSelection)TYPE_DENSITY, TYPE_DENSITY, TYPE_BREACH).setBindable(true);
        this.k = BooleanValue.create(this, "Stun slam", false, "When holding an axe and attacking a shielded player:\nHits with axe first (breaks shield), then swaps to mace for a follow-up slam");
        this.S = NumberValue.createWithDescription(this, "Chance", "#", "%", 0.0, 100.0, 100.0, "Chance that Stun slam will trigger");
        this.Or = EntityTargetFilterValue.createForModule(this);
        this.b = BooleanValue.create(this, "Aim", false, "Aims at the nearest valid target while falling for a smash attack");
        this.O = BooleanValue.create(this, "Silent aim", false, "Uses Silent Aim system");
        this.OX = NumberValue.create(this, "Aim range", "#.#", "", 2.0, 6.0, 10.0, 0.1, "Maximum horizontal distance to search for targets");
        this.G = BooleanValue.create(this, "Attack", false, "Automatically attacks valid mace targets");
        this.OY = BooleanValue.create(this, "Auto unequip Elytra", false, "Equips a hotbar chestplate when your predicted fall can reach a mace target");
        this.Z = BooleanValue.create(this, "Re-equip Elytra", false, "Puts the Elytra back on after upward mace bounce movement is detected");
        this.d = RandomValue.createWithDescription(this, "Extra delay", "#", "ticks", -20.0, 0.0, 0.0, 20.0, 0.1, "Extra delay after attack cooldown(in ticks)\nNegative values will attack before cooldown is complete");
        this.Od = BooleanValue.create(this, "Limit to items", false);
        this.OA = LimitValue.create((Object)this, "am-alloweditems", "Allowed Items", LimitValue.ALLOW_LIST_COLOR, Arrays.asList(new ItemLimitData("swords")));
        this.O6 = new TimerUtil();
        this.O5 = SharedModuleControlClaims.rotation;
        this.Oi = ElytraState.IDLE;
        this.addValue(this.Or, this.b, this.O, this.OX, this.G, this.d, this.OY, this.Z, this.w, this.q, this.Ol, this.k, this.S, this.Od, this.OA);
        this.b.addDependentValues(this.O, this.OX);
        this.G.addDependentValues(this.d);
        this.OY.addDependentValues(this.Z);
        this.q.addModeDependentValues(SELECTION_MANUAL, this.Ol);
        this.k.addDependentValues(this.S);
        this.Od.addDependentValues(this.OA);
        this.O5.setPriority(this, 5);
    }

    private boolean e(EntityPlayerSP bm_12, WorldClient pv_12) {
        if (!this.OY.getEffectiveValue().booleanValue()) {
            this.J(bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6());
            this.a(true);
            return bm_12.isElytraFlying();
        }
        InventoryPlayer ph_12 = bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        if (this.U$src$Z$acgo8d(false)) {
            return this.X(bm_12, ph_12);
        }
        if (this.U$src$Z$acgo8d(true)) {
            this.M(bm_12, ph_12);
            return bm_12.isElytraFlying();
        }
        if (!bm_12.isElytraFlying()) {
            this.g(bm_12);
            this.a(false);
            return false;
        }
        if (this.Oi == ElytraState.WAITING_FOR_GLIDE_END) {
            if (++this.K > 8) {
                this.Oi = ElytraState.ABORTED_THIS_FLIGHT;
            }
            return true;
        }
        if (this.Oi == ElytraState.ABORTED_THIS_FLIGHT) {
            return true;
        }
        if (pv_12.isNull()) {
            return true;
        }
        if (this.x == null) {
            return true;
        }
        if (this.P) {
            return true;
        }
        if (this.a) {
            return true;
        }
        if (bm_12.isDead()) {
            return true;
        }
        if (bm_12.b$src$Z$fqlxe4() || bm_12.f$src$Z$fst3rk() || bm_12.h$src$Z$ftwoya() || bm_12.S$src$Z$151gttj() || bm_12.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
            return true;
        }
        if (!this.canUseHeldItem()) {
            return true;
        }
        if (!this.canOperate()) {
            return true;
        }
        if (!this.X$src$Z$15meugg()) {
            return true;
        }
        int n3 = this.b(ph_12);
        if (n3 < 0) {
            return true;
        }
        if (SharedModuleControlClaims.rightClickUse.isClaimed()) {
            return true;
        }
        ImpactPrediction fb_22 = this.I(bm_12, pv_12, this.l$src$D$15xepsq());
        if (fb_22 == null) {
            return true;
        }
        this.e = ph_12.v();
        this.v = n3;
        this.p = false;
        this.l = 0;
        ph_12.g(n3);
        this.O4 = false;
        return true;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPhysicalAttack(SyntheticAttackRequestEvent wF) {
        if (wF.getSource() instanceof HitSwap || wF.getSource() instanceof ShieldBreaker) {
            return;
        }
        if (wF.getSource() == this) {
            return;
        }
        this.O6.reset();
        this.K(new SyntheticAttackCancellationAdapter(wF));
    }

    private boolean X$src$Z$15meugg() {
        InventoryPlayer ph_12 = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        for (int k = 0; k < 9; ++k) {
            ItemStack pk_02 = ph_12.c(k);
            if (pk_02.isNull() || pk_02.getItem().isNull() || MappedClasses.zx == null || !pk_02.getItem().isInstance(MappedClasses.zx)) continue;
            return true;
        }
        return false;
    }

    private boolean canOperate() {
        return this.canUseHeldItem() && InputEventDispatcher.getInstance().getFocusState().isFocused();
    }

    private boolean U$src$Z$acgo8d(boolean bl) {
        return this.v >= 0 && this.p == bl;
    }

    private void a(boolean bl) {
        this.Oi = ElytraState.IDLE;
        this.K = 0;
        this.O4 = false;
        if (bl) {
            this.r = false;
            this.Y = false;
            this.d$src$V$15t0dhc();
        }
    }

    private double o(ItemStack pk_02, boolean bl, EntityLivingBase bj_12) {
        if (this.w.getEffectiveValue().booleanValue() && !this.isFallingForSmash(Minecraft.thePlayer())) {
            return -1.0;
        }
        if (pk_02.isNull() || pk_02.getItem().isNull()) {
            return -1.0;
        }
        Item pd_12 = pk_02.getItem();
        if (MappedClasses.zx == null || !pd_12.isInstance(MappedClasses.zx)) {
            return -1.0;
        }
        if (!bl) {
            return 0.0;
        }
        int n3 = EnchantmentHelper.e("density", pk_02);
        int n4 = EnchantmentHelper.e("breach", pk_02);
        if (this.N$src$Z$15gwwiu()) {
            return this.d(n3, n4, bj_12);
        }
        if (this.x$src$Z$16409g0() && n3 > 0) {
            return 0.0;
        }
        if (this.m$src$Z$15xyix1() && n4 > 0) {
            return 0.0;
        }
        return -1.0;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onMouseButton(EventMouseButton wp_12) {
        if (wp_12.isKeybinding(Minecraft.gameSettings().F()) && wp_12.getButtonState()) {
            this.O6.reset();
            this.K(new PhysicalAttackCancellationAdapter(wp_12));
        }
    }

    private boolean f(EntityPlayerSP bm_12, EntityLivingBase bj_12, double d) {
        double d3;
        double d4;
        if (!this.isFallingForSmash(bm_12)) {
            return false;
        }
        double d5 = bm_12.z();
        return AutoMace.j(d5, d4 = bm_12.N() + (double)bm_12.X(), d3 = bm_12.h(), bj_12, 0.0, 0.0, 0.0) <= d;
    }

    private boolean x$src$Z$16409g0() {
        return ((ModeSelection)this.Ol.getValue()).equals(TYPE_DENSITY);
    }

    @Override
    public void onDisable() {
        EntityPlayerSP bm_12 = Minecraft.thePlayer();
        if (bm_12.isNotNull()) {
            this.J(bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6());
        }
        this.x$src$V$16409ck();
        this.x = null;
        if (this.a) {
            AttackKeyController.releaseAttackKey();
        }
        this.a = false;
        this.P = false;
        this.Ok = -1;
        this.Oj = 0;
        this.Oz = false;
        this.OP = -1;
        this.A = -1;
        this.a(true);
    }

    private boolean t() {
        InventoryPlayer ph_12 = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        for (int k = 0; k < 9; ++k) {
            ItemStack pk_02 = ph_12.c(k);
            if (!this.r(pk_02)) continue;
            return true;
        }
        return false;
    }

    private double l$src$D$15xepsq() {
        Reach reach = Vape.INSTANCE.getModManager().getMod(Reach.class);
        return reach == null ? 3.0 : reach.getReachDistance();
    }

    private boolean s(EntityPlayerSP bm_12, EntityLivingBase bj_12) {
        if (MappedClasses.FT != null && bj_12.isInstance(MappedClasses.FT)) {
            return false;
        }
        if (bj_12.equals(bm_12) || bj_12.isDead() || bj_12.getHealth() <= 0.0f) {
            return false;
        }
        if (bj_12.equals(bm_12.getRidingEntity()) || Vape.INSTANCE.getFriendManager().isFriend(bj_12)) {
            return false;
        }
        if (!this.Or.isValidTarget(bj_12)) {
            return false;
        }
        return bj_12.N() <= bm_12.N() + 1.0;
    }

    private boolean P$src$Z$15i0hpk() {
        double d = this.S.getValue();
        if (d <= 0.0) {
            return false;
        }
        if (d >= 100.0) {
            return true;
        }
        return d > Math.random() * 100.0;
    }

    public boolean d() {
        int n3 = this.j(this.S$src$La_BJ_$ugqo7s());
        return n3 >= 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void M(EntityPlayerSP bm_12, InventoryPlayer ph_12) {
        if (ph_12.v() != this.v) {
            this.d$src$V$15t0dhc();
            this.r = false;
            this.Y = false;
            return;
        }
        int n3 = this.l + 1;
        if (n3 == 2) {
            if (bm_12.isDead() || SharedModuleControlClaims.rightClickUse.isClaimed()) {
                return;
            }
            SharedModuleControlClaims.rightClickUse.blockUse();
            try {
                Minecraft.S();
            }
            finally {
                SharedModuleControlClaims.rightClickUse.setClaimed(false);
            }
        }
        this.l = n3;
        if (this.l < 4) {
            return;
        }
        this.J(ph_12);
        this.r = false;
        this.Y = false;
    }

    private boolean S(EntityPlayerSP bm_12, int n3) {
        if (n3 < 0 || n3 >= 9) {
            return false;
        }
        ItemStack pk_02 = bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().c(n3);
        return pk_02.isNotNull() && pk_02.getItem().isNotNull() && MappedClasses.zx != null && pk_02.getItem().isInstance(MappedClasses.zx);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean X(EntityPlayerSP bm_12, InventoryPlayer ph_12) {
        if (ph_12.v() != this.v) {
            this.d$src$V$15t0dhc();
            this.Oi = this.O4 ? ElytraState.WAITING_FOR_GLIDE_END : ElytraState.ABORTED_THIS_FLIGHT;
            this.K = 0;
            return bm_12.isElytraFlying();
        }
        if (!bm_12.isElytraFlying() && this.l < 2) {
            this.J(ph_12);
            this.a(false);
            return false;
        }
        int n3 = this.l + 1;
        if (n3 == 2) {
            if (bm_12.isDead() || SharedModuleControlClaims.rightClickUse.isClaimed()) {
                return true;
            }
            SharedModuleControlClaims.rightClickUse.blockUse();
            try {
                Minecraft.S();
            }
            finally {
                SharedModuleControlClaims.rightClickUse.setClaimed(false);
            }
            this.O4 = this.c(ph_12.c(this.v));
            this.r = this.O4 && this.Z.getEffectiveValue() != false;
            this.Y = false;
        }
        this.l = n3;
        if (this.l < 4) {
            return true;
        }
        this.J(ph_12);
        this.Oi = this.O4 ? ElytraState.WAITING_FOR_GLIDE_END : ElytraState.ABORTED_THIS_FLIGHT;
        this.K = 0;
        if (!bm_12.isElytraFlying()) {
            this.a(false);
            return false;
        }
        return true;
    }

    private boolean I(EntityOtherPlayerMP bt) {
        if (bt == null || bt.isNull()) {
            return false;
        }
        EnumHand hand = RotationUtil.q(bt);
        if (hand == null) {
            return false;
        }
        ItemStack itemStack = bt.i(hand);
        float f2 = bt.d(hand);
        if (f2 <= 0.0f) {
            return false;
        }
        int n3 = itemStack.getItem().I(itemStack, bt);
        float f6 = (float)n3 - f2;
        return f6 >= 5.0f;
    }

    private static double x(double d, double d3, double d4) {
        return Math.max(d3, Math.min(d4, d));
    }

    private boolean k(float f2) {
        if (this.isFallingForSmash(Minecraft.thePlayer()) && (this.d() || this.D$src$Z$bfhv2e(Minecraft.thePlayer()))) {
            return true;
        }
        return AttackCooldownUtil.isAttackReady(f2);
    }

    private double d(int n3, int n4, EntityLivingBase bj_12) {
        EntityPlayerSP bm_12 = Minecraft.thePlayer();
        if (bm_12.b$src$Z$fqlxe4()) {
            return n4 > 0 ? 0.0 : -1.0;
        }
        double d = -1.0;
        if (n3 > 0) {
            d = this.n(bm_12.getFallDistance(), bj_12, n3, 0);
        }
        if (n4 > 0) {
            d = Math.max(d, this.n(bm_12.getFallDistance(), bj_12, 0, n4));
        }
        return d;
    }

    private boolean m$src$Z$15xyix1() {
        return ((ModeSelection)this.Ol.getValue()).equals(TYPE_BREACH);
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onPreTick(EventPreTick co_12) {
        if (co_12.getThePlayer().isNull()) {
            this.x$src$V$16409ck();
            return;
        }
        this.x = new BlockPlacementGraph(co_12.getThePlayer());
        if (this.e(co_12.getThePlayer(), co_12.getWorld())) {
            this.x$src$V$16409ck();
            boolean bl = false;
            if (this.a) {
                AttackKeyController.releaseAttackKey();
                this.a = false;
                bl = true;
            }
            if (this.Oz && this.P) {
                if (co_12.getThePlayer().l() <= this.A) {
                    return;
                }
                if (this.S(co_12.getThePlayer(), this.OP)) {
                    co_12.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.OP);
                    AttackKeyController.releaseAttackKey();
                    this.a = AttackKeyController.requestSyntheticAttack(this);
                }
                this.Oz = false;
                this.OP = -1;
                this.A = -1;
                return;
            }
            if (this.P && this.Oj++ > 1) {
                if (this.Ok != -1) {
                    co_12.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.Ok);
                    this.Ok = -1;
                }
                this.P = false;
                this.Oj = 0;
                this.OP = -1;
                this.A = -1;
            }
            return;
        }
        this.c(co_12.getThePlayer(), co_12.getWorld());
        boolean bl = false;
        if (this.a) {
            AttackKeyController.releaseAttackKey();
            this.a = false;
            bl = true;
        }
        if (this.Oz && this.P) {
            if (co_12.getThePlayer().l() <= this.A) {
                return;
            }
            if (this.S(co_12.getThePlayer(), this.OP)) {
                co_12.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.OP);
                AttackKeyController.releaseAttackKey();
                this.a = AttackKeyController.requestSyntheticAttack(this);
            }
            this.Oz = false;
            this.OP = -1;
            this.A = -1;
            return;
        }
        if (this.P && this.Oj++ > 1) {
            if (this.Ok != -1) {
                co_12.getThePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(this.Ok);
                this.Ok = -1;
            }
            this.P = false;
            this.Oj = 0;
            this.OP = -1;
            this.A = -1;
        }
        if (!bl) {
            this.b(co_12.getThePlayer());
        }
    }

    private void c(EntityPlayerSP bm_12, WorldClient pv_12) {
        if (!this.b.getEffectiveValue().booleanValue() || pv_12.isNull() || this.x == null) {
            this.x$src$V$16409ck();
            return;
        }
        if (!this.canUseHeldItem()) {
            this.x$src$V$16409ck();
            return;
        }
        if (bm_12.b$src$Z$fqlxe4() || bm_12.isElytraFlying() || bm_12.f$src$Z$fst3rk() || bm_12.h$src$Z$ftwoya() || bm_12.S$src$Z$151gttj() || bm_12.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
            this.x$src$V$16409ck();
            return;
        }
        if (!this.X$src$Z$15meugg()) {
            this.x$src$V$16409ck();
            return;
        }
        EntityLivingBase bj_12 = this.D(bm_12);
        if (bj_12 == null) {
            this.x$src$V$16409ck();
            return;
        }
        double d = this.l$src$D$15xepsq();
        boolean bl = this.f(bm_12, bj_12, d);
        ImpactPrediction ue_22 = this.J(bm_12, pv_12, bj_12, d);
        if (!(ue_22.valid && ue_22.tick <= 6 || bl)) {
            this.x$src$V$16409ck();
            return;
        }
        if (this.O5.isBlockedFor(this)) {
            this.x$src$V$16409ck();
            return;
        }
        boolean bl2 = this.O.getEffectiveValue();
        if (!this.O5.isOwnedBy(this) && !this.O5.acquire(this, bl2)) {
            return;
        }
        if (this.t == null || RotationManager.INSTANCE.getActiveController() != this.t) {
            this.t = new AutoMaceRotationController(bl2);
            this.t.setRetainAfterCompletion(true);
            RotationManager.INSTANCE.setController(this.t);
        }
        this.t.setTargetEntity(bj_12);
        this.t.setRenderLineWidth(d);
        this.t.setPrediction(ue_22.valid, ue_22.tick, ue_22.sourceX, ue_22.sourceY, ue_22.sourceZ, ue_22.aimX, ue_22.aimY, ue_22.aimZ, bl);
    }

    private boolean canUseHeldItem() {
        if (Minecraft.currentScreen().isNotNull()) {
            return false;
        }
        return this.Od.getEffectiveValue() == false || this.OA.isValid(Minecraft.thePlayer().getHeldItemHand(), false);
    }

    private double n(float f2, EntityLivingBase bj_12, int n3, int n4) {
        double d = 6.0 + this.P(f2) + 0.5 * (double)n3 * (double)f2;
        if (bj_12 == null || bj_12.isNull()) {
            return d;
        }
        double d3 = bj_12.o(MonsterAttributesBridge.L());
        double d4 = bj_12.o(MonsterAttributesBridge.m$src$Lgg_vape_wrapper_impl_Holder_$1lgjxui());
        double d5 = AutoMace.x(d3 - d / (2.0 + d4 / 4.0), d3 * 0.2, 20.0) / 25.0;
        d5 = AutoMace.x(d5 - 0.15 * (double)n4, 0.0, 1.0);
        return d * (1.0 - d5);
    }

    private static double j(double d, double d3, double d4, EntityLivingBase bj_12, double d5, double d8, double d9) {
        AxisAlignedBB bb = bj_12.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
        double d10 = bb.getMinX() + d5;
        double d11 = bb.getMinY() + d8;
        double d12 = bb.getMinZ() + d9;
        double d13 = bb.getMaxX() + d5;
        double d14 = bb.getMaxY() + d8;
        double d15 = bb.getMaxZ() + d9;
        double d16 = MathUtil.clamp(d, d10, d13);
        double d17 = MathUtil.clamp(d3, d11, d14);
        double d18 = MathUtil.clamp(d4, d12, d15);
        double d19 = d - d16;
        double d20 = d3 - d17;
        double d21 = d4 - d18;
        return Math.sqrt(d19 * d19 + d20 * d20 + d21 * d21);
    }

    private int p() {
        InventoryPlayer ph_12 = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        int n3 = ph_12.v();
        for (int k = 0; k < 9; ++k) {
            ItemStack pk_02;
            if (k == n3 || (pk_02 = ph_12.c(k)).isNull() || pk_02.getItem().isNull() || MappedClasses.YP == null || !pk_02.getItem().isInstance(MappedClasses.YP)) continue;
            return k;
        }
        return -1;
    }

    public boolean isHoldingAxe() {
        if (Minecraft.thePlayer().isNull()) {
            return false;
        }
        ItemStack held = Minecraft.thePlayer().getHeldItemHand();
        return held.isNotNull() && held.getItem().isNotNull() && MappedClasses.YP != null && held.getItem().isInstance(MappedClasses.YP);
    }

    public boolean hasAxe() {
        return this.isHoldingAxe() || this.p() >= 0;
    }

    private boolean N$src$Z$15gwwiu() {
        return ((ModeSelection)this.q.getValue()).equals(SELECTION_AUTO);
    }

    private int L(EntityLivingBase bj_12, boolean bl) {
        InventoryPlayer ph_12 = Minecraft.thePlayer().V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        int n3 = ph_12.v();
        boolean bl2 = this.D();
        int n4 = -1;
        double d = -1.0;
        for (int k = 0; k < 9; ++k) {
            double d2;
            if (bl && k == n3) continue;
            ItemStack pk_02 = ph_12.c(k);
            double d3 = this.o(pk_02, bl2, bj_12);
            if (!(d2 >= 0.0) || this.N$src$Z$15gwwiu() && !(d3 > d)) continue;
            n4 = k;
            d = d3;
            if (this.N$src$Z$15gwwiu()) continue;
            return k;
        }
        return n4;
    }

    private int C(EntityLivingBase bj_12) {
        return this.L(bj_12, false);
    }

    private boolean r(ItemStack pk_02) {
        if (pk_02.isNull() || pk_02.getItem().isNull()) {
            return false;
        }
        if (MappedClasses.zx == null || !pk_02.getItem().isInstance(MappedClasses.zx)) {
            return false;
        }
        if (this.N$src$Z$15gwwiu()) {
            if (EnchantmentHelper.e("density", pk_02) > 0) {
                return true;
            }
            return EnchantmentHelper.e("breach", pk_02) > 0;
        }
        if (this.x$src$Z$16409g0()) {
            return EnchantmentHelper.e("density", pk_02) > 0;
        }
        if (!this.m$src$Z$15xyix1()) {
            return false;
        }
        return EnchantmentHelper.e("breach", pk_02) > 0;
    }

    private void x$src$V$16409ck() {
        if (this.t != null) {
            this.t.clearTarget();
            RotationManager.INSTANCE.releaseController(this.t);
            this.t = null;
        }
        this.O5.release(this);
    }

    private void b(EntityPlayerSP bm_12) {
        if (!this.G.getEffectiveValue().booleanValue()) {
            return;
        }
        if (this.P) {
            return;
        }
        if (this.a) {
            return;
        }
        if (bm_12.isDead()) {
            return;
        }
        if (bm_12.isElytraFlying()) {
            return;
        }
        if (!this.x(bm_12)) {
            return;
        }
        if (!this.O6.hasTimeElapsed(50L)) {
            return;
        }
        if (!this.canOperate()) {
            return;
        }
        RayTraceResult np = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (np.isNull() || !np.getTypeOfHit().equals(RayTraceResult_type.entity())) {
            return;
        }
        Entity bz_12 = np.getEntity();
        if (!this.Or.isValidTarget(bz_12)) {
            return;
        }
        if (!this.k((float)(-this.d.getRandomValue()))) {
            return;
        }
        EntityLivingBase bj_12 = this.t(np);
        boolean bl2 = this.D$src$Z$bfhv2e(bm_12);
        boolean bl3 = this.j(bj_12) >= 0;
        boolean bl = this.k.getEffectiveValue() != false && this.C(bj_12) >= 0 && this.p() >= 0;
        boolean bl4 = bl;
        if (!(bl2 || bl3 || bl)) {
            return;
        }
        SyntheticAttackRequestEvent mw_02 = new SyntheticAttackRequestEvent(null);
        this.K(new SyntheticAttackCancellationAdapter(mw_02));
        if (!mw_02.isCanceled() && (this.D$src$Z$bfhv2e(bm_12) || this.Oz)) {
            AttackKeyController.releaseAttackKey();
            this.a = AttackKeyController.requestSyntheticAttack(this);
        }
    }

    @Override
    public String getDetailedSuffix() {
        if (this.N$src$Z$15gwwiu()) {
            return ((ModeSelection)this.q.getValue()).getName();
        }
        return ((ModeSelection)this.Ol.getValue()).getName();
    }

    private void J(InventoryPlayer ph_12) {
        if (this.v >= 0 && this.e >= 0 && ph_12.v() == this.v) {
            ph_12.g(this.e);
        }
        this.d$src$V$15t0dhc();
    }

    private boolean c(ItemStack pk_02) {
        if (pk_02 == null || pk_02.isNull() || pk_02.getItem().isNull()) {
            return false;
        }
        if (this.y == null || this.y.isNull()) {
            this.y = Item.L("minecraft:elytra");
        }
        return this.y != null && this.y.isNotNull() && pk_02.getItem().equals(this.y);
    }

    private ArrayList<FallSample> L(EntityPlayerSP bm_12, WorldClient pv_12, boolean bl, int n3) {
        ArrayList<FallSample> arrayList = new ArrayList<FallSample>();
        if (this.x == null) {
            return arrayList;
        }
        BlockPathPlanner x32 = new BlockPathPlanner(bm_12, bm_12, pv_12, this.x);
        x32.applySnapshot(this.x);
        x32.initializeMovementController();
        EntityPlayer bg_12 = x32.getSimulatedPlayer();
        float f2 = bm_12.getFallDistance();
        double d = bg_12.N();
        boolean bl2 = !bl;
        int n4 = 1;
        while (n4 <= 20) {
            if (!bl2 && bg_12.isElytraFlying()) {
                if (bg_12.q() > -0.5 && f2 > 1.0f) {
                    f2 = 1.0f;
                }
                if (n4 > n3) {
                    bg_12.k(7, false);
                    bl2 = true;
                }
            }
            x32.simulateTick();
            double d3 = bg_12.N();
            double d4 = d3 - d;
            if (d4 < 0.0) {
                f2 += (float)(-d4);
            }
            d = d3;
            FallSample uf_02 = new FallSample();
            uf_02.tick = n4++;
            uf_02.x = bg_12.z();
            uf_02.y = d3 + (double)bg_12.X();
            uf_02.z = bg_12.h();
            uf_02.fallDistance = f2;
            uf_02.elytraFlying = bg_12.isElytraFlying();
            arrayList.add(uf_02);
            if (!bg_12.b$src$Z$fqlxe4()) continue;
            break;
        }
        return arrayList;
    }

    private int j(EntityLivingBase bj_12) {
        return this.L(bj_12, true);
    }

    private boolean D() {
        return this.t();
    }

    public boolean Z$src$Z$15nifn6() {
        return this.k.getEffectiveValue() != false && this.p() >= 0;
    }

    public boolean canHandleShieldAttack() {
        return this.Z$src$Z$15nifn6();
    }

    public boolean hasReadyMace() {
        return this.d();
    }

    public boolean isWeaponSwapActive() {
        return this.P;
    }

    public boolean canHandleMaceAttack() {
        return this.d();
    }

    private void g(EntityPlayerSP bm_12) {
        if (!this.r) {
            return;
        }
        if (!this.Z.getEffectiveValue().booleanValue()) {
            this.J(bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6());
            this.r = false;
            this.Y = false;
            return;
        }
        InventoryPlayer ph_12 = bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6();
        if (this.U$src$Z$acgo8d(true)) {
            this.M(bm_12, ph_12);
            return;
        }
        double d = bm_12.N() - bm_12.W();
        if (d < -0.05) {
            this.Y = true;
        }
        if (!this.Y || d <= 0.05) {
            return;
        }
        if (this.P || this.a || bm_12.isDead()) {
            return;
        }
        if (SharedModuleControlClaims.rightClickUse.isClaimed()) {
            return;
        }
        int n3 = this.T(ph_12);
        if (n3 < 0) {
            this.r = false;
            return;
        }
        this.e = ph_12.v();
        this.v = n3;
        this.p = true;
        this.l = 0;
        ph_12.g(n3);
    }

    private boolean X(ItemStack pk_02, boolean bl) {
        return this.o(pk_02, bl, this.S$src$La_BJ_$ugqo7s()) >= 0.0;
    }

    private boolean D$src$Z$bfhv2e(EntityPlayerSP bm_12) {
        return this.o(bm_12.getHeldItemHand(), this.D(), this.S$src$La_BJ_$ugqo7s()) >= 0.0;
    }

    private void d$src$V$15t0dhc() {
        this.l = 0;
        this.p = false;
        this.v = -1;
        this.e = -1;
    }

    private EntityLivingBase t(RayTraceResult np) {
        if (np == null || np.isNull() || np.getEntity().isNull()) {
            return null;
        }
        Entity bz_12 = np.getEntity();
        if (MappedClasses.zm == null || !bz_12.isInstance(MappedClasses.zm)) {
            return null;
        }
        return new EntityLivingBase(bz_12.getObject());
    }

    private int T(InventoryPlayer ph_12) {
        for (int k = 0; k < 9; ++k) {
            if (!this.c(ph_12.c(k))) continue;
            return k;
        }
        return -1;
    }

    private int b(InventoryPlayer ph_12) {
        int n3 = -1;
        double d = -1.0;
        for (int k = 0; k < 9; ++k) {
            double d3;
            ItemStack pk_02 = ph_12.c(k);
            if (pk_02.isNull() || pk_02.r() || pk_02.getItem().isNull() || this.c(pk_02) || ItemStackScoreUtil.t(pk_02) != 1 || ItemStackScoreUtil.P(pk_02) <= 0.0 || !((d3 = ItemStackScoreUtil.L(pk_02)) > d)) continue;
            d = d3;
            n3 = k;
        }
        return n3;
    }

    private ImpactPrediction I(EntityPlayerSP bm_12, WorldClient pv_12, double d) {
        ArrayList<FallSample> arrayList = this.L(bm_12, pv_12, true, 0);
        ArrayList<FallSample> arrayList2 = this.L(bm_12, pv_12, true, 1);
        if (arrayList.isEmpty() || arrayList2.isEmpty()) {
            return null;
        }
        WorldClient px_02 = Minecraft.theWorld();
        if (px_02.isNull()) {
            return null;
        }
        ImpactPrediction fb_22 = null;
        double d3 = this.OX.getValue();
        ArrayList arrayList3 = new ArrayList(px_02.getEntityList());
        for (Object e : arrayList3) {
            EntityLivingBase bj_12;
            Entity bz_12 = new Entity(e);
            if (MappedClasses.zm == null || !bz_12.isInstance(MappedClasses.zm) || !this.s(bm_12, bj_12 = new EntityLivingBase(e)) || !this.R(arrayList, bj_12, d3) && !this.R(arrayList2, bj_12, d3)) continue;
            ImpactPrediction ue_22 = this.u(arrayList, bj_12, d);
            ImpactPrediction ue_23 = this.u(arrayList2, bj_12, d);
            if (!ue_22.valid || !ue_23.valid || ue_22.tick < 3 || ue_23.tick < 3) continue;
            int n3 = Math.max(ue_22.tick, ue_23.tick);
            if (fb_22 != null && n3 >= fb_22.tick) continue;
            fb_22 = new ImpactPrediction();
            fb_22.tick = n3;
        }
        return fb_22;
    }

    private boolean x(EntityPlayerSP bm_12) {
        return !bm_12.b$src$Z$fqlxe4() && bm_12.N() - bm_12.W() < 0.0;
    }

    private boolean R(ArrayList<FallSample> arrayList, EntityLivingBase bj_12, double d) {
        double d3 = bj_12.t();
        double d4 = bj_12.T();
        AxisAlignedBB bb = bj_12.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
        for (FallSample uf_02 : arrayList) {
            double d5;
            double d8;
            double d9 = Math.min(uf_02.tick, 5);
            double d10 = bb.getMinX() + d3 * d9;
            double d11 = bb.getMaxX() + d3 * d9;
            double d12 = bb.getMinZ() + d4 * d9;
            double d13 = bb.getMaxZ() + d4 * d9;
            double d14 = MathUtil.clamp(uf_02.x, d10, d11);
            double d15 = uf_02.x - d14;
            if (!(Math.sqrt(d15 * d15 + (d8 = uf_02.z - (d5 = MathUtil.clamp(uf_02.z, d12, d13))) * d8) <= d)) continue;
            return true;
        }
        return false;
    }

    private ImpactPrediction u(ArrayList<FallSample> arrayList, EntityLivingBase bj_12, double d) {
        ImpactPrediction ue_22 = new ImpactPrediction();
        double d3 = bj_12.t();
        double d4 = bj_12.q();
        double d5 = bj_12.T();
        boolean bl = bj_12.b$src$Z$fqlxe4();
        AxisAlignedBB bb = bj_12.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu();
        for (FallSample uf_02 : arrayList) {
            double d8 = Math.min(uf_02.tick, 5);
            double d9 = d3 * d8;
            double d10 = d5 * d8;
            double d11 = bl ? 0.0 : d4 * (double)Math.min(uf_02.tick, 3);
            double d12 = AutoMace.j(uf_02.x, uf_02.y, uf_02.z, bj_12, d9, d11, d10);
            if (uf_02.elytraFlying || !(d12 <= d * 0.9) || !(uf_02.fallDistance > 1.5f)) continue;
            ue_22.valid = true;
            ue_22.tick = uf_02.tick;
            ue_22.sourceX = uf_02.x;
            ue_22.sourceY = uf_02.y;
            ue_22.sourceZ = uf_02.z;
            double d13 = bb.getMinY() + d11;
            double d14 = bb.getMaxY() + d11;
            ue_22.aimX = (bb.getMinX() + bb.getMaxX()) * 0.5 + d9;
            ue_22.aimY = d13 + (d14 - d13) * 0.75;
            ue_22.aimZ = (bb.getMinZ() + bb.getMaxZ()) * 0.5 + d10;
            break;
        }
        return ue_22;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onKeyPress(EventKeyPress wz_02) {
        if (wz_02.isKeybinding(Minecraft.gameSettings().F()) && wz_02.isDown()) {
            this.O6.reset();
            this.K(new PhysicalAttackCancellationAdapter(wz_02));
        }
    }

    private EntityLivingBase S$src$La_BJ_$ugqo7s() {
        RayTraceResult np = RotationManager.INSTANCE.getExtendedReachRayTrace();
        return this.t(np);
    }

    private void K(AttackCancellationAdapter ex_12) {
        int n3;
        EntityLivingBase target;
        int n4;
        EntityOtherPlayerMP bt;
        ItemStack ux_12;
        if (ex_12.isCancelled()) {
            return;
        }
        if (this.P) {
            return;
        }
        if (!this.canUseHeldItem()) {
            return;
        }
        EntityPlayerSP bm_12 = Minecraft.thePlayer();
        if (bm_12.isNull()) {
            return;
        }
        if (bm_12.isElytraFlying()) {
            return;
        }
        if (bm_12.isDead()) {
            return;
        }
        RayTraceResult np = RotationManager.INSTANCE.getExtendedReachRayTrace();
        if (np.isNull() || np.getEntity().isNull()) {
            return;
        }
        if (this.k.getEffectiveValue().booleanValue() && this.P$src$Z$15i0hpk() && (ux_12 = bm_12.getHeldItemHand()).isNotNull() && ux_12.getItem().isNotNull() && (bt = this.e()) != null && this.I(bt) && (n4 = this.C(bt)) >= 0) {
            if (MappedClasses.YP != null && ux_12.getItem().isInstance(MappedClasses.YP)) {
                this.Ok = bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                this.OP = n4;
                this.A = bm_12.l();
                this.Oz = true;
                this.P = true;
                this.Oj = 0;
                return;
            }
            int n6 = this.p();
            if (n6 >= 0) {
                this.Ok = bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
                bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(n6);
                this.OP = n4;
                this.A = bm_12.l();
                this.Oz = true;
                this.P = true;
                this.Oj = 0;
                return;
            }
        }
        if ((target = this.t(np)) != null && (n3 = this.j(target)) >= 0) {
            this.Ok = bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().v();
            bm_12.V$src$Lgg_vape_wrapper_impl_InventoryPlayer_$erqak6().g(n3);
            this.P = true;
            this.Oj = 0;
        }
    }

    private ImpactPrediction J(EntityPlayerSP bm_12, WorldClient pv_12, EntityLivingBase bj_12, double d) {
        return this.u(this.L(bm_12, pv_12, false, 0), bj_12, d);
    }

    private double P(float f2) {
        if (f2 <= 3.0f) {
            return 4.0 * (double)f2;
        }
        if (f2 <= 8.0f) {
            return 12.0 + 2.0 * ((double)f2 - 3.0);
        }
        return 22.0 + (double)f2 - 8.0;
    }

    private EntityLivingBase D(EntityPlayerSP bm_12) {
        WorldClient px_02 = Minecraft.theWorld();
        if (px_02.isNull()) {
            return null;
        }
        double d = this.OX.getValue();
        EntityLivingBase bj_12 = null;
        double d3 = Double.MAX_VALUE;
        ArrayList arrayList = new ArrayList(px_02.getEntityList());
        for (Object e : arrayList) {
            double d4;
            double d5;
            double d8;
            double d9;
            EntityLivingBase bj_13;
            Entity bz_12 = new Entity(e);
            if (MappedClasses.zm == null || !bz_12.isInstance(MappedClasses.zm) || !this.s(bm_12, bj_13 = new EntityLivingBase(e)) || (d9 = Math.sqrt((d8 = bj_13.z() - bm_12.z()) * d8 + (d5 = bj_13.h() - bm_12.h()) * d5)) > d || !((d4 = (double)bm_12.getDistanceToEntity(bj_13)) < d3)) continue;
            d3 = d4;
            bj_12 = bj_13;
        }
        return bj_12;
    }

    private EntityOtherPlayerMP e() {
        EntityPlayerSP bm_12 = Minecraft.thePlayer();
        WorldClient world = Minecraft.theWorld();
        if (world.isNull() || bm_12.isNull()) {
            return null;
        }
        for (Object obj : world.X()) {
            EntityOtherPlayerMP bt;
            if (obj == null || MappedClasses.Yl != null && !MappedClasses.Yl.isInstance(obj) && MappedClasses.lG != null && !MappedClasses.lG.isInstance(obj) || (bt = new EntityOtherPlayerMP(obj)).equals(bm_12) || bt.isDead() || bt.getHealth() <= 0.0f || Vape.INSTANCE.getFriendManager().isFriend(bt) || (double)bt.getDistanceToEntity(bm_12) >= 5.0 || !((double)this.getAngleDifference(bm_12, bt) < 90.0)) continue;
            return bt;
        }
        return null;
    }

    private int getAngleDifference(EntityPlayerSP from, EntityOtherPlayerMP to) {
        double d = 0.0;
        double d3 = to.z() - from.z();
        double d4 = to.h() - from.h();
        if (d4 > 0.0 && d3 > 0.0) {
            d = Math.toDegrees(-Math.atan(d3 / d4));
        } else if (d4 > 0.0 && d3 < 0.0) {
            d = Math.toDegrees(-Math.atan(d3 / d4));
        } else if (d4 < 0.0 && d3 > 0.0) {
            d = -90.0 + Math.toDegrees(Math.atan(d4 / d3));
        } else if (d4 < 0.0 && d3 < 0.0) {
            d = 90.0 + Math.toDegrees(Math.atan(d4 / d3));
        }
        int n3 = (int)(Math.abs(d - (double)from.J()) % 360.0);
        return n3 > 180 ? 360 - n3 : n3;
    }

    private boolean isFallingForSmash(EntityPlayer player) {
        if (player.isNull()) {
            return false;
        }
        double deltaY = player.N() - player.W();
        boolean falling = deltaY < 0.0;
        boolean hasFallDistance = player.getFallDistance() > 1.5f;
        boolean isGliding = player.isElytraFlying();
        return falling && hasFallDistance && !isGliding;
    }

    private static enum ElytraState {
        IDLE,
        WAITING_FOR_GLIDE_END,
        ABORTED_THIS_FLIGHT;

    }

    private static class ImpactPrediction {
        public boolean valid;
        public int tick;
        public double sourceX;
        public double sourceY;
        public double sourceZ;
        public double aimX;
        public double aimY;
        public double aimZ;

        private ImpactPrediction() {
        }
    }

    private static class FallSample {
        public int tick;
        public double x;
        public double y;
        public double z;
        public float fallDistance;
        public boolean elytraFlying;

        private FallSample() {
        }
    }
}

