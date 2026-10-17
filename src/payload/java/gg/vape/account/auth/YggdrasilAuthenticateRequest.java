package gg.vape.account.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YggdrasilAuthenticateRequest implements YggdrasilAuthRequest {
   @Expose
   @SerializedName("password")
   private final String password;
   @Expose
   @SerializedName("agent")
   private final YggdrasilAgent agent;
   @Expose
   @SerializedName("username")
   private final String username;
   @Expose(serialize = false, deserialize = false)
   @SerializedName("clientToken")
   private final String clientToken;

   public YggdrasilAuthenticateRequest(String username, String password, String clientToken, YggdrasilAgent agent) {
      this.agent = agent;
      this.username = username;
      this.password = password;
      this.clientToken = clientToken;
   }

   public YggdrasilAuthenticateRequest(String username, String password, String clientToken) {
      this(username, password, clientToken, new YggdrasilAgent());
   }

   public YggdrasilAuthenticateRequest(String username, String password) {
      this(username, password, "", new YggdrasilAgent());
   }

   public YggdrasilAuthenticateRequest(String username, String password, YggdrasilAgent agent) {
      this(username, password, "", agent);
   }
}
