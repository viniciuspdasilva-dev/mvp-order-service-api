package io.git.mvp.mvp_order_service_api.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "https://fakestoreapi.com/products";

    public boolean productExists(Long productId) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/" + productId, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
