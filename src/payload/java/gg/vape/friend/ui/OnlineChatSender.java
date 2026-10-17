package gg.vape.friend.ui;

import gg.vape.friend.OnlineFriend;
import java.util.function.BiConsumer;

public interface OnlineChatSender {
   void sendChatMessage(String var1, BiConsumer<OnlineFriend, String> var2);
}
