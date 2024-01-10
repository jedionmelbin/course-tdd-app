package com.xprotec.app.service;

import com.xprotec.app.domain.Product;
import com.xprotec.app.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AssertionErrors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DefaultProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("Find product with id successfully")
    public void testFindProductById() {
        Product mockProduct = new Product(1, "Product", "Description", 10, 1);

        doReturn(mockProduct).when(productRepository).findProductById(1);

        Product foundProduct = productService.findById(1);

        Assertions.assertNotNull(foundProduct);
        Assertions.assertSame("Product", foundProduct.getName());
    }

    @Test
    @DisplayName("Fail to find product with id")
    public void testFailToFindProductById() {

        doReturn(null).when(productRepository).findProductById(1);

        Product foundProduct = productService.findById(1);

        Assertions.assertNull(foundProduct);
    }

    @Test
    @DisplayName("Find all products")
    public void testFindAllProducts() {
        Product firstProduct = new Product(1, "Product 1", "Description", 10, 1);
        Product secondProduct = new Product(2, "Product 2", "Description", 10, 1);

        doReturn(Arrays.asList(firstProduct, secondProduct)).when(productRepository).findAll();

        Iterable<Product> allProduct = productService.findAll();

        Assertions.assertEquals(2, ((Collection<?>) allProduct).size());

    }


    @Test
    @DisplayName("Save a new product succesfully")
    public void testSuccessfulProductSave() {
        Product mockProduct = new Product(1, "Product 1", "Description", 10, 1);

        doReturn(mockProduct).when(productRepository).save(any());

        Product savedProduct = productService.save(mockProduct);

        AssertionErrors.assertNotNull("Product should not be null", savedProduct);
        Assertions.assertSame("Product 1", mockProduct.getName());
        Assertions.assertSame(1, savedProduct.getVersion());

    }

    @Test
    @DisplayName("Update an exiting product successfully")
    public void testUpdatingProductSuccessful() {
        Product existingProduct = new Product(1, "Product 1", "Description", 10, 1);
        Product updatedProduct = new Product(1, "Product 2", "Description", 50, 1);

        doReturn(existingProduct).when(productRepository).findProductById(1);
        doReturn(updatedProduct).when(productRepository).save(existingProduct);

        Product updateProduct = productService.update(existingProduct);

        Assertions.assertEquals("Product 2", updatedProduct.getName());

    }

    @Test
    @DisplayName("Update an exiting product successfully")
    public void testFailUpdatingExistingProduct() {
        Product mockProduct = new Product(1, "Product 1", "Description", 10, 1);

        doReturn(null).when(productRepository).findById(1);

        Product updateProduct = productService.update(mockProduct);

        AssertionErrors.assertNull("Product  should be null", updateProduct);

    }

    @Test
    void delete() {
    }
}