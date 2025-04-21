package com.yhdc.order_server.configuration;

import com.yhdc.order_server.transaction.InventoryRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public InventoryRestClient inventoryRestClient() {
        final String inventoryUrl = "http://localhost:8085/inventory";

        RestClient inventoryClient = RestClient.builder()
                .baseUrl(inventoryUrl).build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(inventoryClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryRestClient.class);
    }

}
