package io.git.mvp.mvp_order_service_api.client;

import io.git.mvp.mvp_order_service_api.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    public List<Product> getAllProducts() throws BadRequestException {
        try {
            ResponseEntity<List<Product>> response = restTemplate.exchange(
                    BASE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Product>>() {}
            );
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Erro ao buscar produtos");
                return new ArrayList<>();
            }
            return response.getBody();
        } catch (Exception e) {
            throw new BadRequestException("Erro ao buscar produtos");
        }
    }
}
