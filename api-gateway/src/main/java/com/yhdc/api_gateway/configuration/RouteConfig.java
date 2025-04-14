package com.yhdc.api_gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(userRoute -> userRoute.path("/user/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                        .uri("http://localhost:8081")

                )
                .route(storeRoute -> storeRoute.path("/store/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                        .uri("http://localhost:8082")

                )
                .route(storeRoute -> storeRoute.path("/product/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                        .uri("http://localhost:8083")

                )
                .route(auctionRoute -> auctionRoute.path("/order/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                                .uri("http://localhost:8084")

                )
                .route(invoiceRoute -> invoiceRoute.path("/inventory/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                                .uri("http://localhost:8085")

                )
                .route(deliveryRoute -> deliveryRoute.path("/notification/**")
                        .filters(f -> f
                                .prefixPath("/event")
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                                .uri("http://localhost:8086")

                )
                .route(videoCatalogRoute -> videoCatalogRoute.path("/image/**")
                        .filters(f -> f
                                .prefixPath("/file")
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                                .uri("http://localhost:8100")
                )
                .route(videoCatalogRoute -> videoCatalogRoute.path("/video-catalog/**")
                        .filters(f -> f
                                .prefixPath("/file")
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                                .uri("http://localhost:8101")
                )
                .route(videoStreamRoute -> videoStreamRoute.path("/video-stream/**")
                        .filters(f -> f
                                .prefixPath("/file")
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                                .uri("http://localhost:8102")
                )
                .route(aiRoute -> aiRoute.path("/chat-client/**")
                        .filters(f -> f
                                .prefixPath("/ai")
                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
                        )
                                .uri("http://localhost:8200")
                )
                .build();
    }

}
