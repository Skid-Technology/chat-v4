package gg.vape.friend.ui;

import gg.vape.friend.OnlineFriend;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.ui.click.component.TextInputComponentBase;
import gg.vape.utils.render.GuiRenderPrimitives;
import java.awt.Color;

public class OnlineChatInputComponent extends TextInputComponentBase {
   private static GuiComponent[] obfuscationComponents;
   private final OnlineChatSender chatSender;
   private final OnlineChatPanel chatPanel;

   public static GuiComponent[] getObfuscationComponents() {
      return obfuscationComponents;
   }

   @Override
   public double C() {
      return 23.0;
   }

   @Override
   public float getRightInset() {
      return super.getRightInset();
   }

   @Override
   public double x() {
      return 100.0;
   }

   @Override
   public void c() {
      super.c();
      GuiRenderPrimitives.L(this.G$src$D$1b2f02a(), this.n() + 1.0, this.A(), J.l);
      if (this.isFocused()) {
         this.setTextColor(J.A);
      } else {
         this.setTextColor(new Color(255, 255, 255, 102));
      }
   }

   public static void setObfuscationComponents(GuiComponent[] components) {
      obfuscationComponents = components;
   }

   private static void handleSendResponse(OnlineFriend friend, String responseMessage) {
      OnlineFriendUiHelper.addFriendChatMessage(friend, null, responseMessage);
   }

   @Override
   public void setInputEnabled(boolean inputEnabled) {
      super.setInputEnabled(inputEnabled);
      this.setPlaceholderText(inputEnabled ? "Type message..." : "User is offline");
      this.getActionButton().setVisible(inputEnabled);
   }

   @Override
   public void submit() {
      String message = this.getText();
      if (!message.isEmpty()) {
         this.setText("");
         this.chatSender.sendChatMessage(message, OnlineChatInputComponent::handleSendResponse);
      }
   }

   @Override
   public float getLeftInset() {
      return 0.0F;
   }

   public OnlineChatInputComponent(OnlineChatPanel chatPanel, OnlineChatSender chatSender) {
      super("Type message...");
      this.getActionButton().setIconResource("newnext");
      this.setBackgroundVisible(false);
      this.chatPanel = chatPanel;
      this.chatSender = chatSender;
      this.setMaxLength(255);
      this.setActionButtonColor(J.B);
   }

   static {
      setObfuscationComponents(new GuiComponent[5]);
   }
}
