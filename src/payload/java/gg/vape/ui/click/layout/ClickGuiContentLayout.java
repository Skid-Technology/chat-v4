package gg.vape.ui.click.layout;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.layout.PaddedComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ClickGuiContentLayout extends ComponentLayout {
   private double B = -1.0;
   private boolean Q = false;
   private final Map<GuiComponent, Double> j = new HashMap<>();
   private boolean R = false;
   private double v = -1.0;
   private double X = -1.0;
   private static final double y = 1.0;

   public void G(double d) {
      this.X = d;
   }

   public Map<GuiComponent, Double> f() {
      return this.j;
   }

   public void C(boolean bl) {
      this.R = bl;
   }

   public double B() {
      return this.v;
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
      this.v = -1.0;
      this.B = -1.0;
      this.X = -1.0;
   }

   public void h(boolean bl) {
      this.Q = bl;
   }

   private void I(List<GuiComponent> list) {
      double d = 0.0;
      double d2 = 0.0;

      for (GuiComponent guiComponent : list) {
         d += guiComponent.A();
         LayoutConstraints layoutConstraints = this.Q().get(guiComponent);
         if (layoutConstraints != null && layoutConstraints.K()) {
            d2 += layoutConstraints.G();
         } else {
            d2++;
         }
      }

      this.v = d;
      this.B = d2;
      GuiComponent guiComponentx = this.O();
      if (guiComponentx != null) {
         this.X = guiComponentx.A();
      }
   }

   private void g(List<GuiComponent> list, double d, double d2) {
      if (!list.isEmpty()) {
         double d3 = 0.0;
         int n = 0;

         for (GuiComponent guiComponent : list) {
            Object object = this.Q().get(guiComponent);
            if (object != null && ((LayoutConstraints)object).K()) {
               d3 += ((LayoutConstraints)object).G();
               n++;
            }
         }

         if (!this.Q || n != 0) {
            for (GuiComponent guiComponentx : list) {
               Object object = this.Q().get(guiComponentx);
               if (object == null || !((LayoutConstraints)object).K()) {
                  d3++;
               }
            }

            if (this.R && d2 > 0.0) {
               d3 = d2;
            }

            double d4 = this.O().G$src$D$1b2f02a();

            for (GuiComponent guiComponentxx : list) {
               if (this.j.containsKey(guiComponentxx)) {
                  guiComponentxx.K(d4);
                  d4 += guiComponentxx.A();
               } else {
                  LayoutConstraints layoutConstraints = this.Q().get(guiComponentxx);
                  double d5 = layoutConstraints != null && layoutConstraints.K() ? layoutConstraints.G() : 1.0;
                  double d6 = d5 / d3 * d;
                  guiComponentxx.K(d4);
                  guiComponentxx.setUseExplicitWidth(true);
                  this.N(guiComponentxx, d6);
                  d4 += d6;
               }
            }
         }
      }
   }

   private Map<Double, List<GuiComponent>> n$src$Ljava_util_Map_$15p85cu() {
      LinkedHashMap<Double, List<GuiComponent>> linkedHashMap = new LinkedHashMap<>();

      for (GuiComponent guiComponent : this.c()) {
         if (guiComponent.V$src$Z$1xhop3l() && !this.d(guiComponent)) {
            double d = guiComponent.n();
            linkedHashMap.computeIfAbsent(d, ClickGuiContentLayout::lambda$groupNodesByRow$0).add(guiComponent);
         }
      }

      return linkedHashMap;
   }

   public ClickGuiContentLayout() {
   }

   private void B$src$V$1tkcbk7() {
      GuiComponent guiComponent = this.O();
      if (guiComponent != null) {
         double d = guiComponent.A();
         if (this.R && d > 0.0) {
            this.D(d);
         }

         if (this.R && !this.j.isEmpty()) {
            this.l(d);
         }

         this.t();
         Map<Double, List<GuiComponent>> map = this.n$src$Ljava_util_Map_$15p85cu();
         this.S(guiComponent, map);
         double d2 = guiComponent.A();
         double d3 = -1.0;
         if (this.R && this.v > 0.0) {
            d2 = this.v;
            d3 = this.B;
         }

         for (List<GuiComponent> list : map.values()) {
            this.g(list, d2, d3);
         }
      }
   }

   private void l(double d) {
      for (GuiComponent guiComponent : this.c()) {
         Double d2;
         if (this.J(guiComponent) && (d2 = this.j.get(guiComponent)) != null && d > 0.0) {
            double d3 = d * d2;
            this.N(guiComponent, d3);
         }
      }
   }

   private void D(double d) {
      for (GuiComponent guiComponent : this.c()) {
         if (this.J(guiComponent) && !this.j.containsKey(guiComponent)) {
            double d2 = guiComponent.A() / d;
            this.j.put(guiComponent, d2);
         }
      }
   }

   private void t() {
      Map<Double, List<GuiComponent>> map = this.n$src$Ljava_util_Map_$15p85cu();

      for (Entry<Double, List<GuiComponent>> entry : map.entrySet()) {
         List<GuiComponent> list = entry.getValue();
         this.v(list);
      }
   }

   private void v(List<GuiComponent> list) {
      if (!list.isEmpty()) {
         double d = 0.0;
         int n = 0;

         for (GuiComponent guiComponent : list) {
            if (guiComponent.V$src$Z$1xhop3l()) {
               d += guiComponent.A();
               n++;
            }
         }

         if (n != 0) {
            double d2 = d / n;

            for (GuiComponent guiComponentx : list) {
               LayoutConstraints layoutConstraints;
               if (guiComponentx.V$src$Z$1xhop3l() && (layoutConstraints = this.Q().get(guiComponentx)) != null && !layoutConstraints.K()) {
                  double d3 = guiComponentx.A();
                  if (d2 > 0.0) {
                     double d4 = d3 / d2;
                     layoutConstraints.Q(d4);
                  } else {
                     layoutConstraints.Q(1.0);
                  }
               }
            }
         }
      }
   }

   public void E() {
      this.j.clear();
   }

   public double u() {
      return this.B;
   }

   public ClickGuiContentLayout(GuiComponent guiComponent) {
      super(guiComponent);
   }

   public void a(double d) {
      this.v = d;
   }

   public double g() {
      return this.X;
   }

   public void X(double d) {
      this.B = d;
   }

   private void S(GuiComponent guiComponent, Map<Double, List<GuiComponent>> map) {
      if (this.R) {
         boolean bl = false;
         if (this.X > 0.0 && Math.abs(guiComponent.A() - this.X) > 1.0) {
            bl = true;
         }

         if (bl) {
            this.J();
         }

         if (this.v < 0.0 && !map.isEmpty()) {
            List<GuiComponent> list = map.values().iterator().next();
            this.I(list);
         }
      }
   }

   public boolean K() {
      return this.R;
   }

   public boolean g$src$Z$1u4opm0() {
      return this.Q;
   }

   private void N(GuiComponent guiComponent, double d) {
      guiComponent.o(d);
      guiComponent.setExplicitWidth(d);
      PaddedComponent paddedComponent;
      GuiComponent guiComponent2;
      if (guiComponent instanceof PaddedComponent && (guiComponent2 = (paddedComponent = (PaddedComponent)guiComponent).t(GuiComponent.class)) != null) {
         double d2 = d - paddedComponent.c$src$D$1nh5w89() - paddedComponent.o$src$D$1nnrfcl();
         guiComponent2.o(d2);
         guiComponent2.setExplicitWidth(d2);
      }
   }
}
