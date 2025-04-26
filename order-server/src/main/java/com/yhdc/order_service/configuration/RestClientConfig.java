package com.yhdc.order_service.configuration;

import com.yhdc.order_service.transaction.InventoryRestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    public InventoryRestClient inventoryRestClient() {
        final String inventoryUrl = "http://localhost:8085/inventory";

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(30000);
        requestFactory.setReadTimeout(30000);

        RestClient inventoryClient = RestClient.builder()
                .requestFactory(requestFactory)
                .baseUrl(inventoryUrl).build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(inventoryClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryRestClient.class);
    }

}
