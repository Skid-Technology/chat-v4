package gg.vape.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public class PublicProfilePartialJsonPayloadBuilder {
   public static JsonObject build(
      long profileId,
      @Nullable UUID derivedFrom,
      @Nullable String name,
      @Nullable String description,
      @Nullable List<String> tags,
      @Nullable Boolean listed,
      @Nullable Boolean anonymous,
      @Nullable Boolean shareCodeFriendsOnly,
      @Nullable JsonObject profileData
   ) {
      JsonArray tagsJson = new JsonArray();
      if (tags != null) {
         for (String tag : tags) {
            tagsJson.add(new JsonPrimitive(tag));
         }
      }

      JsonObject payload = new JsonObject();
      payload.addProperty("profileId", profileId);
      payload.add("derivedFrom", ApiHttpClient.GSON.toJsonTree(derivedFrom));
      payload.addProperty("name", name);
      payload.addProperty("vapeVersion", "4.22");
      payload.addProperty("description", description);
      payload.add("tags", tagsJson);
      payload.addProperty("listed", listed);
      payload.addProperty("anonymous", anonymous);
      payload.addProperty("shareCodeFriendsOnly", shareCodeFriendsOnly);
      payload.add("profileData", profileData);
      return payload;
   }
}
