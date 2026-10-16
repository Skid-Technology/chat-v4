/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.ui.click.layout;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import gg.vape.ui.click.layout.ComponentLayout;
import gg.vape.ui.click.layout.LayoutConstraints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClickGuiContentLayout
extends ComponentLayout {
    private double var_double_B = -1.0;
    private boolean var_boolean_Q = false;
    private final Map<GuiComponent, Double> var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j = new HashMap<GuiComponent, Double>();
    private boolean var_boolean_R = false;
    private double var_double_v = -1.0;
    private double var_double_X = -1.0;
    private final static double var_double_y = 1.0;

    public void G(double d) {
        this.var_double_X = d;
    }

    public Map<GuiComponent, Double> f() {
        return this.var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j;
    }

    public void C(boolean bl) {
        this.var_boolean_R = bl;
    }

    public double B() {
        return this.var_double_v;
    }

    @Override
    public void p() {
        super.p();
        this.B$src$V$1tkcbk7();
    }

    private boolean J(GuiComponent guiComponent) {
        return guiComponent.V$src$Z$1xhop3l() && !this.d(guiComponent);
    }

    public void n() {
        this.J();
    }

    private static List lambda$groupNodesByRow$0(Double d) {
        return new ArrayList();
    }

    private void J() {
        this.var_double_v = -1.0;
        this.var_double_B = -1.0;
        this.var_double_X = -1.0;
    }

    public void h(boolean bl) {
        this.var_boolean_Q = bl;
    }

    private void I(List<GuiComponent> list) {
        double d = 0.0;
        double d2 = 0.0;
        for (GuiComponent guiComponent : list) {
            d += guiComponent.A();
            LayoutConstraints layoutConstraints = this.Q().get(guiComponent);
            if (layoutConstraints != null && layoutConstraints.K()) {
                d2 += layoutConstraints.G();
                continue;
            }
            d2 += 1.0;
        }
        this.var_double_v = d;
        this.var_double_B = d2;
        GuiComponent guiComponent = this.O();
        if (guiComponent != null) {
            this.var_double_X = guiComponent.A();
        }
    }

    private void g(List<GuiComponent> list, double d, double d2) {
        LayoutConstraints object;
        if (list.isEmpty()) {
            return;
        }
        double d3 = 0.0;
        int n = 0;
        for (GuiComponent guiComponent : list) {
            object = this.Q().get(guiComponent);
            if (object == null || !object.K()) continue;
            d3 += object.G();
            ++n;
        }
        if (this.var_boolean_Q && n == 0) {
            return;
        }
        for (GuiComponent guiComponent : list) {
            object = this.Q().get(guiComponent);
            if (object != null && object.K()) continue;
            d3 += 1.0;
        }
        if (this.var_boolean_R && d2 > 0.0) {
            d3 = d2;
        }
        double d4 = this.O().G$src$D$1b2f02a();
        for (GuiComponent guiComponent : list) {
            if (this.var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j.containsKey(guiComponent)) {
                guiComponent.K(d4);
                d4 += guiComponent.A();
                continue;
            }
            LayoutConstraints layoutConstraints = this.Q().get(guiComponent);
            double d5 = layoutConstraints != null && layoutConstraints.K() ? layoutConstraints.G() : 1.0;
            double d6 = d5 / d3 * d;
            guiComponent.K(d4);
            guiComponent.setUseExplicitWidth(true);
            this.N(guiComponent, d6);
            d4 += d6;
        }
    }

    private Map<Double, List<GuiComponent>> n$src$Ljava_util_Map_$15p85cu() {
        LinkedHashMap<Double, List<GuiComponent>> linkedHashMap = new LinkedHashMap<Double, List<GuiComponent>>();
        for (GuiComponent guiComponent : this.c()) {
            if (!guiComponent.V$src$Z$1xhop3l() || this.d(guiComponent)) continue;
            double d = guiComponent.n();
            linkedHashMap.computeIfAbsent(d, ClickGuiContentLayout::lambda$groupNodesByRow$0).add(guiComponent);
        }
        return linkedHashMap;
    }

    public ClickGuiContentLayout() {
    }

    private void B$src$V$1tkcbk7() {
        GuiComponent guiComponent = this.O();
        if (guiComponent == null) {
            return;
        }
        double d = guiComponent.A();
        if (this.var_boolean_R && d > 0.0) {
            this.D(d);
        }
        if (this.var_boolean_R && !this.var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j.isEmpty()) {
            this.l(d);
        }
        this.t();
        Map<Double, List<GuiComponent>> map = this.n$src$Ljava_util_Map_$15p85cu();
        this.S(guiComponent, map);
        double d2 = guiComponent.A();
        double d3 = -1.0;
        if (this.var_boolean_R && this.var_double_v > 0.0) {
            d2 = this.var_double_v;
            d3 = this.var_double_B;
        }
        for (List<GuiComponent> list : map.values()) {
            this.g(list, d2, d3);
        }
    }

    private void l(double d) {
        for (GuiComponent guiComponent : this.c()) {
            Double d2;
            if (!this.J(guiComponent) || (d2 = this.var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j.get(guiComponent)) == null || !(d > 0.0)) continue;
            double d3 = d * d2;
            this.N(guiComponent, d3);
        }
    }

    private void D(double d) {
        for (GuiComponent guiComponent : this.c()) {
            if (!this.J(guiComponent) || this.var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j.containsKey(guiComponent)) continue;
            double d2 = guiComponent.A() / d;
            this.var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j.put(guiComponent, d2);
        }
    }

    private void t() {
        Map<Double, List<GuiComponent>> map = this.n$src$Ljava_util_Map_$15p85cu();
        for (Map.Entry<Double, List<GuiComponent>> entry : map.entrySet()) {
            List<GuiComponent> list = entry.getValue();
            this.v(list);
        }
    }

    private void v(List<GuiComponent> list) {
        if (list.isEmpty()) {
            return;
        }
        double d = 0.0;
        int n = 0;
        for (GuiComponent guiComponent : list) {
            if (!guiComponent.V$src$Z$1xhop3l()) continue;
            d += guiComponent.A();
            ++n;
        }
        if (n == 0) {
            return;
        }
        double d2 = d / (double)n;
        for (GuiComponent guiComponent : list) {
            LayoutConstraints layoutConstraints;
            if (!guiComponent.V$src$Z$1xhop3l() || (layoutConstraints = this.Q().get(guiComponent)) == null || layoutConstraints.K()) continue;
            double d3 = guiComponent.A();
            if (d2 > 0.0) {
                double d4 = d3 / d2;
                layoutConstraints.Q(d4);
                continue;
            }
            layoutConstraints.Q(1.0);
        }
    }

    public void E() {
        this.var_java_util_Map_gg_vape_ui_click_component_GuiComponent,_java_lang_Double__j.clear();
    }

    public double u() {
        return this.var_double_B;
    }

    public ClickGuiContentLayout(GuiComponent guiComponent) {
        super(guiComponent);
    }

    public void a(double d) {
        this.var_double_v = d;
    }

    public double g() {
        return this.var_double_X;
    }

    public void X(double d) {
        this.var_double_B = d;
    }

    private void S(GuiComponent guiComponent, Map<Double, List<GuiComponent>> map) {
        if (!this.var_boolean_R) {
            return;
        }
        boolean bl = false;
        if (this.var_double_X > 0.0) {
            double d;
            double d2 = guiComponent.A();
            if (Math.abs(d - this.var_double_X) > 1.0) {
                bl = true;
            }
        }
        if (bl) {
            this.J();
        }
        if (this.var_double_v < 0.0 && !map.isEmpty()) {
            List<GuiComponent> list = map.values().iterator().next();
            this.I(list);
        }
    }

    public boolean K() {
        return this.var_boolean_R;
    }

    public boolean g$src$Z$1u4opm0() {
        return this.var_boolean_Q;
    }

    private void N(GuiComponent guiComponent, double d) {
        PaddedComponent paddedComponent;
        GuiComponent guiComponent2;
        guiComponent.o(d);
        guiComponent.setExplicitWidth(d);
        if (guiComponent instanceof PaddedComponent && (guiComponent2 = (paddedComponent = (PaddedComponent)guiComponent).t(GuiComponent.class)) != null) {
            double d2 = d - paddedComponent.c$src$D$1nh5w89() - paddedComponent.o$src$D$1nnrfcl();
            guiComponent2.o(d2);
            guiComponent2.setExplicitWidth(d2);
        }
    }
}

