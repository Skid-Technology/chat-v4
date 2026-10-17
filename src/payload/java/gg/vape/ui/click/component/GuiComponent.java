package gg.vape.ui.click.component;

import func.skidline.RectData;
import gg.vape.Vape;
import gg.vape.input.MouseInput;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.GuiMouseEvent;
import gg.vape.ui.click.GuiMouseListener;
import gg.vape.ui.click.MouseButton;
import gg.vape.ui.click.MouseClickButton;
import gg.vape.ui.click.MousePosition;
import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.FrameComponent;
import gg.vape.ui.click.frame.FrameHeaderComponent;
import gg.vape.ui.click.frame.FrameOutsideChildClickFilterMouseListener;
import gg.vape.ui.font.FontOption;
import gg.vape.ui.font.SmoothFontRenderer;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.RenderUtils;
import gg.vape.value.Value;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jetbrains.annotations.Nullable;

public abstract class GuiComponent implements GuiComponentContract {
   private double x;
   private boolean useExplicitHeight;
   private double width;
   private boolean childRenderingSuppressed;
   private double horizontalInset = 5.0;
   private Value boundValue;
   private List<GuiKeyTypedListener> keyTypedListeners;
   private long tooltipHoverMillis;
   private List<GuiMouseListener> globalMouseListeners;
   public final float V = 8.0F;
   private boolean acceptsMouseInput = true;
   private boolean propagateMouseEvents = false;
   private final ArrayList<GuiComponent> children;
   private GuiMouseEvent lastMouseEvent;
   private boolean useExplicitWidth;
   @Nullable
   private FontOption fontOption;
   private long previousTooltipHoverMillis;
   private double y;
   private boolean showDisabledOverlay = true;
   public final float M = 5.0F;
   private double explicitHeight = -1.0;
   private boolean visible = true;
   private static GuiComponent[] legacyState;
   protected boolean hovered = false;
   private FrameHeaderComponent parentHeader;
   public final double N = 0.15;
   private long tooltipHoverStartMillis;
   private double explicitWidth = -1.0;
   private double height;
   private Color disabledOverlayColor;
   private List<GuiMouseListener> mouseListeners = new CopyOnWriteArrayList<>();
   private boolean removable = true;
   private String text = "unnamed";
   private boolean ignoreFrameClipping;
   private final List<GuiActivationListener> activationListeners;
   private boolean active;
   private ToolTips toolTips;
   private FrameComponent parentFrameComponent;

   public GuiComponent setToolTips(ToolTips toolTips) {
      this.toolTips = toolTips;
      return this;
   }

   @Override
   public void o(double width) {
      this.width = width;
      if (this.explicitWidth == -1.0) {
         this.explicitWidth = width;
      }
   }

   public boolean isChildRenderingSuppressed() {
      return this.childRenderingSuppressed;
   }

   public void setParentHeader(FrameHeaderComponent parentHeader) {
      this.parentHeader = parentHeader;
   }

   public void B$src$V$1wihpow() {
   }

   @Override
   public double L() {
      return this.useExplicitHeight ? this.height : Math.max(this.height, this.C());
   }

   @Override
   public boolean V$src$Z$1xhop3l() {
      if (this.getBoundValue() != null && this.getBoundValue().isHidden()) {
         return false;
      } else {
         boolean bl = true;
         if (this.boundValue != null) {
            bl = this.boundValue.areConditionsMet();
         }

         return this.visible && bl;
      }
   }

   public void setFontOption(@Nullable FontOption fontOption) {
      this.fontOption = fontOption;
   }

   public void setIgnoreFrameClipping(boolean ignoreFrameClipping) {
      this.ignoreFrameClipping = ignoreFrameClipping;
   }

   public List<GuiActivationListener> getActivationListeners() {
      return this.activationListeners;
   }

   public void setShowDisabledOverlay(boolean showDisabledOverlay) {
      this.showDisabledOverlay = showDisabledOverlay;
   }

   public static void setLegacyComponentState(GuiComponent[] state) {
      legacyState = state;
   }

