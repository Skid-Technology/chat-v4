package gg.vape.ui.click.layout;

import com.google.common.collect.ImmutableList;
import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.ui.click.component.GuiComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import org.jetbrains.annotations.UnmodifiableView;

public class ComponentLayout implements LayoutManager {
   private boolean G = true;
   private boolean s = true;
   private String m = null;
   private static int[] c;
   private boolean L = true;
   private GuiComponent Z;
   private boolean d = true;
   private boolean z = true;
   private final Map<GuiComponent, RectData> K;
   private GuiComponent D;
   private boolean N = true;
   private boolean n;
   private double u;
   private GuiComponent Y;
   private final LinkedHashMap<GuiComponent, LayoutConstraints> w;
   private ImmutableList<GuiComponent> k = ImmutableList.of();
   private GuiComponent M;
   private boolean A = true;
   private boolean o = true;
   private GuiComponent P;
   private double b;

   @Override
   public double y() {
      return this.u;
   }

   public static void e(int[] nArray) {
      c = nArray;
   }

   private void G() {
      if (this.P != null) {
         ArrayList<Double> arrayList = new ArrayList<>();
         double d = 0.0;
         double d2 = 0.0;

         for (GuiComponent guiComponent : this.c()) {
            if (guiComponent.V$src$Z$1xhop3l() && !this.d(guiComponent)) {
               double d3 = guiComponent.G$src$D$1b2f02a() + guiComponent.A();
               if (d3 > d2) {
                  d2 = d3;
               }

               if (!arrayList.contains(guiComponent.n())) {
                  d += guiComponent.L();
                  arrayList.add(guiComponent.n());
               }
            }
         }

         this.P.K(d2);
         this.P.Y(d);
         if (this.Z != null) {
            this.Z.o(this.Z.A() + this.P.A());
         }

         if (this.M != null) {
            this.M.o(this.M.A() + this.P.A());
         }
      }
   }

   public void U(boolean bl) {
      this.d = bl;
   }

   public ComponentLayout(GuiComponent guiComponent) {
      this.w = new LinkedHashMap<>();
      this.K = new LinkedHashMap<>();
      this.Y = guiComponent;
   }

   public void t(boolean bl) {
      this.L = bl;
   }

