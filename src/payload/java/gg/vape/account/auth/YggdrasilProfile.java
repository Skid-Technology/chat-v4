package gg.vape.account.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YggdrasilProfile {
   @Expose
   @SerializedName("legacy")
   private boolean legacy;
   @Expose
   @SerializedName("id")
   private String id;
   @Expose
   @SerializedName("name")
   private String name;

   public String getName() {
      return this.name;
   }

   public String getId() {
      return this.id;
   }

   public boolean isLegacy() {
      return this.legacy;
   }
}