   public void setPropagateMouseEvents(boolean propagateMouseEvents) {
      this.propagateMouseEvents = propagateMouseEvents;
   }

   public Color getDisabledOverlayColor() {
      if (this.disabledOverlayColor == null) {
         this.disabledOverlayColor = J.i;
      }

      return this.disabledOverlayColor;
   }

   public SmoothFontRenderer getAlternateFontRenderer(double scale) {
      return this.fontOption != null ? this.fontOption.k((float)scale, true) : Vape.INSTANCE.getFontManager().W(scale, false);
   }

   public String getText() {
      return this.text;
   }

   public void setParentFrameComponent(FrameComponent parentFrameComponent) {
      this.parentFrameComponent = parentFrameComponent;
   }

   public void setUseExplicitWidth(boolean useExplicitWidth) {
      this.useExplicitWidth = useExplicitWidth;
   }

   public boolean isShowDisabledOverlay() {
      return this.showDisabledOverlay;
   }

   public FrameComponent getParentFrameComponent() {
      return this.parentFrameComponent;
   }

   public void renderDebugBounds() {
      GuiRenderPrimitives.y((float)this.G$src$D$1b2f02a(), (float)this.n(), 1.0F, (float)this.L(), Color.MAGENTA);
      GuiRenderPrimitives.y((float)this.G$src$D$1b2f02a(), (float)this.n(), (float)this.A(), 1.0F, Color.MAGENTA);
      GuiRenderPrimitives.y((float)this.G$src$D$1b2f02a() + (float)this.A(), (float)this.n(), 1.0F, (float)this.L(), Color.MAGENTA);
      GuiRenderPrimitives.y((float)this.G$src$D$1b2f02a(), (float)this.n() + (float)this.L(), (float)this.A(), 1.0F, Color.MAGENTA);
   }

   public void addKeyTypedListener(GuiKeyTypedListener keyTypedListener) {
      this.keyTypedListeners.add(keyTypedListener);
   }

   public double getExplicitHeight() {
      return this.explicitHeight;
   }

   public void removeKeyTypedListener(GuiKeyTypedListener keyTypedListener) {
      this.keyTypedListeners.remove(keyTypedListener);
   }

   @Override
   public void Y(double height) {
      this.height = height;
      if (this.explicitHeight == -1.0) {
         this.explicitHeight = height;
      }
   }

   public void J() {
      if (ClientSettings.canReceiveInput(this)) {
         this.F();
         if (!this.active) {
            this.active = true;

            for (GuiActivationListener activationListener : this.activationListeners) {
               activationListener.onActivationChanged(true);
            }
         }
      }

      this.setHovered(true);
      boolean bl = ClientSettings.INSTANCE.showTooltips.getEffectiveValue();

      for (GuiComponent guiComponent : this.f()) {
         if (guiComponent.V$src$Z$1xhop3l()
            && !guiComponent.isChildRenderingSuppressed()
            && guiComponent.t()
            && ClientSettings.canReceiveInput(guiComponent)
            && (
               !(this instanceof FrameComponent)
                  || ((FrameComponent)this).j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() == null
                  || !((FrameComponent)this).j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().t()
                  || guiComponent instanceof FrameHeaderComponent
            )) {
            guiComponent.J();
            if (guiComponent.getToolTips() != null && bl) {
               bl = false;
            }
         }
      }

      if (bl && this.toolTips != null) {
         this.updateToolTipHover();
      }
   }

   public double getComponentHeight() {
      return this.L();
   }

   public double getExplicitWidth() {
      return this.explicitWidth;
   }

   public void removeChild(GuiComponent child) {
      this.f().remove(child);
   }

   public double getHorizontalInset() {
      return this.horizontalInset;
   }

   public void bindValue(Value value) {
      this.boundValue = value;
      value.setBoundComponent(this);
      if (value.getDescription() != null) {
         this.w(value.getDescription());
      }
   }

   public List<GuiMouseListener> getMouseListeners() {
      return this.mouseListeners;
   }

