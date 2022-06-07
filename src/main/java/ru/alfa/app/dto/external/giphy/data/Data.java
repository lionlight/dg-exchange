package ru.alfa.app.dto.external.giphy.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import ru.alfa.app.dto.external.giphy.data.image.Images;

@lombok.Data
@Builder
public class Data {
    private String type;
    private String id;
    private String url;
    private String slug;
    @JsonProperty("bitly_gif_url")
    private String bitlyGifUrl;
    @JsonProperty("bitly_url")
    private String bitlyUrl;
    @JsonProperty("embed_url")
    private String embedUrl;
    private String username;
    private String source;
    private String title;
    private String rating;
    @JsonProperty("content_url")
    private String contentUrl;
    @JsonProperty("source_tld")
    private String sourceTld;
    @JsonProperty("source_post_url")
    private String sourcePostUrl;
    @JsonProperty("is_sticker")
    private String isSticker;
    @JsonProperty("import_datetime")
    private String importDatetime;
    @JsonProperty("trending_datetime")
    private String trendingDatetime;
    private Images images;
    private User user;
    @JsonProperty("analytics_response_payload")
    private String analyticsResponsePayload;
    private Analytics analytics;
}
