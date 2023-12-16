package top.roozen.bangumi;

import com.fasterxml.jackson.databind.node.ArrayNode;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 * @since 2023/7/30
 */
public interface IDoubanClient {
    Mono<ArrayNode> listMovies(DoubanRequest request);
}