   @Override
   public void p() {
      this.i();
      double d = this.Y.G$src$D$1b2f02a();
      double d2 = this.Y.n();
      HashSet<Double> hashSet = new HashSet<>();
      double d3 = 0.0;
      List<GuiComponent> list = this.c();

      for (int i = 0; i < list.size(); i++) {
         GuiComponent object = list.get(i);
         if (object.V$src$Z$1xhop3l()) {
            LayoutConstraints layoutConstraints = this.w.get(object);
            if (layoutConstraints.G$src$Z$1xi35hg()) {
               d = this.Y.G$src$D$1b2f02a() + this.Y.A() - object.A();
            }

            object.K(d);
            object.S(d2);
            if (layoutConstraints.Y()) {
               object.setUseExplicitWidth(true);
               object.o(this.Y.A());
            }

            if (layoutConstraints.a$src$Z$1xwdswu()) {
               switch (layoutConstraints.P()) {
                  case BOTTOM_RIGHT:
                     object.K(this.Y.G$src$D$1b2f02a() + this.Y.A() - object.A());
                     object.S(this.Y.n() + this.Y.L() - object.L() + 2.0);
                     break;
                  case TOP_LEFT:
                     object.K(this.Y.G$src$D$1b2f02a());
                     object.S(this.Y.n());
                     break;
                  case OFFSET:
                     boolean bl = layoutConstraints.m() != 0;
                     boolean bl2 = layoutConstraints.a() != 0;
                     if (bl2) {
                        object.S(this.Y.n() + layoutConstraints.a());
                     }

                     if (bl) {
                        if (layoutConstraints.G$src$Z$1xi35hg()) {
                           object.K(this.Y.G$src$D$1b2f02a() + this.Y.A() - object.A() - layoutConstraints.m());
                        } else {
                           object.K(this.Y.G$src$D$1b2f02a() + layoutConstraints.m());
                        }

                        if (layoutConstraints.Y()) {
                           object.o(this.Y.A() - layoutConstraints.m() * 2);
                        }

                        if (layoutConstraints.V()) {
                           d2 += object.t$src$D$1x9zexg();
                        }
                     }
               }
            } else {
               if (!hashSet.contains(d2)) {
                  hashSet.add(d2);
               }

               if (!this.w.containsKey(object)) {
                  d += object.A();
                  continue;
               }

               boolean blx = layoutConstraints.V();
               if (!layoutConstraints.h() && i < list.size() - 1) {
                  GuiComponent guiComponent = list.get(i + 1);
                  double d4 = d - this.Y.G$src$D$1b2f02a();
                  if (d4 + object.A() + guiComponent.A() < this.Y.A()) {
                     blx = false;
                  }
               }

               if (object.L() > d3) {
                  d3 = object.L();
               }

               if (!blx && !layoutConstraints.G$src$Z$1xi35hg()) {
                  d += object.A();
               } else {
                  d2 += d3;
                  d = this.Y.G$src$D$1b2f02a();
                  d3 = 0.0;
               }
            }

            if (this.d(object)) {
               try {
                  switch (layoutConstraints.t()) {
                     case TOP:
                        if (this.Z != null) {
                           throw new LayoutParseException("Top/north node is already defined");
                        }

                        this.Z = object;
                        d -= object.A();
                        object.S(-2.0);
                        object.K(this.Y.G$src$D$1b2f02a());
                        break;
                     case BOTTOM:
                        if (this.M != null) {
                           throw new LayoutParseException("Bottom/south node is already defined");
                        }

                        this.M = object;
                        d -= object.A();
                        object.S(-1.0);
                        object.K(this.Y.G$src$D$1b2f02a());
                        break;
                     case LEFT:
                        if (this.D != null) {
                           throw new LayoutParseException("Left/west node is already defined");
                        }

                        this.D = object;
                        d -= object.A();
                        object.S(-3.0);
                        object.K(-2.0);
                        break;
                     case RIGHT:
                        if (this.P != null) {
                           throw new LayoutParseException("Right/east node is already defined");
                        }

                        this.P = object;
                        d -= object.A();
                        object.S(-4.0);
                        object.K(-1.0);
                  }
               } catch (LayoutParseException var19) {
                  var19.printStackTrace();
               }
            }
         }
      }

      for (Double d4 : hashSet) {
         if (this.d) {
            this.q(d4);
         }

         if (this.A) {
            this.h(d4);
         }
      }

      if (this.L) {
         this.x();
      }

      if (this.o) {
         this.M();
      }

      if (this.Z != null) {
         for (GuiComponent guiComponent : this.c()) {
            guiComponent.S(guiComponent.n() + this.Z.L());
         }

         this.Z.K(this.Y.G$src$D$1b2f02a());
         this.Z.S(this.Y.n());
      }

      if (this.D != null) {
         this.D.K(this.Y.G$src$D$1b2f02a());
         this.D.S(this.Z != null ? this.Y.n() + this.Z.L() : this.Y.n());
      }

      if (this.N) {
         this.D();
      }

      this.o();
      this.G();

      for (GuiComponent guiComponent : this.c()) {
         this.K.put(guiComponent, new RectData(guiComponent.G$src$D$1b2f02a(), guiComponent.n(), guiComponent.A(), guiComponent.L()));
         if (guiComponent.V$src$Z$1xhop3l()) {
            double d5 = this.Y.G$src$D$1b2f02a();
            double d6 = this.Y.n();
            double d7 = guiComponent.G$src$D$1b2f02a() + guiComponent.A() - d5;
            double d8 = guiComponent.n() + guiComponent.t$src$D$1x9zexg() - d6;
            if (d7 > this.b) {
               this.b = d7;
            }

            if (d8 > this.u) {
               this.u = d8;
            }

            guiComponent.i$src$V$c9opdk();
         }
      }

      if (this.n) {
         this.T();
      }
   }

   public void v(GuiComponent guiComponent, GuiComponent guiComponent2) {
      ArrayList<Entry<GuiComponent, LayoutConstraints>> arrayList = new ArrayList<>();

      for (Entry<GuiComponent, LayoutConstraints> entry : this.w.entrySet()) {
         if (entry.getKey().equals(guiComponent)) {
            arrayList.add(new SimpleEntry<>(guiComponent2, this.w.get(guiComponent)));
         } else {
            arrayList.add(entry);
         }
      }

      this.w.clear();

      for (Entry<GuiComponent, LayoutConstraints> entryx : arrayList) {
         this.w.put(entryx.getKey(), entryx.getValue());
      }

      this.s = true;
   }

