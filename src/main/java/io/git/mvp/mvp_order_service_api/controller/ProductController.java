package io.git.mvp.mvp_order_service_api.controller;

import io.git.mvp.mvp_order_service_api.exceptions.ProductNotFound;
import io.git.mvp.mvp_order_service_api.model.Product;
import io.git.mvp.mvp_order_service_api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Managing products available for purchase.")
public class ProductController {

    private final ProductService service;


    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "View a list of available products", description = "Returns a list of all products")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of products available for purchase.",
                            content = @io.swagger.v3.oas.annotations.media.Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Product.class))
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
            }
    )
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @Operation(summary = "Get a product by their ID", description = "Returns a specific product from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found by ID",
                    content = @Content(schema = @Schema(implementation = ProductNotFound.class))), // Empty content for 404
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return service.getProductById(id)
                .orElseThrow(() -> new ProductNotFound("Product not found with id: " + id));
    }
}
