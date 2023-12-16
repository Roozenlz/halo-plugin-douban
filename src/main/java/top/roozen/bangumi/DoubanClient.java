package top.roozen.bangumi;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 * @since 2023/7/30
 */
@Component
public class DoubanClient implements IDoubanClient {
    private final WebClient webClient = WebClient.builder().build();
    private static final String DB_API_URL = "https://fatesinger.com/dbapi/user/{userid}/interests?count={count}&start={start}&type={type}&status={status}";

    @Override
    public Mono<ArrayNode> listMovies(DoubanRequest request) {
        return webClient.get()
            .uri(DB_API_URL, request.getUserId(),request.getCount(),request.getStart(),request.getType().name(),request.getStatus().name())
            .retrieve()
            .bodyToMono(ObjectNode.class)
            .map(item->{
                return item.withArray("/interests");
            });
    }
}
