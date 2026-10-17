package gg.vape.event;

import gg.vape.Vape;
import gg.vape.event.impl.EventChat;
import gg.vape.event.impl.EventChatMessageRender;
import gg.vape.event.impl.EventEntityRenderState;
import gg.vape.event.impl.EventNameFormat;
import gg.vape.event.impl.EventPlayerTabOverlayDisplayName;
import gg.vape.friend.FriendAliasDisplayNameListener;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.world.cheststeal.ChestStealInventoryState;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ITextComponent;
import gg.vape.wrapper.impl.ScorePlayerTeamTextComponent;
import gg.vape.wrapper.impl.StringTextComponentBase;
import gg.vape.wrapper.impl.TextComponentBaseBridge;
import gg.vape.wrapper.impl.TextComponentString;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class EventNameFormatRewriteService extends FriendAliasDisplayNameListener {
   private static String[] obfuscationState;

   private ITextComponent rewriteComponent(ITextComponent component) {
      if (ForgeVersion.MC_1_20_6.d()) {
         return this.rewriteComponentTree(component);
      } else if (ForgeVersion.MC_1_16_5.d()) {
         this.rewriteLegacyStringComponents(component);
         return component;
      } else {
         Vape.notifyNativeStackTrace();
         return component;
      }
   }

   private Set<TextComponentString> collectStringComponents(ITextComponent component, Set<TextComponentString> components) {
      if (ForgeVersion.MC_1_16_5_ACTUAL.Y()) {
         throw new UnsupportedOperationException("getTextComponents should only be called on versions <= 1.16.5");
      } else {
         if (component.isInstance(MappedClasses.ux)) {
            ChestStealInventoryState translationComponent = new ChestStealInventoryState(component.getObject());

            for (Object siblingHandle : translationComponent.getSiblings()) {
               ITextComponent sibling = new ITextComponent(siblingHandle);
               if (!sibling.isNull() && sibling.isInstance(MappedClasses.Yr)) {
                  this.collectStringComponents(sibling, components);
               }
            }
         }

         for (ITextComponent sibling : component.G()) {
            if (!sibling.isNull() && sibling.isInstance(MappedClasses.z9)) {
               TextComponentString stringComponent = new TextComponentString(sibling.getObject());
               this.collectStringComponents(stringComponent, components);
            }
         }

         if (component.isInstance(MappedClasses.z9)) {
            components.add(new TextComponentString(component.getObject()));
         }

         return components;
      }
   }

   @EventHandler
   public void onNameFormat(EventNameFormat event) {
      if (this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends()) {
         ITextComponent originalComponent = event.getDisplayName();
         ITextComponent rewrittenComponent;
         if (shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
            event.setDisplayName(rewrittenComponent);
         }
      }
   }

   @EventHandler
   public void onPlayerTabDisplayName(EventPlayerTabOverlayDisplayName event) {
      if (this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends()) {
         ITextComponent originalComponent = event.getDisplayName();
         ITextComponent rewrittenComponent;
         if (shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
            event.setDisplayName(rewrittenComponent);
         }
      }
   }

   private static boolean shouldReplace(ITextComponent original, ITextComponent rewritten) {
      return original.isNotNull() && rewritten.isNotNull() && (ForgeVersion.MC_1_20_6.v() || !original.equals(rewritten));
   }

   public static String[] getObfuscationState() {
      return obfuscationState;
   }

   private StringTextComponentBase rewriteStringPayload(StringTextComponentBase payload) {
      ITextComponent rewrittenComponent;
      ITextComponent originalComponent;
      if (payload.isInstance(MappedClasses.qT)
         && !(originalComponent = new ScorePlayerTeamTextComponent(payload.getObject()))
            .equals(rewrittenComponent = this.rewriteLiteralComponent((ScorePlayerTeamTextComponent)originalComponent))) {
         return new StringTextComponentBase(rewrittenComponent.getObject());
      } else {
         return payload.isInstance(MappedClasses.ux)
               && !(originalComponent = new ChestStealInventoryState(payload.getObject()))
                  .equals(rewrittenComponent = this.rewriteTranslationComponent((ChestStealInventoryState)originalComponent))
            ? new StringTextComponentBase(rewrittenComponent.getObject())
            : payload;
      }
   }

   @EventHandler
   public void onChat(EventChat event) {
      if (!ForgeVersion.MC_1_16_5_ACTUAL.Y() && this.isAliasSpoofEnabled() && this.hasFriends()) {
         ITextComponent originalComponent = event.getMessage();
         ITextComponent rewrittenComponent;
         if (shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
            event.setMessage(rewrittenComponent);
         }
      }
   }

   private ITextComponent rewriteComponentTree(ITextComponent component) {
      StringTextComponentBase originalPayload = component.F();
      StringTextComponentBase rewrittenPayload;
      boolean changed = !originalPayload.equals(rewrittenPayload = this.rewriteStringPayload(originalPayload));
      List<ITextComponent> siblings = component.G();
      ArrayList<ITextComponent> rewrittenSiblings = new ArrayList<>();

      for (ITextComponent sibling : siblings) {
         ITextComponent rewrittenSibling = this.rewriteComponent(sibling);
         rewrittenSiblings.add(rewrittenSibling);
         if (!changed && !rewrittenSibling.equals(sibling)) {
            changed = true;
         }
      }

      return (ITextComponent)(changed ? TextComponentBaseBridge.create(rewrittenPayload, rewrittenSiblings, component.J()) : component);
   }

   @EventHandler
   public void onChatMessageRender(EventChatMessageRender event) {
      ITextComponent originalComponent = event.getContentComponent();
      ITextComponent rewrittenComponent;
      if (shouldReplace(originalComponent, rewrittenComponent = this.rewriteComponent(originalComponent))) {
         event.setOutputContentComponent(rewrittenComponent);
      }
   }

   private static UnsupportedOperationException identityException(UnsupportedOperationException exception) {
      return exception;
   }

   private ScorePlayerTeamTextComponent rewriteLiteralComponent(ScorePlayerTeamTextComponent component) {
      if (ForgeVersion.MC_1_16_5_ACTUAL.B()) {
         throw new UnsupportedOperationException("processStringTextComponent should only be called on versions >= 1.16.6");
      } else if (component.isNull()) {
         return component;
      } else {
         String text = component.Y();
         if (text == null) {
            return component;
         } else {
            String rewrittenText = this.getReplacedDisplayName(text, this.getTargetedFriends());
            if (rewrittenText == null) {
               return component;
            } else {
               return text.equalsIgnoreCase(rewrittenText)
                  ? component
                  : (ForgeVersion.MC_1_20_6.d() ? ScorePlayerTeamTextComponent.P(rewrittenText) : ScorePlayerTeamTextComponent.B(rewrittenText));
            }
         }
      }
   }

   private ChestStealInventoryState rewriteTranslationComponent(ChestStealInventoryState component) {
      if (ForgeVersion.MC_1_20_6.v()) {
         throw new UnsupportedOperationException("processChatComponentTranslation should only be called on versions >= 1.20.6");
      } else {
         boolean changed = false;
         ArrayList<Object> rewrittenArguments = new ArrayList<>();

         for (Object argument : component.getFormatArguments()) {
            if (argument == null) {
               rewrittenArguments.add(null);
            } else if (MappedClasses.Yr.isInstance(argument)) {
               Object originalArgument = new ITextComponent(argument);
               Object rewrittenArgument = this.rewriteComponent((ITextComponent)originalArgument);
               rewrittenArguments.add(((Wrapper)rewrittenArgument).getObject());
               if (!changed && !((Wrapper)originalArgument).equals(rewrittenArgument)) {
                  changed = true;
               }
            } else if (argument instanceof String) {
               Object originalArgument = (String)argument;
               Object rewrittenArgument = this.getReplacedDisplayName((String)originalArgument, this.getTargetedFriends());
               rewrittenArguments.add(rewrittenArgument);
               if (!changed && !((String)originalArgument).equalsIgnoreCase((String)rewrittenArgument)) {
                  changed = true;
               }
            } else {
               rewrittenArguments.add(argument);
            }
         }

         return !changed
            ? component
            : (
               ForgeVersion.MC_1_20_6.d()
                  ? ChestStealInventoryState.createTranslationWithFallback(component.getTranslationKey(), component.getFallback(), rewrittenArguments.toArray())
                  : ChestStealInventoryState.createTranslation(component.getTranslationKey(), rewrittenArguments.toArray())
            );
      }
   }

   public static void setObfuscationState(String[] state) {
      obfuscationState = state;
   }

   @EventHandler
   public void onEntityRenderState(EventEntityRenderState event) {
      if (this.isAliasEnabled() && this.isAliasSpoofEnabled() && this.hasFriends()) {
         ITextComponent displayName = event.getEntityRenderState().d();
         if (!displayName.isNull()) {
            ITextComponent rewrittenDisplayName = this.rewriteComponent(displayName);
            if (shouldReplace(displayName, rewrittenDisplayName)) {
               event.getEntityRenderState().Z(rewrittenDisplayName);
            }
         }
      }
   }

   private void rewriteLegacyStringComponents(ITextComponent component) {
      for (TextComponentString stringComponent : this.collectStringComponents(component, new HashSet<>())) {
         String text = stringComponent.getText();
         String rewrittenText = this.getReplacedDisplayName(text, this.getTargetedFriends());
         if (rewrittenText != null && !text.equalsIgnoreCase(rewrittenText)) {
            stringComponent.setText(rewrittenText);
         }
      }
   }

   static {
      setObfuscationState(null);
   }
}
