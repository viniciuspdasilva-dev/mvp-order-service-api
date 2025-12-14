package io.git.mvp.mvp_order_service_api.service;


import io.git.mvp.mvp_order_service_api.client.FakeStoreClient;
import io.git.mvp.mvp_order_service_api.model.Product;
import io.git.mvp.mvp_order_service_api.model.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FakeStoreClient fakeStoreClient;

    public ProductService(ProductRepository productRepository, FakeStoreClient fakeStoreClient) {
        this.productRepository = productRepository;
        this.fakeStoreClient = fakeStoreClient;
    }

    @PostConstruct
    public void initProducts() throws BadRequestException {
        if (productRepository.count() == 0) {
            List<Product> allProducts = fakeStoreClient.getAllProducts();
            this.saveAll(allProducts);
        } else {
            log.info("Produtos já existentes no banco de dados");
        }
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id){
        return productRepository.findById(id);
    }


    private void saveAll(List<Product> products) {
        if (products.isEmpty()) {
            log.info("Nenhum produto listado para ser salvo no estoque");
            return;
        }
        productRepository.saveAll(products);
    }


}