   @Nullable
   public FontOption getFontOption() {
      return this.fontOption;
   }

   public void removeMarkedChildren() {
      for (GuiComponent guiComponent : new CopyOnWriteArrayList<>(this.f())) {
         if (guiComponent.isRemovable()) {
            this.removeChild(guiComponent);
         }
      }
   }

   public void updateToolTipHover() {
      if (this.tooltipHoverStartMillis == 0L) {
         this.tooltipHoverStartMillis = System.currentTimeMillis();
      }

      this.tooltipHoverMillis = this.tooltipHoverMillis + (System.currentTimeMillis() - this.tooltipHoverStartMillis);
      if (this.tooltipHoverMillis >= 2000L) {
         MousePosition mousePosition = RenderUtils.h();
         this.toolTips.K(mousePosition.O);
         this.toolTips.S(mousePosition.H);
         this.toolTips.setVisible(true);
         ClientSettings.activeTooltips = this.toolTips;
      }
   }

   public SmoothFontRenderer getDefaultFontRenderer() {
      return this.fontOption != null ? this.fontOption.k(1.0F, false) : Vape.INSTANCE.getFontManager().Y();
   }

   public void setAcceptsMouseInput(boolean acceptsMouseInput) {
      this.acceptsMouseInput = acceptsMouseInput;
   }

   public static GuiComponent[] getLegacyComponentState() {
      return legacyState;
   }

   public boolean isShowDisabledOverlayCompat() {
      return this.showDisabledOverlay;
   }

   public RectData getBounds() {
      return new RectData(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L());
   }

   public void setHovered(boolean hovered) {
      this.hovered = hovered;
   }

   public void dispatchMouseEvent(GuiMouseEvent guiMouseEvent) {
      if (!guiMouseEvent.equals(this.lastMouseEvent)) {
         this.lastMouseEvent = guiMouseEvent;
         if (!guiMouseEvent.isCancelled()) {
            for (GuiMouseListener object2 : this.getMouseListeners()) {
               if (object2.Q(new Point(guiMouseEvent.getX(), guiMouseEvent.getY()))) {
                  return;
               }
            }

            ArrayList<GuiComponent> arrayList = new ArrayList<>(this.f());
            Collections.reverse(arrayList);

            for (GuiComponent guiComponent : arrayList) {
               if (guiComponent.V$src$Z$1xhop3l()
                  && !guiComponent.isChildRenderingSuppressed()
                  && guiComponent.w$src$Z$e457mb()
                  && (
                     !(this instanceof FrameComponent)
                        || ((FrameComponent)this).j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc() == null
                        || !((FrameComponent)this).j$src$Lgg_vape_ui_click_frame_FrameHeaderComponent_$175vsfc().w$src$Z$e457mb()
                        || guiComponent instanceof FrameHeaderComponent
                  )
                  && guiComponent.acceptsMouseInput()) {
                  guiComponent.dispatchMouseEvent(guiMouseEvent);
                  if (!(guiComponent instanceof FrameHeaderComponent) && !this.propagateMouseEvents) {
                     return;
                  }
               }
            }

            this.g(guiMouseEvent);

            for (GuiMouseListener guiMouseListener : this.getMouseListeners()) {
               guiMouseListener.g(
                  new Point(MouseInput.getMouseX(), MouseInput.getInvertedMouseY()),
                  guiMouseEvent.getAction() == MouseButton.LEFT_CLICK
                     ? MouseClickButton.LEFT_CLICK
                     : (
                        guiMouseEvent.getAction() == MouseButton.RIGHT_CLICK
                           ? MouseClickButton.RIGHT_CLICK
                           : (guiMouseEvent.getAction() == MouseButton.MIDDLE_CLICK ? MouseClickButton.MIDDLE_CLICK : null)
                     )
               );
            }
         }
      }
   }

   public void setText(String text) {
      this.text = text;
   }

