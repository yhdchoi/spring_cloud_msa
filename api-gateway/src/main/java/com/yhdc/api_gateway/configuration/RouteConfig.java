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
                .route("account_route", accoutnRoute -> accoutnRoute
                        .path("/account/**")
                        .filters(f -> f
                                .circuitBreaker(breaker -> breaker
                                        .setName("account_breaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8081")

                )
                .route("account_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/aggregate/account-service/v3/api-docs")
                        .filters(f -> f
                                .circuitBreaker(breaker -> breaker
                                        .setName("account_swagger_breaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                        )
                        .uri("http://localhost:8081")
                )

                // Store Route
                .route("store_route", storeRoute -> storeRoute
                        .path("/store/**")
                        .filters(f -> f
                                .circuitBreaker(breaker -> breaker
                                        .setName("account_breaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8082")

                )
                .route("store_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/aggregate/store-service/v3/api-docs")
                        .filters(f -> f
                                .circuitBreaker(breaker -> breaker
                                        .setName("store_swagger_breaker")
                                        .setFallbackUri("forward:/fallback")
                                )
                        )
                        .uri("http://localhost:8082")
                )

                // Product Route
                .route("product_route", productRoute -> productRoute
                        .path("/product/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8083")

                )

                // Inventory Route
                .route("inventory_route", inventoryRoute -> inventoryRoute
                        .path("/inventory/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8084")

                )

                // Order Route
                .route("order_route", orderRoute -> orderRoute
                        .path("/order/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8085")

                )

                // Notification Route
                .route("notification_route", notificationRoute -> notificationRoute
                        .path("/notification/**")
                        .filters(f -> f
                                .prefixPath("/event")
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8086")

                )

                // Image Route
                .route("image_route", imageRoute -> imageRoute
                        .path("/image/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8100")
                )

                // Video Route
                .route("video_catalog_route", videoCatalogRoute -> videoCatalogRoute
                        .path("/video-catalog/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8101")
                )
                .route("video_streaming_route", videoStreamRoute -> videoStreamRoute
                        .path("/video-stream/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8102")
                )

                // Ai Route
                .route("ai_route", aiRoute -> aiRoute
                        .path("/chat-client/**")
                        .filters(f -> f
                                .prefixPath("/ai")
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("http://localhost:8200")
                )

                // Fallback Route
                .route("fallback_route", fallbackRoute -> fallbackRoute
                        .path("/fallback")
                        .uri("http://localhost:8080")
                )
                .build();
    }

}