   @Override
   public void o(GuiComponent guiComponent, Object... objectArray) {
      if (objectArray != null && objectArray.length != 0) {
         this.z(guiComponent, objectArray[0].toString());
      } else if (this.m != null) {
         this.z(guiComponent, this.m);
      } else {
         this.z(guiComponent, "");
      }
   }

   public void v(boolean bl) {
      this.G = bl;
   }

   @Override
   public List<GuiComponent> c() {
      if (this.s) {
         this.k = ImmutableList.copyOf(this.w.keySet());
         this.s = false;
      }

      return this.k;
   }

   public ComponentLayout() {
      this.w = new LinkedHashMap<>();
      this.K = new LinkedHashMap<>();
   }

   private void x() {
      HashMap<Double, Double> hashMap = new HashMap<>();
      double d = 0.0;

      for (GuiComponent object : this.c()) {
         if (object.V$src$Z$1xhop3l() && !this.d(object) && object.A() > d) {
            d = object.A();
         }
      }

      for (Entry entry : this.w.entrySet()) {
         if (((GuiComponent)entry.getKey()).V$src$Z$1xhop3l()) {
            double d2 = d;
            if (((LayoutConstraints)entry.getValue()).c() > 0) {
               d2 = d * ((LayoutConstraints)entry.getValue()).c();
            }

            ((GuiComponent)entry.getKey()).o(d2);
            if (!hashMap.containsKey(((GuiComponent)entry.getKey()).n())) {
               ((GuiComponent)entry.getKey()).K(this.Y.G$src$D$1b2f02a());
            } else {
               ((GuiComponent)entry.getKey()).K(hashMap.get(((GuiComponent)entry.getKey()).n()));
            }

            hashMap.put(((GuiComponent)entry.getKey()).n(), ((GuiComponent)entry.getKey()).G$src$D$1b2f02a() + ((GuiComponent)entry.getKey()).A());
         }
      }
   }

   public void Q(boolean bl) {
      this.n = bl;
   }

   public Map<GuiComponent, RectData> m() {
      return this.K;
   }

   public LayoutConstraints b(String string) {
      LayoutConstraints layoutConstraints = new LayoutConstraints();
      string = string.toLowerCase();

      for (String string2 : string.split(",")) {
         try {
            this.J(layoutConstraints, string2.trim());
         } catch (LayoutParseException var8) {
            Vape.logThrowable(var8);
         }
      }

      return layoutConstraints;
   }

   public void j(GuiComponent guiComponent, LayoutConstraints layoutConstraints) {
      this.w.put(guiComponent, layoutConstraints);
      this.s = true;
   }

   public Map<GuiComponent, LayoutConstraints> k(double d) {
      LinkedHashMap<GuiComponent, LayoutConstraints> linkedHashMap = new LinkedHashMap<>();

      for (GuiComponent guiComponent : this.c()) {
         if (guiComponent.n() == d) {
            linkedHashMap.put(guiComponent, this.w.get(guiComponent));
         }
      }

      return linkedHashMap;
   }

   public void B(double d) {
      this.u = d;
   }

   private void q(double d) {
      double d2 = 0.0;
      Map<GuiComponent, LayoutConstraints> map = this.k(d);

      for (GuiComponent object : map.keySet()) {
         if (object.V$src$Z$1xhop3l() && object.A() > d2) {
            d2 = object.A();
         }
      }

      for (Entry entry : map.entrySet()) {
         if (((GuiComponent)entry.getKey()).V$src$Z$1xhop3l()) {
            double d3 = d2;
            if (((LayoutConstraints)entry.getValue()).c() > 0) {
               d3 = d2 * ((LayoutConstraints)entry.getValue()).c();
            }

            if (((GuiComponent)entry.getKey()).A() < d3) {
               ((GuiComponent)entry.getKey()).o(d3);
            }
         }
      }

      double d4 = this.Y.G$src$D$1b2f02a();

      for (GuiComponent guiComponent : map.keySet()) {
         guiComponent.K(d4);
         d4 += guiComponent.A();
      }
   }

   public boolean d(GuiComponent guiComponent) {
      return !this.P(guiComponent).equals(LayoutDock.NONE);
   }

   public void L() {
      this.i();
      this.u = 0.0;
      this.b = 0.0;

      for (GuiComponent guiComponent : this.c()) {
         guiComponent.K(0.0);
         guiComponent.S(0.0);
         if (this.G) {
            guiComponent.Y(guiComponent.getExplicitHeight());
         }

         if (this.z) {
            guiComponent.o(guiComponent.getExplicitWidth());
         }
      }
   }