   public void deactivate() {
      this.setHovered(false);
      if (this.active) {
         this.active = false;
         this.onEnable();

         for (GuiActivationListener activationListener : this.activationListeners) {
            activationListener.onActivationChanged(false);
         }
      }
   }

   public void hideToolTips() {
      this.tooltipHoverMillis = 0L;
      this.tooltipHoverStartMillis = 0L;
      this.toolTips.setVisible(false);
      ClientSettings.activeTooltips = null;
   }

   public void addMouseListener(GuiMouseListener mouseListener) {
      this.mouseListeners.add(mouseListener);
   }

   public void addActivationListener(GuiActivationListener activationListener) {
      this.activationListeners.add(activationListener);
   }

   @Override
   public double n() {
      return this.y;
   }

   @Override
   public void S(double y) {
      this.y = y;
   }

   public void addChildren(GuiComponent... children) {
      Collections.addAll(this.f(), children);
   }

   public Value getBoundValue() {
      return this.boundValue;
   }

   public ToolTips getToolTips() {
      return this.toolTips;
   }

   public void i$src$V$c9opdk() {
   }

   public SmoothFontRenderer getFontRenderer(double scale) {
      return this.fontOption != null ? this.fontOption.k((float)scale, false) : Vape.INSTANCE.getFontManager().Y(scale);
   }

   public void setUseExplicitHeight(boolean useExplicitHeight) {
      this.useExplicitHeight = useExplicitHeight;
   }

   public boolean w$src$Z$e457mb() {
      return this.hovered;
   }

   public void setExplicitWidth(double width) {
      this.explicitWidth = width;
      this.width = width;
   }

   public double t$src$D$1x9zexg() {
      double d3 = this.L();
      double d;
      if (this instanceof FrameComponent && Math.max(d3, d = ((FrameComponent)this).d$src$D$ibccpu()) > d) {
         d3 = d;
      }

      return d3;
   }

   @Override
   public double G$src$D$1b2f02a() {
      return this.x;
   }

