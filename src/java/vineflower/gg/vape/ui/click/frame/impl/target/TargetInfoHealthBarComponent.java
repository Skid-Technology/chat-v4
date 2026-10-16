package gg.vape.ui.click.frame.impl.target;

import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.frame.impl.hud.HudModuleFrameBase;
import gg.vape.utils.RotationUtil;
import gg.vape.utils.TimerUtil;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.wrapper.impl.EntityLivingBase;
import java.awt.Color;
import org.jetbrains.annotations.Nullable;

public class TargetInfoHealthBarComponent extends GuiComponent {
   private final TimerUtil animationTimer = new TimerUtil();
   private boolean snapToTargetHealth;
   private HudModuleFrameBase frame;
   @Nullable
   private EntityLivingBase entity;
   private float targetHealth;
   private float displayedHealth;

   public void setFrame(HudModuleFrameBase frame) {
      this.frame = frame;
   }

   @Override
   public void H() {
      double d = this.getHealthFraction();
      if (d != -1.0) {
         double d2 = Math.min(this.A() * this.getHealthFraction(), this.A());
         float f = (float)this.L() / 2.0F - 0.5F;
         Color color = new Color(0, 0, 0, 100);
         Color color2 = J.B;
         Color color3 = new Color(0, 0, 0, 100);
         if (this.frame != null) {
            color = this.frame.applyDefaultEditorAlpha(color);
            color2 = this.frame.applyDefaultEditorAlpha(color2);
            color3 = this.frame.applyDefaultEditorAlpha(color3);
         }

         GuiRenderPrimitives.I(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color, false, f, 1.0F, 8.0F, color3);
         GuiRenderPrimitives.I(this.G$src$D$1b2f02a(), this.n(), d2, this.L(), color2, true, f, 1.0F, 8.0F, color3);
      }
   }

   private float getSmoothedHealth() {
      if (this.snapToTargetHealth) {
         return this.targetHealth;
      } else {
         float f2 = this.targetHealth;
         long l = this.animationTimer.getLastMS();
         float var10000 = (float)l;
         float f = 10.0F;
         if (var10000 >= 10.0F) {
            float f3 = Math.abs(this.displayedHealth - f2);
            float f4 = f3 < 0.5F && this.displayedHealth < 0.5F ? 0.05F * ((float)l / f) : 0.08F * ((float)l / f);
            if (this.displayedHealth < f2) {
               double d = Math.max(1.0, Math.pow(f3, 0.5));
               this.displayedHealth = (float)(this.displayedHealth + f4 * d);
            }

            if (this.displayedHealth > f2) {
               double d = Math.max(1.0, Math.pow(f3, 0.5));
               this.displayedHealth = (float)(this.displayedHealth - f4 * d);
            }

            this.displayedHealth = Math.max(0.0F, Math.min(this.displayedHealth, 20.0F));
            if (Math.abs(this.displayedHealth - f2) < 0.001) {
               this.displayedHealth = f2;
            }

            this.animationTimer.reset();
         }

         if (Float.isNaN(this.displayedHealth) || !Float.isFinite(this.displayedHealth)) {
            this.displayedHealth = f2;
            this.animationTimer.reset();
         }

         f2 = this.displayedHealth;
         return Math.max(f2, 0.0F);
      }
   }

   @Override
   public void u() {
      if (this.entity != null) {
         this.targetHealth = RotationUtil.x(this.entity);
         if (this.snapToTargetHealth) {
            this.snapToTargetHealth = false;
            this.animationTimer.reset();
            this.displayedHealth = this.targetHealth;
         }
      } else {
         this.targetHealth = 0.0F;
      }
   }

   public double getHealthFraction() {
      if (this.entity == null) {
         return -1.0;
      } else {
         float f = this.getSmoothedHealth();
         return f / this.entity.I$src$F$14vyvep();
      }
   }

   public void setEntity(@Nullable EntityLivingBase entityLivingBase) {
      if (entityLivingBase == null || !entityLivingBase.isNotNull() || !entityLivingBase.equals(this.entity)) {
         this.entity = entityLivingBase;
         this.snapToTargetHealth = true;
      }
   }

   public TargetInfoHealthBarComponent(int n, int n2) {
      this.o(n);
      this.Y(n2);
   }

   public EntityLivingBase getEntity() {
      return this.entity;
   }
}
