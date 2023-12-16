package top.roozen.bangumi;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import run.halo.app.extension.AbstractExtension;
import run.halo.app.extension.GVK;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 * @since 2023/7/30
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@GVK(group = "douban.roozen.top", version = "v1alpha1",
    kind = "DoubanMovie", singular = "douban_movie", plural = "douban_movies")
public class DoubanMovie extends AbstractExtension {
    @Schema(requiredMode = REQUIRED)
    private DoubanMovieSpec spec;

    @Data
    @Builder
    public static class DoubanMovieSpec {
        // 'name' => $interest['subject']['title'],
        // 'poster' => $interest['subject']['pic']['large'],
        // 'douban_id' => $interest['subject']['id'],
        // 'douban_score' => $interest['subject']['rating']['value'],
        // 'link' => $interest['subject']['url'],
        // 'year' => $interest['subject']['year'],
        // 'type' => $type,
        // 'pubdate' => $interest['subject']['pubdate'] ? $interest['subject']['pubdate'][0] : '',
        // 'card_subtitle' => $interest['subject']['card_subtitle'],
        // @Schema(requiredMode = REQUIRED)
        private String name;
        // @Schema(requiredMode = REQUIRED)
        private String poster;
        // @Schema(requiredMode = REQUIRED)
        private String douban_id;
        // @Schema(requiredMode = REQUIRED)
        private String douban_score;
        // @Schema(requiredMode = REQUIRED)
        private String link;
        // @Schema(requiredMode = REQUIRED)
        private String year;
        // @Schema(requiredMode = REQUIRED)
        private String type;
        // @Schema(requiredMode = REQUIRED)
        private String pubdate;
        // @Schema(requiredMode = REQUIRED)
        private String card_subtitle;
    }

}
