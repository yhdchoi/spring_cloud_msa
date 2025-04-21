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
                // Account Route
                .route(userRoute -> userRoute.path("/account/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8081")

                )
                .route(accountSwaggerRoute -> accountSwaggerRoute.path("/aggregate/account-service/v3/api-docs")
                        .filters(f -> f
                                .setPath("/api-docs")
                        )
                        .uri("http://localhost:8081")
                )

                // Store Route
                .route(storeRoute -> storeRoute.path("/store/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8082")

                )

                .route(productRoute -> productRoute.path("/product/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8083")

                )
                .route(orderRoute -> orderRoute.path("/order/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8084")

                )
                .route(inventoryRoute -> inventoryRoute.path("/inventory/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8085")

                )
                .route(notificationRoute -> notificationRoute.path("/notification/**")
                        .filters(f -> f
                                .prefixPath("/event")
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8086")

                )
                .route(videoCatalogRoute -> videoCatalogRoute.path("/file/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8100")
                )

                .route(videoCatalogRoute -> videoCatalogRoute.path("/video-catalog/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8101")
                )
                .route(videoStreamRoute -> videoStreamRoute.path("/video-stream/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8102")
                )
                .route(aiRoute -> aiRoute.path("/chat-client/**")
                        .filters(f -> f
                                .prefixPath("/ai")
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8200")
                )
                .build();
    }

}
