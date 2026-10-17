package gg.vape.ui.click.layout;

public class LayoutConstraints {
   private int m;
   private LayoutDock r = LayoutDock.NONE;
   private boolean D;
   private LayoutAnchor H = LayoutAnchor.NONE;
   private int i;
   private double j = 0.0;
   private boolean U;
   private boolean A = true;
   private boolean S;
   private static String g;
   private int q;
   private int x;

   public void U(boolean bl) {
      this.U = bl;
   }

   public void X(int n) {
      this.m = n;
      this.H = LayoutAnchor.OFFSET;
   }

   public int c() {
      return this.i;
   }

   public void v(LayoutAnchor g5) {
      this.H = g5;
   }

   public int int_m() {
      return this.q;
   }

   public static String java_lang_String_m() {
      return g;
   }

   public double double_G() {
      return this.j;
   }

   public LayoutAnchor P() {
      return this.H;
   }

   public void M(int n) {
      this.x = n;
   }

   public int L() {
      return this.x;
   }

   public LayoutDock t() {
      return this.r;
   }

   public boolean boolean_G() {
      return this.U;
   }

   public boolean K() {
      return this.j > 0.0;
   }

   public static void f(String string) {
      g = string;
   }

   public void Q(double d) {
      this.j = d;
   }

   public void b(boolean bl) {
      this.D = bl;
   }

   public void O(boolean bl) {
      this.S = bl;
   }

   public boolean boolean_a() {
      return this.H != LayoutAnchor.NONE;
   }

   public boolean V() {
      return this.S;
   }

   public void Q(int n) {
      this.q = n;
      this.H = LayoutAnchor.OFFSET;
   }

   public boolean Y() {
      return this.D;
   }

   public void s(LayoutDock va_22) {
      this.r = va_22;
   }

   public int int_a() {
      return this.m;
   }

   public boolean h() {
      return this.A;
   }

   public void B(int n) {
      this.i = n;
   }

   public void N(boolean bl) {
      this.A = bl;
   }

   public double G() {
      return this.double_G();
   }

   public boolean G$src$Z$1xi35hg() {
      return this.boolean_G();
   }

   public int a() {
      return this.int_a();
   }

   public boolean a$src$Z$1xwdswu() {
      return this.boolean_a();
   }

   public int m() {
      return this.int_m();
   }

   static {
      if (java_lang_String_m() == null) {
         f("S8QZvb");
      }
   }
}
