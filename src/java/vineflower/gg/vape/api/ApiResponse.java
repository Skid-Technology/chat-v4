package gg.vape.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import gg.vape.ui.click.component.GuiComponent;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ApiResponse<T> {
   @SerializedName("successful")
   private final boolean successful;
   private static GuiComponent[] legacyComponentState;
   @SerializedName("error")
   @Nullable
   private final String error;
   @SerializedName("data")
   @Nullable
   private final T data;

   public static GuiComponent[] getLegacyComponentState() {
      return legacyComponentState;
   }

   public static <T> ApiResponse<T> fromJson(JsonObject responseJson, Function<JsonElement, T> dataParser) {
      JsonElement dataElement = responseJson.get("data");
      return dataElement != null && !dataElement.isJsonNull() ? success(dataParser.apply(dataElement)) : failure(responseJson.get("error").getAsString());
   }

   public static <T> ApiResponse<T> success(@NotNull T data) {
      return new ApiResponse<>(data, null, true);
   }

   public static <T> ApiResponse<T> failure(@Nullable String error) {
      return new ApiResponse<>(null, error, false);
   }

   public boolean isSuccessful() {
      return this.successful;
   }

   @Nullable
   public String getError() {
      return this.error;
   }

   @Nullable
   public T getData() {
      return this.data;
   }

   ApiResponse(@Nullable T data, @Nullable String error, boolean successful) {
      this.data = data;
      this.error = error;
      this.successful = successful;
   }

   public static void setLegacyComponentState(GuiComponent[] state) {
      legacyComponentState = state;
   }

   @Override
   public String toString() {
      return "VapeResponse{data=" + this.data + ", error='" + this.error + '\'' + ", successful=" + this.successful + '}';
   }

   static {
      setLegacyComponentState(new GuiComponent[5]);
   }
}
