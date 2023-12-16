package top.roozen.bangumi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.finders.Finder;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 * @since 2023/7/30
 */
@Finder("doubanFinder")
@RequiredArgsConstructor
public class DoubanFinder {
    private final DoubanClient doubanClient;
    private final ReactiveSettingFetcher settingFetcher;

    private String DOUBAN_ID;

    public ArrayNode getMovies(){
        return doubanClient.listMovies(DoubanRequest.builder().build()).block();
    }

    public List<DoubanMovie> getMovies(Integer count,Integer start,String type,String status){
        if(DOUBAN_ID == null){
            DOUBAN_ID = getDoubanId();
        }
        ArrayNode arrayNode = doubanClient.listMovies(DoubanRequest.builder()
            .userId(DOUBAN_ID)
            .count(count)
            .start(start)
            .type(DoubanRequest.DoubanType.valueOf(type))
            .status(DoubanRequest.DoubanStatus.valueOf(status))
            .build()).block();
        List<DoubanMovie> movies = new ArrayList<>();
        // 'name' => $interest['subject']['title'],
        // 'poster' => $interest['subject']['pic']['large'],
        // 'douban_id' => $interest['subject']['id'],
        // 'douban_score' => $interest['subject']['rating']['value'],
        // 'link' => $interest['subject']['url'],
        // 'year' => $interest['subject']['year'],
        // 'type' => $type,
        // 'pubdate' => $interest['subject']['pubdate'] ? $interest['subject']['pubdate'][0] : '',
        // 'card_subtitle' => $interest['subject']['card_subtitle'],
        for(JsonNode node : arrayNode){
            JsonNode subject = node.get("subject");
            movies.add(DoubanMovie.builder()
                .spec(DoubanMovie.DoubanMovieSpec.builder()
                    .douban_id(subject.get("id").asText())
                    .name(subject.get("title").asText())
                    .link(subject.get("url").asText())
                    .poster(subject.get("pic").get("large").asText())
                    .year(subject.get("year").asText())
                    .douban_score(subject.get("rating").get("value").asText())
                    .type(type)
                    .card_subtitle(subject.get("card_subtitle").asText())
                    .pubdate(subject.get("pubdate") == null ? "" : subject.get("pubdate").get(0).asText())
                    .build()).build());
        }
        return movies;
    }

    // private final DoubanClient bilibiliBangumiClient;
    // public List<DoubanMovie> getBiliData(int typeNum, int status, int ps, int pn) {
    //     List<ArrayNode> block = bilibiliBangumiClient.listBiliBangumiByPage(
    //         BilibiliBangumiRequest.builder()
    //             .vmid(getBiliId().block())
    //             .typeNum(typeNum)
    //             .status(status)
    //             .ps(ps)
    //             .pn(pn)
    //             .build()).map((item) -> {
    //         return item.withArray("/list");
    //     }).collectList().block();
    //     return block.stream().flatMap(item -> {
    //         ArrayList<ObjectNode> objectNodes = new ArrayList<>();
    //         item.forEach(jsonNode -> objectNodes.add((ObjectNode) jsonNode));
    //         return objectNodes.stream();
    //     }).map((item -> {
    //         DoubanMovie bangumi = new DoubanMovie();
    //         bangumi.setMetadata(new Metadata());
    //         bangumi.getMetadata().setGenerateName("ban-");
    //         bangumi.setSpec(new DoubanMovie.BangumiSpec());
    //         DoubanMovie.BangumiSpec spec = bangumi.getSpec();
    //         spec.setTitle(item.get("title").textValue());
    //         spec.setType(item.get("season_type_name").textValue());
    //         ArrayNode jsonNodes = item.withArray("/areas");
    //         if (jsonNodes != null) {
    //             JsonNode jsonNode = jsonNodes.get(0);
    //             if (jsonNode != null) {
    //                 String s = jsonNode.get("name").textValue();
    //                 spec.setArea(s);
    //             }
    //         }
    //         spec.setCover(
    //             item.get("cover").textValue().replaceFirst("^http[^s]$", "https")
    //                 + "@220w_280h.webp");
    //         int total_count = item.get("total_count").intValue();
    //         spec.setTotalCount(total_count >= 0 ? (total_count == -1 ? "未完结"
    //             : String.format("全%d%s", total_count, typeNum == 1 ? "话" : "集")) : "-");
    //         spec.setId(item.get("media_id").asText());
    //         JsonNode stat = item.get("stat");
    //         spec.setFollow(count(stat.get("follow").asLong()));
    //         spec.setView(count(stat.get("view").asLong()));
    //         spec.setDanmaku(count(stat.get("danmaku").asLong()));
    //         spec.setCoin(count(stat.get("coin").asLong()));
    //         JsonNode jsonNode = item.get("rating");
    //         if (jsonNode != null) {
    //             String score = jsonNode.get("score").asText();
    //             spec.setScore(score);
    //         }
    //         spec.setDes(item.get("evaluate").textValue());
    //         spec.setUrl("https://www.bilibili.com/bangumi/media/md" + spec.getId());
    //         return bangumi;
    //     })).collect(Collectors.toList());
    // }
    //
    // public List<DoubanMovie> getBiliDataAll(int typeNum, int status) {
    //     Integer dataTotal = getDataTotal(typeNum, status);
    //     ArrayList<DoubanMovie> res = new ArrayList<>();
    //     for (int i = 1; i <= (int) Math.ceil(dataTotal / 30) + 1; i++) {
    //         res.addAll(getBiliData(typeNum, status, 30, 1 * i));
    //     }
    //     return res;
    // }
    //
    // public Integer getDataTotal(int typeNum, int status) {
    //     List<ObjectNode> block = bilibiliBangumiClient.listBiliBangumiByPage(
    //         BilibiliBangumiRequest.builder()
    //             .vmid(getBiliId().block())
    //             .typeNum(typeNum)
    //             .status(status)
    //             .ps(1)
    //             .pn(1)
    //             .build()).collectList().block();
    //     return block.stream().map(item ->
    //         item.get("total").intValue()
    //     ).collect(Collectors.toList()).get(0);
    // }
    //
    // private String count(long num) {
    //     if (num < 0) {
    //         return "-";
    //     }
    //     if (num > 10000 && num < 100000000) {
    //         return String.format(Locale.CHINA, "%.1f 万", num / 10000.0);
    //     } else if (num > 100000000) {
    //         return String.format(Locale.CHINA, "%.1f 亿", num / 100000000.0);
    //     } else {
    //         return num + "";
    //     }
    // }
    //
    String getDoubanId() {
        return this.settingFetcher.get("base")
            .map(setting -> setting.get("doubanId").asText("241930182"))
            .defaultIfEmpty("241930182").block();
    }
}
