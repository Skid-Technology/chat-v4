/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.Nullable
 */
package gg.vape.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.vape.api.ApiHttpClient;
import gg.vape.api.PagedResult;
import gg.vape.config.ConfigJsonUtils;
import gg.vape.config.PublicProfileReview;
import gg.vape.config.PublicProfileShareInfo;
import gg.vape.config.PublicProfileUser;
import gg.vape.ui.click.component.GuiComponent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class PublicProfile {
    private String shareCode;
    @Nullable
    private final Date updatedDate;
    private final String name;
    private final Map<String, Object> data;
    private final String description;
    private final Date creationDate;
    private final PagedResult<PublicProfileReview> reviews;
    private long dislikes;
    @Nullable
    private final PublicProfileShareInfo shareInfo;
    private static GuiComponent[] sharedGuiComponents;
    private final long profileId;
    private final long downloads;
    private final long version;
    @Nullable
    private PublicProfileReview viewerReview;
    private final List<String> tags;
    private long likes;
    @Nullable
    private final PublicProfileUser owner;

    public long getReviewCount() {
        if (this.reviews == null) {
            return this.viewerReview != null ? 1L : 0L;
        }
        long count = this.reviews.getTotalElements();
        if (this.viewerReview != null && this.reviews.getContent() != null) {
            boolean alreadyInList = false;
            for (PublicProfileReview r : this.reviews.getContent()) {
                if (r.getCommentId() != this.viewerReview.getCommentId()) continue;
                alreadyInList = true;
                break;
            }
            if (!alreadyInList) {
                ++count;
            }
        }
        return count;
    }

    public String getDescription() {
        return this.description;
    }

    public int getApprovalPercentage() {
        long reviewCount = this.getReviewCount();
        if (reviewCount == 0L) {
            return 0;
        }
        return (int)((double)this.getLikes() / (double)reviewCount * 100.0);
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    @Nullable
    public PublicProfileReview getViewerReview() {
        return this.viewerReview;
    }

    public long getDownloads() {
        return this.downloads;
    }

    PublicProfile(long profileId, @Nullable PublicProfileUser owner, String name, String description, List<String> tags, Map<String, Object> data, @Nullable String shareCode, long version, long likes, long dislikes, long downloads, Date creationDate, @Nullable Date updatedDate, @Nullable PublicProfileReview viewerReview, PagedResult<PublicProfileReview> reviews, @Nullable PublicProfileShareInfo shareInfo) {
        this.profileId = profileId;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.data = data;
        this.shareCode = shareCode;
        this.version = version;
        this.likes = likes;
        this.dislikes = dislikes;
        this.downloads = downloads;
        this.creationDate = creationDate;
        this.updatedDate = updatedDate;
        this.viewerReview = viewerReview;
        this.reviews = reviews;
        this.shareInfo = shareInfo;
    }

    public long getDislikes() {
        return this.dislikes;
    }

    public void setDislikes(long dislikes) {
        this.dislikes = dislikes;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public static GuiComponent[] getSharedGuiComponents() {
        return sharedGuiComponents;
    }

    public void setViewerReview(@Nullable PublicProfileReview viewerReview) {
        this.viewerReview = viewerReview;
    }

    public String toString() {
        return "FullPublicProfile{profileId=" + this.profileId + ", owner=" + this.owner + ", name='" + this.name + '\'' + ", description='" + this.description + '\'' + ", tags=" + this.tags + ", data=" + this.data + ", version=" + this.version + ", likes=" + this.likes + ", dislikes=" + this.dislikes + ", reviews=" + this.reviews + '}';
    }

    public long getLikes() {
        return this.likes;
    }

    public long getVersion() {
        return this.version;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public PagedResult<PublicProfileReview> getReviews() {
        return this.reviews;
    }

    @Nullable
    @Contract(value="!null -> !null; null -> null")
    public static PublicProfile fromJson(@Nullable JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return null;
        }
        JsonObject object = element.getAsJsonObject();
        ArrayList<String> tags = new ArrayList<String>();
        if (object.has("tags") && !object.get("tags").isJsonNull() && object.get("tags").isJsonArray()) {
            for (JsonElement tagElement : object.get("tags").getAsJsonArray()) {
                tags.add(tagElement.getAsString());
            }
        }
        try {
            Long profileId = ConfigJsonUtils.getLong(object, "profileId");
            Long version = ConfigJsonUtils.getLong(object, "version");
            Long likes = ConfigJsonUtils.getLong(object, "likes");
            Long dislikes = ConfigJsonUtils.getLong(object, "dislikes");
            Long downloads = ConfigJsonUtils.getLong(object, "downloads");
            String name = ConfigJsonUtils.getString(object, "name");
            String description = ConfigJsonUtils.getString(object, "description");
            String creationDateStr = ConfigJsonUtils.getString(object, "creationDate");
            return new PublicProfile(profileId != null ? profileId : 0L, PublicProfileUser.fromJson(object.get("owner")), name != null ? name : "", description != null ? description : "", tags, object.has("data") && !object.get("data").isJsonNull() ? ApiHttpClient.GSON.fromJson(object.get("data"), Map.class) : null, ConfigJsonUtils.getString(object, "shareCode"), version != null ? version : 0L, likes != null ? likes : 0L, dislikes != null ? dislikes : 0L, downloads != null ? downloads : 0L, creationDateStr != null ? ApiHttpClient.parseApiDate(creationDateStr) : new Date(), ApiHttpClient.parseApiDate(ConfigJsonUtils.getString(object, "updatedDate")), object.has("viewerReview") && !object.get("viewerReview").isJsonNull() ? PublicProfileReview.fromJson(object.get("viewerReview")) : null, object.has("reviews") && !object.get("reviews").isJsonNull() && object.get("reviews").isJsonObject() ? PagedResult.fromJson(object.get("reviews").getAsJsonObject(), PublicProfileReview::fromJson) : null, object.has("metadata") && !object.get("metadata").isJsonNull() ? PublicProfileShareInfo.fromJson(object.get("metadata")) : null);
        }
        catch (ParseException parseException) {
            throw new RuntimeException(parseException);
        }
    }

    public long getProfileId() {
        return this.profileId;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    private static Exception preserveException(Exception error) {
        return error;
    }

    public String getUppercaseShareCode() {
        return this.shareCode.toUpperCase();
    }

    public Date getLatestDate() {
        return this.updatedDate != null ? this.updatedDate : this.creationDate;
    }

    @Nullable
    public PublicProfileShareInfo getShareInfo() {
        return this.shareInfo;
    }

    @Nullable
    public PublicProfileUser getOwner() {
        return this.owner;
    }

    public static void setSharedGuiComponents(GuiComponent[] components) {
        sharedGuiComponents = components;
    }

    static {
        PublicProfile.setSharedGuiComponents(null);
    }
}

