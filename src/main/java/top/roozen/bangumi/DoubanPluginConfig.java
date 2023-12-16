package top.roozen.bangumi;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import run.halo.app.extension.ReactiveExtensionClient;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 * @since 2023/7/30
 */
@Configuration
@RequiredArgsConstructor
public class DoubanPluginConfig {
    private final ReactiveExtensionClient client;

    @Bean
    RouterFunction<ServerResponse> repoTemplateRouter() {
        return RouterFunctions.route()
            .GET("/movies", request -> client.list(DoubanMovie.class, null, null)
                .collectList()
                .flatMap(movies -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("movies", movies);
                    return ServerResponse.ok().render("movies", model);
                })
            )
            .build();
    }
}

