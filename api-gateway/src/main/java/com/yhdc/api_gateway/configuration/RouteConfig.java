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
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8081")

                )
                .route(storeRoute -> storeRoute.path("/str/**")
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8082")

                )
                .route(auctionRoute -> auctionRoute.path("/act/**")
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8083")

                )
                .route(invoiceRoute -> invoiceRoute.path("/inv/**")
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8084")

                )
                .route(deliveryRoute -> deliveryRoute.path("/dlv/**")
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8085")

                )
                .route(videoCatalogRoute -> videoCatalogRoute.path("/vcs/**")
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8090")
                )
                .route(videoStreamRoute -> videoStreamRoute.path("/vss/**")
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8091")
                )
                .route(aiRoute -> aiRoute.path("/ai/**")
//                        .filters(f -> f
//                                .prefixPath("/api")
//                                .addResponseHeader("X-Powered-By", "Fiorano Gateway Service")
//                        )
                                .uri("http://localhost:8100")
                )
                .build();
    }

}
