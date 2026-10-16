package gg.vape.config;

import java.util.Arrays;
import java.util.List;

public enum PublicProfileSortMode {
   RATED("rated", "Top Rated"),
   DOWNLOADED("downloaded", "Most Downloaded"),
   NEWEST("newest", "Newest");

   private final String queryValue;
   public static final List<PublicProfileSortMode> VALUES = Arrays.asList(values());
   private static final PublicProfileSortMode[] ENUM_VALUES = new PublicProfileSortMode[]{RATED, DOWNLOADED, NEWEST};
   private final String displayName;

   public String getDisplayName() {
      return this.displayName;
   }

   public String getQueryValue() {
      return this.queryValue;
   }

   private PublicProfileSortMode(String queryValue, String displayName) {
      this.queryValue = queryValue;
      this.displayName = displayName;
   }

   static {
      String[] var10000 = new String[]{"rated", "Most Downloaded", "newest", "downloaded", "Top Rated", "Newest", "RATED", "NEWEST", "DOWNLOADED"};
   }
}
