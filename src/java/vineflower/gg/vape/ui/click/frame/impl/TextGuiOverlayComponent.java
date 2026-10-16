package gg.vape.ui.click.frame.impl;

import gg.vape.Vape;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.module.none.TextGuiSettings;
import gg.vape.module.none.TextGuiSettingsFrame;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleConfigFrameBase;
import gg.vape.ui.font.FontFamily;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.unmap.ColorUtil;
import gg.vape.unmap.ModeSelection;
import gg.vape.utils.MutableColor;
import gg.vape.utils.NameComparator;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.ImageRenderer;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtils;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TextGuiOverlayComponent extends GuiComponent {
   public SmoothFontRenderer K;
   private HashMap<Mod, TextGuiModuleRenderState> O;
   private TextGuiSettingsFrame v;
   private List<Mod> R;
   public SmoothFontRenderer b;
   private TextGuiSettings i = Vape.INSTANCE.getModManager().getMod(TextGuiSettings.class);
   private double G = 110.0;
   private HashSet<Mod> I;
   private TimerUtil Q;
   private double o = 0.0;
   private final Comparator<Mod> BY;
   private HashMap<Mod, Double> a = new HashMap<>();

   public static boolean G(TextGuiOverlayComponent textGuiOverlayComponent) {
      return textGuiOverlayComponent.q$src$Z$emgut();
   }

   private Color R(Mod mod) {
      ModeSelection modeSelection = this.i.colorMode.getValue();
      if (modeSelection.equals(this.i.matchGuiColor)) {
         return J.z();
      } else {
         return (Color)(modeSelection.equals(this.i.customColor) ? this.i.textGuiColor.getMutableColor() : new Color(mod.getGuiColor()));
      }
   }

   @Override
   public void H() {
      this.L(false);
   }

   private void l(List<Mod> list) {
      ArrayList<Mod> arrayList = new ArrayList<>();
      ArrayList<Mod> arrayList2 = new ArrayList<>();

      for (Mod mod : list) {
         if (!this.R.contains(mod)) {
            arrayList.add(mod);
         }
      }

      for (Mod modx : this.R) {
         if (!list.contains(modx)) {
            arrayList2.add(modx);
         }
      }

      for (Mod modxx : arrayList) {
         TextGuiModuleRenderState textGuiModuleRenderState = new TextGuiModuleRenderState(modxx);
         textGuiModuleRenderState.w(true);
         textGuiModuleRenderState.f(0.0F);
         this.O.put(modxx, textGuiModuleRenderState);
      }

      for (Mod modxx : arrayList2) {
         TextGuiModuleRenderState textGuiModuleRenderState = new TextGuiModuleRenderState(modxx);
         textGuiModuleRenderState.w(false);
         textGuiModuleRenderState.f(1.0F);
         this.O.put(modxx, textGuiModuleRenderState);
      }

      if (this.Q.hasTimeElapsed(10L) || !this.i.animations.getEffectiveValue()) {
         this.Q.reset();

         for (Mod modxx : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(modxx);
            if (!TextGuiModuleRenderState.h(textGuiModuleRenderState)) {
               if (!this.i.animations.getEffectiveValue()) {
                  if (TextGuiModuleRenderState.a(textGuiModuleRenderState)) {
                     TextGuiModuleRenderState.f(textGuiModuleRenderState, 1.0F);
                     textGuiModuleRenderState.T(true);
                  } else {
                     TextGuiModuleRenderState.f(textGuiModuleRenderState, 0.0F);
                     textGuiModuleRenderState.T(true);
                  }
               } else if (TextGuiModuleRenderState.a(textGuiModuleRenderState)) {
                  TextGuiModuleRenderState.f(textGuiModuleRenderState, (float)(TextGuiModuleRenderState.G(textGuiModuleRenderState) + 0.08));
                  if (TextGuiModuleRenderState.G(textGuiModuleRenderState) >= 1.0F) {
                     TextGuiModuleRenderState.f(textGuiModuleRenderState, 1.0F);
                     textGuiModuleRenderState.T(true);
                  }
               } else {
                  TextGuiModuleRenderState.f(textGuiModuleRenderState, (float)(TextGuiModuleRenderState.G(textGuiModuleRenderState) - 0.08));
                  if (TextGuiModuleRenderState.G(textGuiModuleRenderState) <= 0.0F) {
                     TextGuiModuleRenderState.f(textGuiModuleRenderState, 0.0F);
                     textGuiModuleRenderState.T(true);
                  }
               }
            }
         }
      }
   }

   @Override
   public void I() {
      this.L(true);
   }

   public TextGuiOverlayComponent(TextGuiSettingsFrame textGuiSettingsFrame) {
      this.I = new HashSet<>();
      this.O = new HashMap<>();
      this.R = new ArrayList<>();
      this.Q = new TimerUtil();
      this.BY = new TextGuiModuleComparator(this);
      this.v = textGuiSettingsFrame;
      this.setUseExplicitWidth(true);
      this.setUseExplicitHeight(true);
   }

   @Override
   public double C() {
      int n = this.R();
      return n == 0 && !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext() ? this.o(3) : this.o(n);
   }

   private void f(List<Mod> list) {
      float f = this.i.scale.getValue().floatValue();
      this.b = this.w((int)(16.0F * f));
      int n = !this.q$src$Z$emgut() ? 14 : 16;
      this.K = this.w((int)(n * f));
      String var4 = this.i.sortMode.getValue().getName();
      switch (var4) {
         case "Alphabetical":
            list.sort(new NameComparator());
            break;
         case "Length":
            list.sort(this.BY);
      }
   }

   private SmoothFontRenderer w(int n) {
      FontFamily fontFamily = Vape.INSTANCE.getFontSelector().W().b();
      return fontFamily != null && fontFamily != FontFamily.PROXIMA
         ? Vape.INSTANCE.getFontManager().n(fontFamily, n, false)
         : Vape.INSTANCE.getFontManager().a(n);
   }

   @Override
   public double x() {
      float f = this.i.scale.getValue().floatValue();
      return this.G + 7.0F * f + 2.0;
   }

   private int R() {
      int n = 0;

      for (Mod mod : Vape.INSTANCE.getModManager().collectMods()) {
         if (mod.isEnabled()
            && mod.getGuiColor() != 0
            && mod.q$src$Z$12h8h4c()
            && (!this.i.hideModules.getEffectiveValue() || !this.i.hiddenModules.matches(mod.getName(), false))) {
            n++;
         }
      }

      for (Mod modx : this.O.keySet()) {
         TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(modx);
         if (!TextGuiModuleRenderState.a(textGuiModuleRenderState)
            && !TextGuiModuleRenderState.h(textGuiModuleRenderState)
            && TextGuiModuleRenderState.G(textGuiModuleRenderState) > 0.0F) {
            n++;
         }
      }

      return n;
   }

   @Override
   public void e(GuiMouseEvent guiMouseEvent) {
      super.e(guiMouseEvent);
      if (this.i.clickDisable.getEffectiveValue() && !ClientSettings.INSTANCE.inputEnabled) {
         for (Mod mod : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
            if (TextGuiModuleRenderState.J(textGuiModuleRenderState) != null
               && TextGuiModuleRenderState.J(textGuiModuleRenderState).J(guiMouseEvent.getX(), guiMouseEvent.getY())) {
               if (mod.isEnabled()) {
                  mod.setEnabled(false);
                  this.I.add(mod);
               } else {
                  mod.setEnabled(true);
                  this.I.remove(mod);
                  this.R.add(mod);
               }
               break;
            }
         }
      }
   }

   private boolean q$src$Z$emgut() {
      return !this.i.smoothFont.getEffectiveValue();
   }

   private void L(boolean bl) {
      MousePosition mousePosition = RenderUtils.h();
      float f2 = this.i.scale.getValue().floatValue();
      double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      f2 = (float)(f2 * d);
      SmoothFontRenderer smoothFontRenderer = this.w((int)(16.0F * f2));
      int n2 = !this.q$src$Z$emgut() ? 14 : 16;
      SmoothFontRenderer smoothFontRenderer2 = this.w((int)(n2 * f2));
      float f3 = this.v.isManagedByClickGui()
         ? (float)this.n()
         : (bl ? (float)(this.n() - this.v.j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().L()) : (float)(this.n() + 4.0));
      float f4 = f3;
      double d2 = this.v.A();
      boolean bl2 = this.G$src$D$1b2f02a() + d2 / 2.0 >= Minecraft.J() / 4 / Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      if (bl2) {
         boolean bl4 = Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled();
         double d3 = 10.0;
         CopyOnWriteArrayList<Mod> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

         for (Mod object5 : Vape.INSTANCE.getModManager().collectMods()) {
            if (object5.isEnabled()
               && object5.getGuiColor() != 0
               && object5.q$src$Z$12h8h4c()
               && (!this.i.hideModules.getEffectiveValue() || !this.i.hiddenModules.matches(object5.getName(), false))) {
               copyOnWriteArrayList.add(object5);
            }
         }

         this.l(copyOnWriteArrayList);
         this.R.clear();
         this.R.addAll(copyOnWriteArrayList);

         for (Mod object7 : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(object7);
            if (this.I.contains(object7)) {
               if (object7.isEnabled()) {
                  textGuiModuleRenderState.T(true);
                  textGuiModuleRenderState.f(1.0F);
                  textGuiModuleRenderState.w(true);
                  this.I.remove(object7);
               } else {
                  textGuiModuleRenderState.T(true);
                  textGuiModuleRenderState.f(1.0F);
                  textGuiModuleRenderState.w(false);
                  if (!copyOnWriteArrayList.contains(object7)) {
                     copyOnWriteArrayList.add(object7);
                  }
               }
            }

            if (!TextGuiModuleRenderState.h(textGuiModuleRenderState)
               && !TextGuiModuleRenderState.a(textGuiModuleRenderState)
               && TextGuiModuleRenderState.G(textGuiModuleRenderState) > 0.0F) {
               copyOnWriteArrayList.add(object7);
            }
         }

         this.f(copyOnWriteArrayList);
         ArrayList arrayList = new ArrayList();

         for (Mod mod : copyOnWriteArrayList) {
            arrayList.add(mod);
            String string = mod.getName();
            String string2 = mod.getSuffixForMode(this.i.getSuffixModeIndex());
            double d4 = this.o(smoothFontRenderer, smoothFontRenderer2, string, string2, f2, bl2);
            d3 = Math.max(d3, d4);
         }

         boolean bl5 = arrayList.isEmpty() && !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext();
         String[] stringArray = new String[]{"Example 1", "Example 2", "Example 3"};
         int n3 = bl5 ? stringArray.length : arrayList.size();
         if (bl5) {
            for (String string : stringArray) {
               d3 = Math.max(d3, smoothFontRenderer.Y(string, this.q$src$Z$emgut()));
            }
         }

         if (this.i.watermark.getEffectiveValue()) {
            d3 = Math.max(d3, 60.0);
         }

         if (this.i.addCustomText.getEffectiveValue() && this.i.customText.getValue().length() > 0) {
            String string = this.i.customText.getValue().replace("&", "§");
            SmoothFontRenderer smoothFontRenderer3 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0F * f2), true);
            double d5 = smoothFontRenderer3.Y(string, this.q$src$Z$emgut());
            d3 = Math.max(d3, d5);
         }

         this.G = d3;
         double d6 = this.G$src$D$1b2f02a() + d2 - d3;
         ClientSettings.INSTANCE.syncRainbowHue();
         if (this.i.watermark.getEffectiveValue()) {
            float f6 = (float)(d6 + d3 - 58.0F * f2);
            float f7 = f6 + 40.0F * f2;
            float f8 = 12.0F * f2;
            Object object2 = this.v.applyDefaultEditorAlpha(J.z());
            boolean bl6 = this.i.shadow.getEffectiveValue() && ((Color)object2).getAlpha() == 255;
            ImageRenderer.drawImage((Color)object2, f6, f3, "vapelogo", f8, f8, bl6);
            ImageRenderer.drawImage(this.v.applyDefaultEditorAlpha(Color.WHITE), f7, f3, "v4", f8, f8, bl6);
            f3 += 15.0F * f2;
         }

         if (this.i.addCustomText.getEffectiveValue() && this.i.customText.getValue().length() > 0) {
            String string = this.i.customText.getValue().replace("&", "§");
            SmoothFontRenderer smoothFontRenderer4 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0F * f2), true);
            double d7 = smoothFontRenderer4.Y(string, this.q$src$Z$emgut());
            float f5 = (float)(this.G$src$D$1b2f02a() + d2 - 4.0 - d7);
            if (this.i.shadow.getEffectiveValue()) {
               smoothFontRenderer4.V(
                  string,
                  f5,
                  f3,
                  this.v
                     .applyDefaultEditorAlpha(
                        (Color)(this.i.customTextColorEnabled.getEffectiveValue() && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()
                           ? this.i.customTextColor.getMutableColor()
                           : J.z())
                     ),
                  this.q$src$Z$emgut()
               );
            } else {
               smoothFontRenderer4.E(
                  string,
                  f5,
                  f3,
                  this.v
                     .applyDefaultEditorAlpha(
                        (Color)(this.i.customTextColorEnabled.getEffectiveValue() && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()
                           ? this.i.customTextColor.getMutableColor()
                           : J.z())
                     ),
                  this.q$src$Z$emgut()
               );
            }

            f3 = (float)(f3 + (smoothFontRenderer4.R(string, this.q$src$Z$emgut()) + 4.0F * f2));
         }

         if (!bl5 && arrayList.isEmpty()) {
            this.o = f3 - f4;
         } else {
            Color color = J.z();
            Color color2 = this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 160));
            boolean bl7 = this.i.renderBackground.getEffectiveValue();
            float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
            float hue = hsbColor[0];
            int n5 = -1;
            int n6 = 1;
            int n7 = 8;
            int n8 = 2;
            int n9 = 4;
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.disableLighting();
            double[] dArray = bl5 ? new double[stringArray.length] : null;

            for (int i = 0; i < n3; i++) {
               Mod mod = bl5 ? null : (Mod)arrayList.get(i);
               boolean bl9;
               String string;
               MutableColor mutableColor;
               String string3;
               if (bl5) {
                  string3 = stringArray[i];
                  string = "";
                  mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(J.z()));
                  bl9 = false;
               } else {
                  bl9 = this.I.contains(mod);
                  string3 = mod.getName();
                  string = mod.getSuffixForMode(this.i.getSuffixModeIndex());
                  if (bl9) {
                     string = "§7| Disabled";
                  }

                  mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(this.R(mod)));
                  if (bl4) {
                     if ((hue -= 0.025F) <= 0.0F) {
                        hue = 1.0F;
                     }

                     hsbColor[0] = hue;
                     mutableColor = new MutableColor(this.v.applyDefaultEditorAlpha(ColorUtil.createReadableHsbColor(hue, hsbColor[1], hsbColor[2], 4)));
                  }
               }

               double d12 = this.o(smoothFontRenderer, smoothFontRenderer2, string3, string, f2, bl2);
               float f5 = (float)(this.G$src$D$1b2f02a() + d2 - 4.0 - d12);
               double d13 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
               double d14 = f5;
               double d15 = d14 + (d12 + 3.5);
               if (i == 0) {
                  f3 += 1.0F * f2;
               }

               int n10 = (int)(f3 - 1.0F * f2);
               int n11 = (int)(d13 + 2.0);
               double d16 = d12 + 7.0F * f2;
               d15 -= d12 + 5.0;
               if (n5 != -1) {
                  n10 = n5;
               }

               boolean bl10 = false;
               if (!bl5 && this.i.clickDisable.getEffectiveValue() && this.O.containsKey(mod)) {
                  TextGuiModuleRenderState textGuiModuleRenderStatex = this.O.get(mod);
                  textGuiModuleRenderStatex.a(d15, n10, d16, n11);
                  if (TextGuiModuleRenderState.J(textGuiModuleRenderStatex).Z(mousePosition)) {
                     bl10 = true;
                  }
               }

               if (bl10) {
                  int n12 = ColorUtil.calculatePerceivedBrightness(mutableColor);
                  if (n12 > 200) {
                     mutableColor.blend(new Color(100, 100, 100, 100), 0.1F);
                  } else {
                     mutableColor.blend(new Color(255, 255, 255, 255), 0.2F);
                  }
               }

               n11 = (int)(n11 + 1.0F * f2);
               float f9 = 2.0F * f2;
               if (this.q$src$Z$emgut()) {
                  f9 = 0.0F;
               }

               int n13 = 0;
               if (i > 0) {
                  if (bl5) {
                     double d11 = dArray[i - 1];
                     if (d11 < d16) {
                        n13 |= n6;
                     }
                  } else {
                     Mod mod3 = (Mod)arrayList.get(i - 1);
                     if (this.a.containsKey(mod3) && this.a.get(mod3) < d16) {
                        n13 |= n6;
                     }
                  }
               } else {
                  n13 |= n6;
               }

               if (i < n3 - 1) {
                  if (bl5) {
                     double d11 = smoothFontRenderer.Y(stringArray[i + 1], this.q$src$Z$emgut()) + 7.0F * f2;
                     double d9 = d16 - d11;
                     if (Math.abs(d9) < f9) {
                        d16 = d11;
                        d15 += d9;
                     }

                     if (d11 < d16) {
                        n13 |= n7;
                        if (d9 < f9) {
                           d16 += f9 - d9;
                           d15 -= f9 - d9;
                        }
                     }
                  } else {
                     Mod mod4 = (Mod)arrayList.get(i + 1);
                     if (this.a.containsKey(mod4)) {
                        double d10 = this.a.get(mod4);
                        double d8 = d16 - d10;
                        if (Math.abs(d8) < f9) {
                           d16 = d10;
                           d15 += d8;
                        }

                        if (d10 < d16) {
                           n13 |= n7;
                           if (d8 < f9) {
                              d16 += f9 - d8;
                              d15 -= f9 - d8;
                           }
                        }
                     }
                  }
               } else {
                  n13 |= n7;
               }

               if (i > 0) {
                  if (bl5) {
                     double d17 = dArray[i - 1];
                     double d9x = d16 - d17;
                     if (d9x == 0.0) {
                        n13 &= ~n6;
                     }
                  } else {
                     Mod mod5 = (Mod)arrayList.get(i - 1);
                     double d8x;
                     if (this.a.containsKey(mod5) && (d8x = d16 - this.a.get(mod5)) == 0.0) {
                        n13 &= ~n6;
                     }
                  }
               }

               boolean bl11 = i == 0;
               boolean bl8 = i == n3 - 1;
               if (bl11) {
                  n13 |= n8;
               }

               if (bl8) {
                  n13 |= n9;
               }

               Color mutableColor2 = (Color)(bl9 ? this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 100)) : mutableColor);
               float f10 = 1.5F * f2;
               double d18 = d15;
               double d19 = d16 - f10;
               if ((n13 & n7) != 0) {
                  d19 -= f9;
                  d18 = d15 + f9;
               }

               double d20 = d15;
               double d21 = n10 - 1;
               double d23 = n11 + 2;
               TextGuiModuleRenderState textGuiModuleRenderStatex = null;
               if (!bl5 && this.O.containsKey(mod)) {
                  textGuiModuleRenderStatex = this.O.get(mod);
                  d15 += d16 - d16 * TextGuiModuleRenderState.G(textGuiModuleRenderStatex);
               }

               RenderUtils.m(d20, d21, d16, d23);
               if (bl7) {
                  GuiRenderPrimitives.p(d15, n10, d16, n11, color2, false, f9, 1.0F, 0.0F, null, n13);
                  boolean bl13 = true;
                  if (textGuiModuleRenderStatex != null && !TextGuiModuleRenderState.h(textGuiModuleRenderStatex)) {
                     bl13 = false;
                  }

                  if (bl13) {
                     float f11 = 1.0F * f2;
                     GuiRenderPrimitives.a(d18, n10 + n11 - f11 / 2.0F, d19, f11, this.v.applyDefaultEditorAlpha(new Color(30, 30, 30, 40)));
                  }

                  if (GuiRenderPrimitives.d() && this.q$src$Z$emgut()) {
                     Color color5 = null;
                     float f15 = 0.0F;
                     float f16 = 1.0F;
                     boolean bl15 = false;
                     double d29 = n11;
                     double d30 = f10;
                     double d31 = n10;
                     double d32 = d15 + (d16 - 1.5 * f2);
                     int n14;
                     Color color4;
                     float f12;
                     float f13;
                     float f14;
                     boolean bl14;
                     Color mutableColor3;
                     double d24;
                     double d25;
                     double d26;
                     double d27;
                     if (bl11) {
                        d27 = d32;
                        d26 = d31;
                        d25 = d30;
                        d24 = d29;
                        mutableColor3 = mutableColor2;
                        bl14 = bl15;
                        f14 = f9;
                        f13 = f16;
                        f12 = f15;
                        color4 = color5;
                        n14 = n8;
                     } else if (bl8) {
                        d27 = d32;
                        d26 = d31;
                        d25 = d30;
                        d24 = d29;
                        mutableColor3 = mutableColor2;
                        bl14 = bl15;
                        f14 = f9;
                        f13 = f16;
                        f12 = f15;
                        color4 = color5;
                        n14 = n9;
                     } else {
                        d27 = d32;
                        d26 = d31;
                        d25 = d30;
                        d24 = d29;
                        mutableColor3 = mutableColor2;
                        bl14 = bl15;
                        f14 = f9;
                        f13 = f16;
                        f12 = f15;
                        color4 = color5;
                        n14 = 0;
                     }

                     GuiRenderPrimitives.p(d27, d26, d25, d24, mutableColor3, bl14, f14, f13, f12, color4, n14);
                  } else {
                     RenderUtils.m(d15 + (d16 - 1.5 * f2), n10, f10, d23);
                     Color color8 = null;
                     float f24 = 0.0F;
                     float f25 = 1.0F;
                     boolean bl18 = false;
                     double d43 = n11;
                     double d44 = 5.0;
                     double d45 = n10;
                     double d46 = d15 + (d16 - 5.0);
                     Color color7;
                     float f22;
                     float f23;
                     boolean bl17;
                     double d39;
                     double d40;
                     int n15;
                     float f21;
                     Color mutableColor6;
                     double d37;
                     double d38;
                     if (bl11) {
                        d40 = d46;
                        d39 = d45;
                        d38 = d44;
                        d37 = d43;
                        mutableColor6 = mutableColor2;
                        bl17 = bl18;
                        f23 = f9;
                        f22 = f25;
                        f21 = f24;
                        color7 = color8;
                        n15 = n8;
                     } else if (bl8) {
                        d40 = d46;
                        d39 = d45;
                        d38 = d44;
                        d37 = d43;
                        mutableColor6 = mutableColor2;
                        bl17 = bl18;
                        f23 = f9;
                        f22 = f25;
                        f21 = f24;
                        color7 = color8;
                        n15 = n9;
                     } else {
                        d40 = d46;
                        d39 = d45;
                        d38 = d44;
                        d37 = d43;
                        mutableColor6 = mutableColor2;
                        bl17 = bl18;
                        f23 = f9;
                        f22 = f25;
                        f21 = f24;
                        color7 = color8;
                        n15 = 0;
                     }

                     GuiRenderPrimitives.p(d40, d39, d38, d37, mutableColor6, bl17, f23, f22, f21, color7, n15);
                     RenderUtils.T();
                  }

                  n5 = n10 + n11;
                  f5 += 2.0F * f2;
                  if (bl5) {
                     dArray[i] = d16;
                  } else {
                     this.a.put(mod, d16);
                  }
               }

               if (!bl5 && textGuiModuleRenderStatex != null && !textGuiModuleRenderStatex.W()) {
                  double d51 = -(d12 - d12 * TextGuiModuleRenderState.G(textGuiModuleRenderStatex));
                  d51 = -d51;
                  f5 = (float)(f5 + d51);
                  mutableColor.withAlpha((int)(255.0F * TextGuiModuleRenderState.G(textGuiModuleRenderStatex)));
                  mutableColor = new MutableColor(mutableColor.getRed(), mutableColor.getGreen(), mutableColor.getBlue(), mutableColor.getAlpha());
               }

               double d52 = n10 + n11 / 2 - (smoothFontRenderer.R("A", this.q$src$Z$emgut()) + (this.q$src$Z$emgut() ? 0.0F : 2.0F * f2)) / 2.0;
               if (bl9) {
                  mutableColor = bl10
                     ? new MutableColor(this.v.applyDefaultEditorAlpha(new Color(200, 200, 200, 255)))
                     : new MutableColor(this.v.applyDefaultEditorAlpha(new Color(175, 175, 175, 255)));
               }

               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               if (this.i.shadow.getEffectiveValue()) {
                  MutableColor mutableColor14 = new MutableColor(this.v.applyDefaultEditorAlpha(new Color(0, 0, 0, bl7 ? 80 : 170)));
                  if (mutableColor.equals(Color.BLACK)) {
                     mutableColor = new MutableColor(1, 1, 1, 255);
                  }

                  OpenGlBackendHolder.backend.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                  smoothFontRenderer.t(string3, f5, d52, mutableColor, mutableColor14, this.q$src$Z$emgut());
                  OpenGlBackendHolder.backend.setColor(1.0F, 1.0F, 1.0F, 1.0F);
               } else {
                  smoothFontRenderer.E(string3, f5, d52, mutableColor, this.q$src$Z$emgut());
               }

               if (!string.isEmpty()) {
                  double d53 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
                  double d54 = smoothFontRenderer2.R("A", this.q$src$Z$emgut());
                  double d55 = d53 - d54;
                  if (!bl7 && this.i.shadow.getEffectiveValue()) {
                     smoothFontRenderer2.V(
                        string,
                        f5 + smoothFontRenderer.Y(string3, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "),
                        d52 + d55,
                        this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)),
                        this.q$src$Z$emgut()
                     );
                  } else {
                     smoothFontRenderer2.E(
                        string,
                        f5 + smoothFontRenderer.Y(string3, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "),
                        d52 + d55,
                        this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)),
                        this.q$src$Z$emgut()
                     );
                  }
               }

               RenderUtils.T();
               f3 += n11;
            }

            this.o = f3 - f4;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            OpenGlBackendHolder.backend.setColor(1.0F, 1.0F, 1.0F, 1.0F);
         }
      } else {
         boolean bl20 = Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled();
         double d56 = 10.0;
         CopyOnWriteArrayList<Mod> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

         for (Mod modx : Vape.INSTANCE.getModManager().collectMods()) {
            if (modx.isEnabled()
               && modx.getGuiColor() != 0
               && modx.q$src$Z$12h8h4c()
               && (!this.i.hideModules.getEffectiveValue() || !this.i.hiddenModules.matches(modx.getName(), false))) {
               copyOnWriteArrayList.add(modx);
            }
         }

         this.l(copyOnWriteArrayList);
         this.R.clear();
         this.R.addAll(copyOnWriteArrayList);

         for (Mod modxx : this.O.keySet()) {
            TextGuiModuleRenderState textGuiModuleRenderStatexx = this.O.get(modxx);
            if (this.I.contains(modxx)) {
               if (modxx.isEnabled()) {
                  textGuiModuleRenderStatexx.T(true);
                  textGuiModuleRenderStatexx.f(1.0F);
                  textGuiModuleRenderStatexx.w(true);
                  this.I.remove(modxx);
               } else {
                  textGuiModuleRenderStatexx.T(true);
                  textGuiModuleRenderStatexx.f(1.0F);
                  textGuiModuleRenderStatexx.w(false);
                  if (!copyOnWriteArrayList.contains(modxx)) {
                     copyOnWriteArrayList.add(modxx);
                  }
               }
            }

            if (!TextGuiModuleRenderState.h(textGuiModuleRenderStatexx)
               && !TextGuiModuleRenderState.a(textGuiModuleRenderStatexx)
               && TextGuiModuleRenderState.G(textGuiModuleRenderStatexx) > 0.0F) {
               copyOnWriteArrayList.add(modxx);
            }
         }

         this.f(copyOnWriteArrayList);
         ArrayList arrayList = new ArrayList();

         for (Mod modxx : copyOnWriteArrayList) {
            arrayList.add(modxx);
            String stringx = modxx.getName();
            String string4 = modxx.getSuffixForMode(this.i.getSuffixModeIndex());
            double d57 = this.o(smoothFontRenderer, smoothFontRenderer2, stringx, string4, f2, bl2);
            d56 = Math.max(d56, d57);
         }

         boolean bl21 = arrayList.isEmpty() && !ClientSettings.INSTANCE.inputEnabled && HudModuleConfigFrameBase.isHudEditorContext();
         String[] stringArrayx = new String[]{"Example 1", "Example 2", "Example 3"};
         int n = bl21 ? stringArrayx.length : arrayList.size();
         if (bl21) {
            for (String stringx : stringArrayx) {
               d56 = Math.max(d56, smoothFontRenderer.Y(stringx, this.q$src$Z$emgut()));
            }
         }

         if (this.i.watermark.getEffectiveValue()) {
            d56 = Math.max(d56, 60.0);
         }

         if (this.i.addCustomText.getEffectiveValue() && this.i.customText.getValue().length() > 0) {
            String stringx = this.i.customText.getValue().replace("&", "§");
            SmoothFontRenderer smoothFontRenderer5 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0F * f2), true);
            double d58 = smoothFontRenderer5.Y(stringx, this.q$src$Z$emgut());
            d56 = Math.max(d56, d58);
         }

         this.G = d56;
         double d59 = this.G$src$D$1b2f02a();
         ClientSettings.INSTANCE.syncRainbowHue();
         if (this.i.watermark.getEffectiveValue()) {
            float f30 = (float)d59;
            float f31 = f30 + 40.0F * f2;
            float f32 = 12.0F * f2;
            Object object = this.v.applyDefaultEditorAlpha(J.z());
            boolean bl22 = this.i.shadow.getEffectiveValue() && ((Color)object).getAlpha() == 255;
            ImageRenderer.drawImage((Color)object, f30, f3, "vapelogo", f32, f32, bl22);
            ImageRenderer.drawImage(this.v.applyDefaultEditorAlpha(Color.WHITE), f31, f3, "v4", f32, f32, bl22);
            f3 += 15.0F * f2;
         }

         if (this.i.addCustomText.getEffectiveValue() && this.i.customText.getValue().length() > 0) {
            String stringx = this.i.customText.getValue().replace("&", "§");
            SmoothFontRenderer smoothFontRenderer6 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0F * f2), true);
            double d60 = smoothFontRenderer6.Y(stringx, this.q$src$Z$emgut());
            float f = (float)(this.G$src$D$1b2f02a() + 2.0);
            if (this.i.shadow.getEffectiveValue()) {
               smoothFontRenderer6.V(
                  stringx,
                  f,
                  f3,
                  this.v
                     .applyDefaultEditorAlpha(
                        (Color)(this.i.customTextColorEnabled.getEffectiveValue() && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()
                           ? this.i.customTextColor.getMutableColor()
                           : J.z())
                     ),
                  this.q$src$Z$emgut()
               );
            } else {
               smoothFontRenderer6.E(
                  stringx,
                  f,
                  f3,
                  this.v
                     .applyDefaultEditorAlpha(
                        (Color)(this.i.customTextColorEnabled.getEffectiveValue() && !Vape.INSTANCE.getClientSettings().guiColor.isRainbowEnabled()
                           ? this.i.customTextColor.getMutableColor()
                           : J.z())
                     ),
                  this.q$src$Z$emgut()
               );
            }

            f3 = (float)(f3 + (smoothFontRenderer6.R(stringx, this.q$src$Z$emgut()) + 4.0F * f2));
         }

         if (!bl21 && arrayList.isEmpty()) {
            this.o = f3 - f4;
         } else {
            Color color = J.z();
            Color color15 = this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 160));
            boolean bl23 = this.i.renderBackground.getEffectiveValue();
            float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), new float[3]);
            float hue = hsbColor[0];
            int n24 = -1;
            int n25 = 2;
            int n26 = 4;
            int n27 = 1;
            int n28 = 8;
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            GlStateManager.disableLighting();
            double[] dArray = bl21 ? new double[stringArrayx.length] : null;

            for (int i = 0; i < n; i++) {
               Mod modxx = bl21 ? null : (Mod)arrayList.get(i);
               boolean bl25;
               MutableColor mutableColorx;
               String stringx;
               String string5;
               if (bl21) {
                  string5 = stringArrayx[i];
                  stringx = "";
                  mutableColorx = new MutableColor(this.v.applyDefaultEditorAlpha(J.z()));
                  bl25 = false;
               } else {
                  bl25 = this.I.contains(modxx);
                  string5 = modxx.getName();
                  stringx = modxx.getSuffixForMode(this.i.getSuffixModeIndex());
                  if (bl25) {
                     stringx = "§7| Disabled";
                  }

                  mutableColorx = new MutableColor(this.v.applyDefaultEditorAlpha(this.R(modxx)));
                  if (bl20) {
                     if ((hue -= 0.025F) <= 0.0F) {
                        hue = 1.0F;
                     }

                     hsbColor[0] = hue;
                     mutableColorx = new MutableColor(this.v.applyDefaultEditorAlpha(ColorUtil.createReadableHsbColor(hue, hsbColor[1], hsbColor[2], 4)));
                  }
               }

               double d61 = this.o(smoothFontRenderer, smoothFontRenderer2, string5, stringx, f2, bl2);
               float f = (float)(this.G$src$D$1b2f02a() + 2.0);
               double d62 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
               double d63 = f;
               double d64 = d63 + -1.5;
               if (i == 0) {
                  f3 += 1.0F * f2;
               }

               int n29 = (int)(f3 - 1.0F * f2);
               int n30 = (int)(d62 + 2.0);
               double d65 = d61 + 7.0F * f2;
               if (n24 != -1) {
                  n29 = n24;
               }

               boolean bl26 = false;
               if (!bl21 && this.i.clickDisable.getEffectiveValue() && this.O.containsKey(modxx)) {
                  TextGuiModuleRenderState textGuiModuleRenderStatexxx = this.O.get(modxx);
                  textGuiModuleRenderStatexxx.a(d64, n29, d65, n30);
                  if (TextGuiModuleRenderState.J(textGuiModuleRenderStatexxx).Z(mousePosition)) {
                     bl26 = true;
                  }
               }

               if (bl26) {
                  int n31 = ColorUtil.calculatePerceivedBrightness(mutableColorx);
                  if (n31 > 200) {
                     mutableColorx.blend(new Color(100, 100, 100, 100), 0.1F);
                  } else {
                     mutableColorx.blend(new Color(255, 255, 255, 255), 0.2F);
                  }
               }

               n30 = (int)(n30 + 1.0F * f2);
               float f33 = 2.0F * f2;
               if (this.q$src$Z$emgut()) {
                  f33 = 0.0F;
               }

               int n32 = 0;
               if (i > 0) {
                  if (bl21) {
                     double d66 = dArray[i - 1];
                     if (d66 < d65) {
                        n32 |= n25;
                     }
                  } else {
                     Mod mod7 = (Mod)arrayList.get(i - 1);
                     if (this.a.containsKey(mod7) && this.a.get(mod7) < d65) {
                        n32 |= n25;
                     }
                  }
               } else {
                  n32 |= n25;
               }

               if (i < n - 1) {
                  if (bl21) {
                     double d68 = smoothFontRenderer.Y(stringArrayx[i + 1], this.q$src$Z$emgut()) + 7.0F * f2;
                     double d69 = d65 - d68;
                     if (Math.abs(d69) < f33) {
                        d65 = d68;
                     }

                     if (d68 < d65) {
                        n32 |= n26;
                        if (d69 < f33) {
                           d65 += f33 - d69;
                        }
                     }
                  } else {
                     Mod mod8 = (Mod)arrayList.get(i + 1);
                     if (this.a.containsKey(mod8)) {
                        double d70 = this.a.get(mod8);
                        double d71 = d65 - d70;
                        if (Math.abs(d71) < f33) {
                           d65 = d70;
                        }

                        if (d70 < d65) {
                           n32 |= n26;
                           if (d71 < f33) {
                              d65 += f33 - d71;
                           }
                        }
                     }
                  }
               } else {
                  n32 |= n26;
               }

               if (i > 0) {
                  if (bl21) {
                     double d72 = dArray[i - 1];
                     double d73 = d65 - d72;
                     if (d73 == 0.0) {
                        n32 &= ~n25;
                     }
                  } else {
                     Mod mod9 = (Mod)arrayList.get(i - 1);
                     double d75;
                     if (this.a.containsKey(mod9) && (d75 = d65 - this.a.get(mod9)) == 0.0) {
                        n32 &= ~n25;
                     }
                  }
               }

               boolean bl27 = i == 0;
               boolean bl24 = i == n - 1;
               if (bl27) {
                  n32 |= n27;
               }

               if (bl24) {
                  n32 |= n28;
               }

               Color mutableColor15 = (Color)(bl25 ? this.v.applyDefaultEditorAlpha(new Color(20, 20, 20, 100)) : mutableColorx);
               float f34 = 1.5F * f2;
               double d77 = d65 - f34;
               double d76 = d64 + f34;
               if ((n32 & n26) != 0) {
                  d77 -= f33;
               }

               double d79 = n29 - 1;
               double d80 = d65;
               double d81 = n30 + 2;
               TextGuiModuleRenderState textGuiModuleRenderStatexxx = null;
               if (!bl21 && this.O.containsKey(modxx)) {
                  textGuiModuleRenderStatexxx = this.O.get(modxx);
                  d65 *= TextGuiModuleRenderState.G(textGuiModuleRenderStatexxx);
               }

               RenderUtils.m(d64, d79, d80, d81);
               if (bl23) {
                  GuiRenderPrimitives.p(d64, n29, d65, n30, color15, false, f33, 1.0F, 0.0F, null, n32);
                  boolean bl29 = true;
                  if (textGuiModuleRenderStatexxx != null && !TextGuiModuleRenderState.h(textGuiModuleRenderStatexxx)) {
                     bl29 = false;
                  }

                  if (bl29) {
                     float f35 = 1.0F * f2;
                     GuiRenderPrimitives.a(d76, n29 + n30 - f35 / 2.0F, d77, f35, this.v.applyDefaultEditorAlpha(new Color(30, 30, 30, 40)));
                  }

                  if (GuiRenderPrimitives.d() && this.q$src$Z$emgut()) {
                     Color color18 = null;
                     float f39 = 0.0F;
                     float f40 = 1.0F;
                     boolean bl31 = false;
                     double d87 = n30;
                     double d88 = f34;
                     double d89 = n29;
                     double d90 = d64 + 0.0;
                     int n33;
                     Color color17;
                     float f36;
                     float f37;
                     float f38;
                     boolean bl30;
                     Color mutableColor16;
                     double d82;
                     double d83;
                     double d84;
                     double d85;
                     if (bl27) {
                        d85 = d90;
                        d84 = d89;
                        d83 = d88;
                        d82 = d87;
                        mutableColor16 = mutableColor15;
                        bl30 = bl31;
                        f38 = f33;
                        f37 = f40;
                        f36 = f39;
                        color17 = color18;
                        n33 = n27;
                     } else if (bl24) {
                        d85 = d90;
                        d84 = d89;
                        d83 = d88;
                        d82 = d87;
                        mutableColor16 = mutableColor15;
                        bl30 = bl31;
                        f38 = f33;
                        f37 = f40;
                        f36 = f39;
                        color17 = color18;
                        n33 = n28;
                     } else {
                        d85 = d90;
                        d84 = d89;
                        d83 = d88;
                        d82 = d87;
                        mutableColor16 = mutableColor15;
                        bl30 = bl31;
                        f38 = f33;
                        f37 = f40;
                        f36 = f39;
                        color17 = color18;
                        n33 = 0;
                     }

                     GuiRenderPrimitives.p(d85, d84, d83, d82, mutableColor16, bl30, f38, f37, f36, color17, n33);
                  } else {
                     RenderUtils.m(d64 + 0.0, n29, f34, d81);
                     Color color21 = null;
                     float f48 = 0.0F;
                     float f49 = 1.0F;
                     boolean bl34 = false;
                     double d101 = n30;
                     double d102 = 5.0;
                     double d103 = n29;
                     double d104 = d64 + 0.0;
                     Color color20;
                     float f45;
                     float f46;
                     float f47;
                     boolean bl33;
                     Color mutableColor19;
                     double d95;
                     double d96;
                     double d97;
                     double d98;
                     int n34;
                     if (bl27) {
                        d98 = d104;
                        d97 = d103;
                        d96 = d102;
                        d95 = d101;
                        mutableColor19 = mutableColor15;
                        bl33 = bl34;
                        f47 = f33;
                        f46 = f49;
                        f45 = f48;
                        color20 = color21;
                        n34 = n27;
                     } else if (bl24) {
                        d98 = d104;
                        d97 = d103;
                        d96 = d102;
                        d95 = d101;
                        mutableColor19 = mutableColor15;
                        bl33 = bl34;
                        f47 = f33;
                        f46 = f49;
                        f45 = f48;
                        color20 = color21;
                        n34 = n28;
                     } else {
                        d98 = d104;
                        d97 = d103;
                        d96 = d102;
                        d95 = d101;
                        mutableColor19 = mutableColor15;
                        bl33 = bl34;
                        f47 = f33;
                        f46 = f49;
                        f45 = f48;
                        color20 = color21;
                        n34 = 0;
                     }

                     GuiRenderPrimitives.p(d98, d97, d96, d95, mutableColor19, bl33, f47, f46, f45, color20, n34);
                     RenderUtils.T();
                  }

                  n24 = n29 + n30;
                  f += 2.0F * f2;
                  if (bl21) {
                     dArray[i] = d65;
                  } else {
                     this.a.put(modxx, d65);
                  }
               }

               if (!bl21 && textGuiModuleRenderStatexxx != null && !textGuiModuleRenderStatexxx.W()) {
                  double d109 = -(d61 - d61 * TextGuiModuleRenderState.G(textGuiModuleRenderStatexxx));
                  f = (float)(f + d109);
                  mutableColorx.withAlpha((int)(255.0F * TextGuiModuleRenderState.G(textGuiModuleRenderStatexxx)));
                  mutableColorx = new MutableColor(mutableColorx.getRed(), mutableColorx.getGreen(), mutableColorx.getBlue(), mutableColorx.getAlpha());
               }

               double d110 = n29 + n30 / 2 - (smoothFontRenderer.R("A", this.q$src$Z$emgut()) + (this.q$src$Z$emgut() ? 0.0F : 2.0F * f2)) / 2.0;
               if (bl25) {
                  mutableColorx = bl26
                     ? new MutableColor(this.v.applyDefaultEditorAlpha(new Color(200, 200, 200, 255)))
                     : new MutableColor(this.v.applyDefaultEditorAlpha(new Color(175, 175, 175, 255)));
               }

               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               if (this.i.shadow.getEffectiveValue()) {
                  MutableColor mutableColor27 = new MutableColor(this.v.applyDefaultEditorAlpha(new Color(0, 0, 0, bl23 ? 80 : 170)));
                  if (mutableColorx.equals(Color.BLACK)) {
                     mutableColorx = new MutableColor(1, 1, 1, 255);
                  }

                  OpenGlBackendHolder.backend.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                  smoothFontRenderer.t(string5, f, d110, mutableColorx, mutableColor27, this.q$src$Z$emgut());
                  OpenGlBackendHolder.backend.setColor(1.0F, 1.0F, 1.0F, 1.0F);
               } else {
                  smoothFontRenderer.E(string5, f, d110, mutableColorx, this.q$src$Z$emgut());
               }

               if (!stringx.isEmpty()) {
                  double d111 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
                  double d112 = smoothFontRenderer2.R("A", this.q$src$Z$emgut());
                  double d113 = d111 - d112;
                  if (!bl23 && this.i.shadow.getEffectiveValue()) {
                     smoothFontRenderer2.V(
                        stringx,
                        f + smoothFontRenderer.Y(string5, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "),
                        d110 + d113,
                        this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)),
                        this.q$src$Z$emgut()
                     );
                  } else {
                     smoothFontRenderer2.E(
                        stringx,
                        f + smoothFontRenderer.Y(string5, this.q$src$Z$emgut()) + smoothFontRenderer.N(" "),
                        d110 + d113,
                        this.v.applyDefaultEditorAlpha(new Color(150, 150, 150, 255)),
                        this.q$src$Z$emgut()
                     );
                  }
               }

               RenderUtils.T();
               f3 += n30;
            }

            this.o = f3 - f4;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            OpenGlBackendHolder.backend.setColor(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }
   }

   private double o(SmoothFontRenderer smoothFontRenderer, SmoothFontRenderer smoothFontRenderer2, String string, String string2, float f, boolean bl) {
      double d = smoothFontRenderer.Y(string, this.q$src$Z$emgut());
      double d2 = smoothFontRenderer2.Y(string2.isEmpty() ? "" : string2, this.q$src$Z$emgut());
      double d3 = smoothFontRenderer.Y(" ", this.q$src$Z$emgut());
      d += d2;
      if (!string2.isEmpty()) {
         d += d3;
         if (this.q$src$Z$emgut()) {
            d -= 1.0F * f;
         }

         if (bl) {
            d++;
         }
      }

      return d;
   }

   public static TextGuiSettings F(TextGuiOverlayComponent textGuiOverlayComponent) {
      return textGuiOverlayComponent.i;
   }

   public double m$src$D$cf9yf() {
      return this.o;
   }

   @Override
   public void u() {
      if (ClientSettings.INSTANCE.inputEnabled || !this.i.clickDisable.getEffectiveValue()) {
         this.I.clear();
      }

      for (Mod mod : this.O.keySet()) {
         TextGuiModuleRenderState textGuiModuleRenderState = this.O.get(mod);
         if (TextGuiModuleRenderState.J(textGuiModuleRenderState) != null && !mod.isEnabled()) {
            TextGuiModuleRenderState.H(textGuiModuleRenderState, null);
         }
      }
   }

   public double f$src$D$8kpsw() {
      return this.G;
   }

   private double o(int n) {
      float f = this.i.scale.getValue().floatValue();
      double d = Vape.INSTANCE.getClientSettings().getGuiScaleFactor();
      f = (float)(f * d);
      SmoothFontRenderer smoothFontRenderer = this.w((int)(16.0F * f));
      double d2 = smoothFontRenderer.R("A", this.q$src$Z$emgut());
      int n2 = (int)(d2 + 2.0);
      n2 = (int)(n2 + 1.0F * f);
      double d3 = 0.0;
      if (this.i.watermark.getEffectiveValue()) {
         d3 += 15.0F * f;
      }

      if (this.i.addCustomText.getEffectiveValue() && this.i.customText.getValue().length() > 0) {
         String string = this.i.customText.getValue().replace("&", "§");
         SmoothFontRenderer smoothFontRenderer2 = Vape.INSTANCE.getFontManager().n(FontFamily.PROXIMA, (int)(22.0F * f), true);
         d3 += smoothFontRenderer2.R(string, this.q$src$Z$emgut()) + 4.0F * f;
      }

      if (n > 0) {
         d3 += 1.0F * f;
         d3 += n2 * n;
      }

      return d3;
   }
}
