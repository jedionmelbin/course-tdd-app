package com.xprotec.app.controller;

import com.xprotec.app.domain.Product;
import com.xprotec.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;


    @GetMapping()
    public Iterable<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id) {
        Product product = productService.findById(id);
        if (product != null) {
            try {
                return ResponseEntity.ok()
                        .eTag(Integer.toString(product.getId()))
                        .location(new URI("/products/" + product.getId()))
                        .body(product);

            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody Product product) {
        LOGGER.info("Adding new a product {}", product.getName());
        Product newProduct = productService.save(product);
        try {
            return ResponseEntity.created(new URI("/products/" + newProduct.getId()))
                    .eTag(Integer.toString(newProduct.getId()))
                    .body(newProduct);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id,
                                           @RequestBody Product product,
                                           @RequestHeader("If-Match") Integer ifMatch) {
        LOGGER.info("Updating new a product {}", product.getName());
        Product existProduct = productService.findById(id);
        if (existProduct == null) {
            return ResponseEntity.notFound().build();
        } else {
            if (!existProduct.getVersion().equals(ifMatch)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                LOGGER.info("Updating producto with name {} ", product.getName());

                existProduct.setName(product.getName());
                existProduct.setDescription(product.getDescription());
                try {
                    existProduct = productService.update(existProduct);
                    return ResponseEntity.ok()
                            .eTag(Integer.toString(existProduct.getVersion()))
                            .location(new URI("/products/" + existProduct.getId()))
                            .body(existProduct);
                } catch (URISyntaxException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        LOGGER.info("Deletign product with id");
        Product product = productService.findById(id);
        if (product != null) {
            productService.delete(product);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
