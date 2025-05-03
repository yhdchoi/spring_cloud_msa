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
                        .uri("lb://ACCOUNT-SERVICE")

                )
                .route("account_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/account/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/account/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://ACCOUNT-SERVICE")
                )

                // Store Route
                .route("store_route", storeRoute -> storeRoute
                        .path("/store/**")
                        .uri("lb://STORE-SERVICE")

                )
                .route("store_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/store/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/store/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://STORE-SERVICE")
                )

                // Product Route
                .route("product_route", productRoute -> productRoute
                        .path("/product/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route("product_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/product/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/product/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://PRODUCT-SERVICE")
                )

                // Inventory Route
                .route("inventory_route", inventoryRoute -> inventoryRoute
                        .path("/inventory/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("lb://INVENTORY-SERVICE")

                )
                .route("inventory_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/inventory/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/inventory/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://INVENTORY-SERVICE")
                )

                // Order Route
                .route("order_route", orderRoute -> orderRoute
                        .path("/order/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("lb://ORDER-SERVICE")

                )
                .route("order_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/order/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/order/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://ORDER-SERVICE")
                )

                // Notification Route
                .route("notification_route", notificationRoute -> notificationRoute
                        .path("/notification/**")
                        .filters(f -> f
                                .prefixPath("/event")
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("lb://NOTIFICATION-SERVICE")

                )
                .route("notification_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/notification/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/notification/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://NOTIFICATION-SERVICE")
                )

                // Image Route
                .route("image_route", imageRoute -> imageRoute
                        .path("/image/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("lb://IMAGE-SERVICE")
                )
                .route("image_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/image/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/image/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://IMAGE-SERVICE")
                )

                // Video Route
                .route("video_catalog_route", videoCatalogRoute -> videoCatalogRoute
                        .path("/video-catalog/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("lb://VIDEO-CATALOG-SERVICE")
                )
                .route("video_catalog_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/video-catalog/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/video-catalog/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://VIDEO-CATALOG-SERVICE")
                )

                .route("video_streaming_route", videoStreamRoute -> videoStreamRoute
                        .path("/video-stream/**")
                        .filters(f -> f
                                .addResponseHeader("X-Powered-By", "MSC Gateway Service")
                        )
                        .uri("lb://VIDEO-STREAM-SERVICE")
                )
                .route("video_stream_swagger_route", accountSwaggerRoute -> accountSwaggerRoute
                        .path("/video_stream/api-docs/**")
                        .filters(f -> f
                                .rewritePath("/video_stream/(?<path>.*)", "/${path}")
                        )
                        .uri("lb://VIDEO-STREAM-SERVICE")
                )

                // Fallback Route
//                .route("fallback_route", fallbackRoute -> fallbackRoute
//                        .path("/fallback")
//                        .uri("http://localhost:8080")
//                )
                .build();
    }

}
