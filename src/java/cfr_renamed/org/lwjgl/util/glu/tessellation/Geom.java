/*
 * Decompiled with CFR 0.152.
 */
package org.lwjgl.util.glu.tessellation;

import org.lwjgl.util.glu.tessellation.GLUhalfEdge;
import org.lwjgl.util.glu.tessellation.GLUvertex;

class Geom {
    private Geom() {
    }

    static double EdgeEval(GLUvertex u, GLUvertex v, GLUvertex w) {
        assert (Geom.VertLeq(u, v) && Geom.VertLeq(v, w));
        double gapL = v.var_double_s - u.var_double_s;
        double gapR = w.var_double_s - v.var_double_s;
        if (gapL + gapR > 0.0) {
            if (gapL < gapR) {
                return v.var_double_t - u.var_double_t + (u.var_double_t - w.var_double_t) * (gapL / (gapL + gapR));
            }
            return v.var_double_t - w.var_double_t + (w.var_double_t - u.var_double_t) * (gapR / (gapL + gapR));
        }
        return 0.0;
    }

    static double EdgeSign(GLUvertex u, GLUvertex v, GLUvertex w) {
        assert (Geom.VertLeq(u, v) && Geom.VertLeq(v, w));
        double gapL = v.var_double_s - u.var_double_s;
        double gapR = w.var_double_s - v.var_double_s;
        if (gapL + gapR > 0.0) {
            return (v.var_double_t - w.var_double_t) * gapL + (v.var_double_t - u.var_double_t) * gapR;
        }
        return 0.0;
    }

    static double TransEval(GLUvertex u, GLUvertex v, GLUvertex w) {
        assert (Geom.TransLeq(u, v) && Geom.TransLeq(v, w));
        double gapL = v.var_double_t - u.var_double_t;
        double gapR = w.var_double_t - v.var_double_t;
        if (gapL + gapR > 0.0) {
            if (gapL < gapR) {
                return v.var_double_s - u.var_double_s + (u.var_double_s - w.var_double_s) * (gapL / (gapL + gapR));
            }
            return v.var_double_s - w.var_double_s + (w.var_double_s - u.var_double_s) * (gapR / (gapL + gapR));
        }
        return 0.0;
    }

    static double TransSign(GLUvertex u, GLUvertex v, GLUvertex w) {
        assert (Geom.TransLeq(u, v) && Geom.TransLeq(v, w));
        double gapL = v.var_double_t - u.var_double_t;
        double gapR = w.var_double_t - v.var_double_t;
        if (gapL + gapR > 0.0) {
            return (v.var_double_s - w.var_double_s) * gapL + (v.var_double_s - u.var_double_s) * gapR;
        }
        return 0.0;
    }

    static boolean VertCCW(GLUvertex u, GLUvertex v, GLUvertex w) {
        return u.var_double_s * (v.var_double_t - w.var_double_t) + v.var_double_s * (w.var_double_t - u.var_double_t) + w.var_double_s * (u.var_double_t - v.var_double_t) >= 0.0;
    }

    static double Interpolate(double a, double x, double b, double y) {
        a = a < 0.0 ? 0.0 : a;
        double d = b = b < 0.0 ? 0.0 : b;
        if (a <= b) {
            if (b == 0.0) {
                return (x + y) / 2.0;
            }
            return x + (y - x) * (a / (a + b));
        }
        return y + (x - y) * (b / (a + b));
    }

    static void EdgeIntersect(GLUvertex o1, GLUvertex d1, GLUvertex o2, GLUvertex d2, GLUvertex v) {
        double z2;
        double z1;
        GLUvertex temp;
        if (!Geom.VertLeq(o1, d1)) {
            temp = o1;
            o1 = d1;
            d1 = temp;
        }
        if (!Geom.VertLeq(o2, d2)) {
            temp = o2;
            o2 = d2;
            d2 = temp;
        }
        if (!Geom.VertLeq(o1, o2)) {
            temp = o1;
            o1 = o2;
            o2 = temp;
            temp = d1;
            d1 = d2;
            d2 = temp;
        }
        if (!Geom.VertLeq(o2, d1)) {
            v.var_double_s = (o2.var_double_s + d1.var_double_s) / 2.0;
        } else if (Geom.VertLeq(d1, d2)) {
            z1 = Geom.EdgeEval(o1, o2, d1);
            if (z1 + (z2 = Geom.EdgeEval(o2, d1, d2)) < 0.0) {
                z1 = -z1;
                z2 = -z2;
            }
            v.var_double_s = Geom.Interpolate(z1, o2.var_double_s, z2, d1.var_double_s);
        } else {
            z1 = Geom.EdgeSign(o1, o2, d1);
            if (z1 + (z2 = -Geom.EdgeSign(o1, d2, d1)) < 0.0) {
                z1 = -z1;
                z2 = -z2;
            }
            v.var_double_s = Geom.Interpolate(z1, o2.var_double_s, z2, d2.var_double_s);
        }
        if (!Geom.TransLeq(o1, d1)) {
            temp = o1;
            o1 = d1;
            d1 = temp;
        }
        if (!Geom.TransLeq(o2, d2)) {
            temp = o2;
            o2 = d2;
            d2 = temp;
        }
        if (!Geom.TransLeq(o1, o2)) {
            temp = o2;
            o2 = o1;
            o1 = temp;
            temp = d2;
            d2 = d1;
            d1 = temp;
        }
        if (!Geom.TransLeq(o2, d1)) {
            v.var_double_t = (o2.var_double_t + d1.var_double_t) / 2.0;
        } else if (Geom.TransLeq(d1, d2)) {
            z1 = Geom.TransEval(o1, o2, d1);
            if (z1 + (z2 = Geom.TransEval(o2, d1, d2)) < 0.0) {
                z1 = -z1;
                z2 = -z2;
            }
            v.var_double_t = Geom.Interpolate(z1, o2.var_double_t, z2, d1.var_double_t);
        } else {
            z1 = Geom.TransSign(o1, o2, d1);
            if (z1 + (z2 = -Geom.TransSign(o1, d2, d1)) < 0.0) {
                z1 = -z1;
                z2 = -z2;
            }
            v.var_double_t = Geom.Interpolate(z1, o2.var_double_t, z2, d2.var_double_t);
        }
    }

    static boolean VertEq(GLUvertex u, GLUvertex v) {
        return u.var_double_s == v.var_double_s && u.var_double_t == v.var_double_t;
    }

    static boolean VertLeq(GLUvertex u, GLUvertex v) {
        return u.var_double_s < v.var_double_s || u.var_double_s == v.var_double_s && u.var_double_t <= v.var_double_t;
    }

    static boolean TransLeq(GLUvertex u, GLUvertex v) {
        return u.var_double_t < v.var_double_t || u.var_double_t == v.var_double_t && u.var_double_s <= v.var_double_s;
    }

    static boolean EdgeGoesLeft(GLUhalfEdge e) {
        return Geom.VertLeq(e.Sym.Org, e.Org);
    }

    static boolean EdgeGoesRight(GLUhalfEdge e) {
        return Geom.VertLeq(e.Org, e.Sym.Org);
    }

    static double VertL1dist(GLUvertex u, GLUvertex v) {
        return Math.abs(u.var_double_s - v.var_double_s) + Math.abs(u.var_double_t - v.var_double_t);
    }
}