   public void onDisable() {
      if (this.isShowDisabledOverlay()) {
         GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.getComponentHeight(), this.getDisabledOverlayColor());
      }
   }

   protected void x$src$V$1xc6lqe() {
      if (this.w$src$Z$e457mb() && !this.t()) {
         this.deactivate();

         for (GuiComponent guiComponent : this.f()) {
            guiComponent.deactivate();
         }
      }
   }

   public boolean isIgnoreFrameClipping() {
      return this.ignoreFrameClipping;
   }

   public boolean i(int n, int n2) {
      return this.getBounds().J(n, n2);
   }

   public GuiComponent setDisabledOverlayColor(Color color) {
      this.disabledOverlayColor = color;
      return this;
   }

   public boolean isPropagateMouseEvents() {
      return this.propagateMouseEvents;
   }

   public List<GuiComponent> f() {
      return this.children;
   }

   public void setChildRenderingSuppressed(boolean suppressed) {
      this.childRenderingSuppressed = suppressed;
   }

   public boolean acceptsMouseInput() {
      return this.acceptsMouseInput;
   }

   public List<GuiMouseListener> getGlobalMouseListeners() {
      return this.globalMouseListeners;
   }

   public boolean isRemovable() {
      return this.removable;
   }

   public void setRemovable(boolean removable) {
      this.removable = removable;
   }

   public Frame L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa() {
      if (this.parentHeader != null) {
         return this.parentHeader.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa();
      } else {
         FrameComponent frameComponent = this.getParentFrameComponent();
         if (frameComponent != null) {
            return frameComponent instanceof Frame ? (Frame)frameComponent : frameComponent.L$src$Lgg_vape_ui_click_frame_Frame_$1djx6sa();
         } else if (frameComponent instanceof Frame) {
            return (Frame)frameComponent;
         } else {
            return this instanceof Frame ? (Frame)this : null;
         }
      }
   }

   public void setExplicitHeight(double height) {
      this.explicitHeight = height;
      this.height = height;
   }

   public void removeActivationListener(GuiActivationListener activationListener) {
      this.activationListeners.remove(activationListener);
   }

   public void e(GuiMouseEvent guiMouseEvent) {
      this.U(guiMouseEvent);

      for (GuiComponent guiComponent : this.f()) {
         if (guiComponent.V$src$Z$1xhop3l()) {
            guiComponent.e(guiMouseEvent);
         }
      }
   }

   public void setHorizontalInset(double horizontalInset) {
      this.horizontalInset = horizontalInset;
   }

   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   public void q$src$V$1x8c1kv() {
      this.I();

      for (GuiComponent guiComponent : this.f()) {
         if (guiComponent.V$src$Z$1xhop3l()) {
            guiComponent.q$src$V$1x8c1kv();
         }
      }
   }

   public GuiComponent w(@Nullable String string) {
      if (string == null) {
         this.toolTips = null;
         return this;
      } else if (string.isEmpty()) {
         return this;
      } else {
         this.toolTips = new ToolTips(this, string);
         return this;
      }
   }

   public void T$src$V$1wse0de() {
      this.u();
      List<GuiComponent> list = this.f();
      int n = list.size();

      for (int i = 0; i < n; i++) {
         list.get(i).T$src$V$1wse0de();
      }
   }

   public void K(Color color) {
      GuiRenderPrimitives.C(this.G$src$D$1b2f02a(), this.n(), this.A(), this.L(), color);
      this.getFontRenderer(0.75)
         .d(String.format("x %f, y %f, w %f, h %f", this.G$src$D$1b2f02a(), this.n(), this.A(), this.L()), this.G$src$D$1b2f02a(), this.n(), Color.WHITE);
   }

   public void removeMouseListener(GuiMouseListener mouseListener) {
      this.mouseListeners.remove(mouseListener);
   }

   @Override
   public double A() {
      return this.useExplicitWidth ? this.width : Math.max(this.width, this.x());
   }

   public GuiComponent() {
      this.globalMouseListeners = new CopyOnWriteArrayList<>();
      this.keyTypedListeners = new ArrayList<>();
      this.activationListeners = new ArrayList<>();
      this.children = new ArrayList<>();
   }

   public void q(Frame frame, Frame frame2) {
      this.mouseListeners.add(new FrameOutsideChildClickFilterMouseListener(this, frame, frame2));
   }

   public boolean t() {
      MousePosition mousePosition = RenderUtils.h();
      return this.getBounds().Z(mousePosition);
   }

   public void clearMouseListeners() {
      this.mouseListeners.clear();
   }

   @Override
   public void K(double x) {
      this.x = x;
   }

   public List<GuiKeyTypedListener> getKeyTypedListeners() {
      return this.keyTypedListeners;
   }

   public void addGlobalMouseListener(GuiMouseListener mouseListener) {
      this.globalMouseListeners.add(mouseListener);
   }

   public void c() {
      this.x$src$V$1xc6lqe();
      if (this.tooltipHoverMillis != 0L && this.previousTooltipHoverMillis == this.tooltipHoverMillis && (!this.w$src$Z$e457mb() || !this.V$src$Z$1xhop3l())) {
         this.hideToolTips();
      }

      if (this.toolTips != null) {
         this.toolTips.setActive(true);
      }

      this.previousTooltipHoverMillis = this.tooltipHoverMillis;
      this.H();
      if (this instanceof FrameComponent) {
         ((FrameComponent)this).z$src$V$infu7a();
      }

      for (GuiComponent guiComponent : this.f()) {
         if (guiComponent.V$src$Z$1xhop3l() && !this.isChildRenderingSuppressed()) {
            try {
               guiComponent.c();
            } catch (Exception var4) {
               Vape.logThrowable(var4);
            }
         }
      }
   }

   public double double_G() {
      return this.G$src$D$1b2f02a();
   }

   public double double_n() {
      return this.n();
   }

   public double double_A() {
      return this.A();
   }

   public double double_L() {
      return this.L();
   }

   static {
      setLegacyComponentState(new GuiComponent[4]);
   }
}