   public boolean V() {
      return this.G;
   }

   public @UnmodifiableView Map<GuiComponent, LayoutConstraints> Q() {
      return this.w;
   }

   private void D() {
      HashMap<Double, Double> hashMap = new HashMap<>();

      for (GuiComponent guiComponent : this.c()) {
         if (guiComponent.V$src$Z$1xhop3l() && !this.P(guiComponent).equals(LayoutDock.LEFT) && !this.P(guiComponent).equals(LayoutDock.RIGHT)) {
            if (!hashMap.containsKey(guiComponent.n())) {
               hashMap.put(guiComponent.n(), guiComponent.A());
            } else {
               hashMap.put(guiComponent.n(), hashMap.get(guiComponent.n()) + guiComponent.A());
            }
         }
      }

      double d = 0.0;

      for (Object object : hashMap.values()) {
         if ((Double)object > d) {
            d = (Double)object;
         }
      }

      HashMap hashMap2 = new HashMap();

      for (Entry objectx : hashMap.entrySet()) {
         hashMap2.put(objectx.getKey(), Math.round(d / (Double)objectx.getValue() * 100.0) / 100.0);
      }

      for (GuiComponent guiComponentx : this.c()) {
         if (guiComponentx.V$src$Z$1xhop3l() && !this.P(guiComponentx).equals(LayoutDock.LEFT) && !this.P(guiComponentx).equals(LayoutDock.RIGHT)) {
            guiComponentx.o(Math.ceil(guiComponentx.A() * (Double)hashMap2.get(guiComponentx.n())));
            guiComponentx.K(
               Math.floor((guiComponentx.G$src$D$1b2f02a() - this.Y.G$src$D$1b2f02a()) * (Double)hashMap2.get(guiComponentx.n()) + this.Y.G$src$D$1b2f02a())
            );
         }
      }
   }

   public static int[] b() {
      return c;
   }

   private void h(double d) {
      double d2 = 0.0;
      GuiComponent guiComponent = null;
      Map<GuiComponent, LayoutConstraints> map = this.k(d);

      for (GuiComponent guiComponent2 : map.keySet()) {
         if (guiComponent2.V$src$Z$1xhop3l() && guiComponent2.L() > d2) {
            d2 = guiComponent2.L();
         }
      }

      for (GuiComponent guiComponent2x : map.keySet()) {
         if (guiComponent2x.V$src$Z$1xhop3l()) {
            if (guiComponent2x.L() < d2) {
               guiComponent2x.Y(d2);
            }

            if (guiComponent == null || guiComponent2x.n() > guiComponent.n()) {
               guiComponent = guiComponent2x;
            }
         }
      }

      if (this.M != null) {
         if (guiComponent != null) {
            this.M.S(guiComponent.n() + guiComponent.L());
         } else {
            this.M.S(this.Y.n());
         }

         this.M.K(this.Y.G$src$D$1b2f02a());
      }
   }

   public void r(double d) {
      this.b = d;
   }

   public void T() {
      double d = this.Y.n() + this.Y.L() / 2.0;

      for (GuiComponent guiComponent : this.c()) {
         if (guiComponent.V$src$Z$1xhop3l()) {
            double d2 = d - guiComponent.L() / 2.0;
            guiComponent.S(d2);
         }
      }
   }

   public GuiComponent O() {
      return this.Y;
   }

   public void z(GuiComponent guiComponent, String string) {
      this.j(guiComponent, this.b(string));
   }

   public LayoutDock P(GuiComponent guiComponent) {
      return !this.w.containsKey(guiComponent) ? LayoutDock.NONE : this.w.get(guiComponent).t();
   }

   public boolean C$src$Z$1jglhej() {
      return this.n;
   }

   public void u(boolean bl) {
      this.N = bl;
   }

   private void o() {
      if (this.D != null) {
         ArrayList<Double> arrayList = new ArrayList<>();
         double d = 0.0;

         for (GuiComponent guiComponent : this.c()) {
            if (guiComponent.V$src$Z$1xhop3l() && !this.d(guiComponent)) {
               if (!arrayList.contains(guiComponent.n())) {
                  d += guiComponent.L();
                  arrayList.add(guiComponent.n());
               }

               guiComponent.K(guiComponent.G$src$D$1b2f02a() + this.D.A());
            }
         }

         this.D.Y(d);
         if (this.Z != null) {
            this.Z.o(this.Z.A() + this.D.A());
         }

         if (this.M != null) {
            this.M.o(this.M.A() + this.D.A());
         }
      }
   }

