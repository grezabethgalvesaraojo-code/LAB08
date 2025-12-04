package com.ARAOJO.LAB07.controller;

import com.ARAOJO.LAB07.model.Product;
import com.ARAOJO.LAB07.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Stereotype Annotation: Registers as a REST controller [cite: 43]
@RequestMapping("/api/products") // Defines the base path [cite: 44]
public class ProductController {

    private final ProductService productService;

    // Constructor Injection [cite: 45]
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 1. READ ALL Products (GET /api/products)
    @GetMapping // Mapping: @GetMapping 
    public ResponseEntity<List<Product>> getAllProducts() {
        // Success: Return list and HTTP status 200 OK [cite: 48, 57]
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    // 2. READ ONE Product (GET /api/products/{id})
    @GetMapping("/{id}") // Mapping: @GetMapping, URL Path: /api/products/{id} 
    public ResponseEntity<Product> getProductById(@PathVariable Long id) { // Use @PathVariable [cite: 59]
        return productService.findById(id)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK)) // Success: 200 OK [cite: 60]
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Error: 404 Not Found [cite: 48, 62]
    }

    // 3. CREATE a new Product (POST /api/products)
    @PostMapping // Mapping: @PostMapping 
    public ResponseEntity<Product> createProduct(@RequestBody Product product) { // Use @RequestBody [cite: 64]
        Product createdProduct = productService.createProduct(product);
        // Success: Return newly created product and HTTP status 201 Created 
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // 4. UPDATE an existing Product (PUT /api/products/{id})
    @PutMapping("/{id}") // Mapping: @PutMapping 
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails)
                .map(updatedProduct -> new ResponseEntity<>(updatedProduct, HttpStatus.OK)) // Success: 200 OK [cite: 48, 70]
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Error: 404 Not Found [cite: 48, 71]
    }

    // 5. DELETE a Product (DELETE /api/products/{id})
    @DeleteMapping("/{id}") // Mapping: @DeleteMapping 
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            // Success: 200 OK (or 204 No Content) [cite: 49]
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            // Error: 404 Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}