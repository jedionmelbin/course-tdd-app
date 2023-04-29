package com.xprotec.app.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xprotec.app.domain.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.plaf.PanelUI;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private static File DATA_JSON = Paths.get("src", "test", "resources", "products.json").toFile();

    @BeforeEach
    public void setup() throws IOException {
        Product[] products = new ObjectMapper().readValue(DATA_JSON, Product[].class);

        //Save each product to database
        Arrays.stream(products).forEach(productRepository::save);
    }

    @AfterEach
    public void cleanup() {
        // Cleanup the database after each test
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Test product with id retrieved successfully")
    public void testProductWithIdRetrievedSuccessfully() {

        //Given two products in the database

        //WHEN
        Product retrievedProduct = productRepository.findProductById(1);

        //Then
        Assertions.assertNotNull(retrievedProduct, "Product with id 1 should exist");
        Assertions.assertEquals("First Product Description", retrievedProduct.getName());
    }

    @Test
    @DisplayName("Test product not found  exist id")
    public void testProductNoFoundForNoExistingId() {

        //Given two products in the database

        //WHEN
        Product retrievedProduct = productRepository.findProductById(100);

        //Then
        Assertions.assertNull(retrievedProduct, "Product with id 100 should exist");
    }


    @Test
    @DisplayName("Test product save successfully")
    public void testProductSavedSuccessFully() {

        //Prepare mock
        Product newProduct = new Product(1, "Product", "Description", 10, 1);

        //When
        Product saveProduct = productRepository.save(newProduct);

        //Then
        Assertions.assertNotNull(saveProduct, "Product should be saved");
        Assertions.assertNotNull(saveProduct.getId(), "Product should be saved");
        Assertions.assertEquals(saveProduct.getName(), "Product");
    }

    @Test
    @DisplayName("Test product update successfully")
    public void testProductUpdateSuccessFully() {

        //Prepare mock
        Product newProduct = new Product(1, "Product", "Description", 10, 1);

        //When
        Product updateProduct = productRepository.save(newProduct);

        //Then
        Assertions.assertNotNull(updateProduct, "Product should be saved");
        Assertions.assertNotNull(updateProduct.getId(), "Product should be saved");
        Assertions.assertEquals(updateProduct.getName(), "Product");
    }
}