   public void M(String string) {
      this.m = string;
   }

   public void I(boolean bl) {
      this.A = bl;
   }

   public Map<GuiComponent, LayoutConstraints> i(double d) {
      LinkedHashMap<GuiComponent, LayoutConstraints> linkedHashMap = new LinkedHashMap<>();

      for (GuiComponent guiComponent : this.c()) {
         if (guiComponent.G$src$D$1b2f02a() == d) {
            linkedHashMap.put(guiComponent, this.w.get(guiComponent));
         }
      }

      return linkedHashMap;
   }

   @Override
   public void g(GuiComponent guiComponent) {
      this.w.remove(guiComponent);
      this.s = true;
   }

   private static Exception a(Exception exception) {
      return exception;
   }

   @Override
   public double C() {
      return this.b;
   }

   private void M() {
      double d = this.Y.n();
      double d2 = 0.0;
      GuiComponent guiComponent = null;

      for (GuiComponent object : this.c()) {
         if (object.V$src$Z$1xhop3l() && object.L() > d2) {
            d2 = object.L();
         }
      }

      for (Entry entry : this.w.entrySet()) {
         if (((GuiComponent)entry.getKey()).V$src$Z$1xhop3l()) {
            if (this.o) {
               ((GuiComponent)entry.getKey()).Y(d2);
            }

            ((GuiComponent)entry.getKey()).S(d);
            if (guiComponent == null || ((GuiComponent)entry.getKey()).n() > guiComponent.n()) {
               guiComponent = (GuiComponent)entry.getKey();
            }

            if (((LayoutConstraints)entry.getValue()).V()) {
               d += ((GuiComponent)entry.getKey()).L();
            }
         }
      }

      if (this.M != null) {
         if (guiComponent != null) {
            this.M.S(guiComponent.n() + guiComponent.L());
         } else {
            this.M.S(this.Y.n());
         }

         this.M.K(this.Y.G$src$D$1b2f02a());
      }
   }

   @Override
   public void F(GuiComponent guiComponent) {
      this.Y = guiComponent;
   }

   public void J(LayoutConstraints layoutConstraints, String string) throws LayoutParseException {
      if (!string.isEmpty()) {
         switch (string) {
            case "top":
            case "north":
               layoutConstraints.s(LayoutDock.TOP);
               break;
            case "bottom":
            case "south":
               layoutConstraints.s(LayoutDock.BOTTOM);
               break;
            case "left":
            case "west":
               layoutConstraints.s(LayoutDock.LEFT);
               break;
            case "right":
            case "east":
               layoutConstraints.s(LayoutDock.RIGHT);
               break;
            case "wrap":
               layoutConstraints.O(true);
               break;
            case "widthwrap":
               layoutConstraints.N(false);
               break;
            case "bottomright":
               layoutConstraints.v(LayoutAnchor.BOTTOM_RIGHT);
               break;
            case "topleft":
               layoutConstraints.v(LayoutAnchor.TOP_LEFT);
               break;
            case "spanwidth":
               layoutConstraints.b(true);
               break;
            case "alignright":
               layoutConstraints.U(true);
               break;
            default:
               if (!string.contains(" ")) {
                  throw new LayoutParseException("Constraint descriptor not recognized: " + string);
               }

               if (string.startsWith("span ") || string.startsWith("spanx")) {
                  layoutConstraints.B(Integer.parseInt(string.split(" ")[1]));
               } else if (string.startsWith("spany")) {
                  layoutConstraints.M(Integer.parseInt(string.split(" ")[1]));
               } else if (string.startsWith("offsetx")) {
                  layoutConstraints.Q(Integer.parseInt(string.split(" ")[1]));
               } else if (string.startsWith("offsety")) {
                  layoutConstraints.X(Integer.parseInt(string.split(" ")[1]));
               } else {
                  if (!string.startsWith("weight")) {
                     throw new LayoutParseException("Constraint descriptor not recognized: " + string);
                  }

                  layoutConstraints.Q(Double.parseDouble(string.split(" ")[1]));
               }
         }
      }
   }

   public void o(boolean bl) {
      this.z = bl;
   }

   public boolean R() {
      return this.z;
   }

   public void i() {
      this.Z = null;
      this.M = null;
      this.D = null;
      this.P = null;
   }

   public void M(boolean bl) {
      this.o = bl;
   }

   static {
      e(null);
   }
}
