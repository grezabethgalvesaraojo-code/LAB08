package com.ARAOJO.LAB07.controller;

import com.ARAOJO.LAB07.model.Product;
import com.ARAOJO.LAB07.service.ProductService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

/**
 * GraphQL Resolver for Product entity.
 * This controller maps the GraphQL queries and mutations defined in schema.graphqls
 * to the business logic implemented in ProductService.
 */
@Controller // Use @Controller for Spring GraphQL resolvers
public class ProductGraphQLController {

    private final ProductService productService;

    // Constructor Injection
    public ProductGraphQLController(ProductService productService) {
        this.productService = productService;
    }

    // --- QUERIES (Read Operations) ---

    // Maps to 'Query.findAllProducts' in the schema
    // The method name should match the field name in the schema for easy mapping,
    // or you can explicitly name it: @QueryMapping("findAllProducts")
    @QueryMapping
    public List<Product> findAllProducts() { // RENAMED: was 'products'
        // Delegates to the existing service method. productService.findAll() is guaranteed to return a List (not null).
        return productService.findAll();
    }

    // Maps to 'Query.productById(id: ID!)' in the schema
    @QueryMapping("productById") // Explicitly mapping since method name differs from field
    public Product product(@Argument Long id) { // RENAMED: was 'product'
        // Delegates to the existing service method and returns null if not found
        // Returning null for a nullable field (Product) is fine.
        return productService.findById(id).orElse(null);
    }

    // --- MUTATIONS (Write Operations) ---

    // Maps to 'Mutation.createProduct(product: ProductInput!)' in the schema
    @MutationMapping
    public Product createProduct(@Argument Product product) {
        return productService.createProduct(product);
    }

    // Maps to 'Mutation.updateProduct(id: ID!, product: ProductInput!)' in the schema
    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument Product product) {
        return productService.updateProduct(id, product).orElse(null);
    }

    // Maps to 'Mutation.deleteProduct(id: ID!)' in the schema
    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }
}