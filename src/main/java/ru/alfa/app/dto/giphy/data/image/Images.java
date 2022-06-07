package ru.alfa.app.dto.giphy.data.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Images {
    private Original original;
    private Downsized downsized;
    @JsonProperty("downsized_large")
    private DownsizedLarge downsizedLarge;
    @JsonProperty("downsized_medium")
    private DownsizedMedium downsizedMedium;
    @JsonProperty("downsized_small")
    private DownsizedSmall downsizedSmall;
    @JsonProperty("downsized_still")
    private DownsizedStill downsizedStill;
    @JsonProperty("fixed_height")
    private FixedHeight fixedHeight;
    @JsonProperty("fixed_height_downsampled")
    private FixedHeightDownsampled fixedHeightDownsampled;
    @JsonProperty("fixed_height_small")
    private FixedHeightSmall fixedHeightSmall;
    @JsonProperty("fixed_height_small_still")
    private FixedHeightSmallStill fixedHeightSmallStill;
    @JsonProperty("fixed_height_still")
    private FixedHeightStill fixedHeightStill;
    @JsonProperty("fixed_width")
    private FixedWidth fixedWidth;
    @JsonProperty("fixed_width_downsampled")
    private FixedWidthDownsampled fixedWidthDownsampled;
    @JsonProperty("fixed_width_small")
    private FixedWidthSmall fixedWidthSmall;
    @JsonProperty("fixed_width_small_still")
    private FixedWidthSmallStill fixedWidthSmallStill;
    @JsonProperty("fixed_width_still")
    private FixedWidthStill fixedWidthStill;
    private Looping looping;
    @JsonProperty("original_still")
    private OriginalStill originalStill;
    @JsonProperty("original_mp4")
    private OriginalMp4 originalMp4;
    private Preview preview;
    @JsonProperty("preview_gif")
    private PreviewGif previewGif;
    @JsonProperty("preview_webp")
    private PreviewWebp previewWebp;
    private Hd hd;
    @JsonProperty("480w_still")
    private WStill480 wStill480;
}
