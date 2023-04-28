package com.xprotec.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xprotec.app.domain.Product;
import com.xprotec.app.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test product found - GET  /products/1")
    public void testGetProductByIdFindsProduct() throws Exception {

        //Prepare mock product
        Product mockProduct = new Product(1, "New product", "Details products", 5, 1);

        //Prepare mocked service method
        doReturn(mockProduct).when(productService).findById(mockProduct.getId());

        //Perform GET Request

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",1))
                //Validate 200 ok
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate response
                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/products/1"))

                // Validate response body
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New product"))
                .andExpect(jsonPath("$.description").value("Details products"))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.version").value(1));
    }


    @Test
    @DisplayName("Test all products found -GET /products")
    void testGetAllProductsFound() throws Exception {
        //Prepare mock product
        Product fistProduct = new Product(1, "First Product", "Details Products", 5, 1);
        Product secondProduct = new Product(2, "Second Product", "Second Products", 5, 1);

        List<Product> products = new ArrayList<>();
        products.add(fistProduct);
        products.add(secondProduct);

        doReturn(products).when(productService).findAll();

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                // Validate 200 Ok and JSON response type received
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                //Validate response body
                .andExpect(jsonPath("$[0].name").value("First Product"))
                .andExpect(jsonPath("[1].name").value("Second Product"));
    }


    @Test
    @DisplayName("Add a new product - POST /products")
    public void testAddNewProduct() throws Exception {
        //Prepare mock product
        Product newProduct = new Product(1, "New Product", "Details Products", 5, 1);
        Product mockProduct = new Product(1, "New Product", "Details Products", 5, 1);

        doReturn(mockProduct).when(productService).save(ArgumentMatchers.any());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(newProduct))
                )

                // Validate 201 CREATED and JSON response type received
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                //Validate response headers
                .andExpect(header().string(HttpHeaders.ETAG,"\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/products/1"))

                //validate response body
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Product"))
                .andExpect(jsonPath("$.quantity").value(5))
                .andExpect(jsonPath("$.version").value(1));
    }

    @Test
    @DisplayName("Update an existing product with success - PUT /products/1")
    public void testUpdatingProductWithSuccess() throws Exception {
        Product productToUpdate = new Product(1, "New Product", "Details Products", 5, 1);
        Product mockProduct = new Product(1, "New Product", "Details Products", 5, 1);

        doReturn(mockProduct).when(productService).findById(1);
        doReturn(mockProduct).when(productService).update(ArgumentMatchers.any());

        // Testing controller

        //Perform PUT request
        mockMvc.perform(put("/products/{1}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.IF_MATCH,1)
                        .content(new ObjectMapper().writeValueAsString(productToUpdate)))

                        //Validate 200 ok and JSON response type received
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))

                // Validate response headers
                .andExpect(header().string(HttpHeaders.ETAG,"\"2\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/products/1"))

                //Validate response body
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New name"))
                .andExpect(jsonPath("$.quantity").value(20))
                .andExpect(jsonPath("$.version").value(1));

    }

    @Test
    @DisplayName("Version mismatch while updating existing -PUT /products/1")
    public void testVersionMismatchWhileUpdating() throws Exception {
        Product productToUpdate = new Product(1, "New Product", "Details Products", 5, 1);
        Product mockProduct = new Product(1, "New Product", "Details Products", 5, 1);

        //Prepare mock service method
        doReturn(mockProduct).when(productService).findById(1);

        //Perform PUT request
        mockMvc.perform(put("/products/{id}",1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.IF_MATCH,1)
                .content(new ObjectMapper().writeValueAsString(productToUpdate)))

                //Validate 409 CONFLIC receieved
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Product not found while updating existing -PUT /products/1")
    public void testProductNoFoundWhileUpdating() throws Exception {
        Product productToUpdate = new Product(1, "New Product", "Details Products", 5, 1);
       // Product mockProduct = new Product(1, "New Product", "Details Products", 5, 1);

        //Prepare mock service method
        doReturn(null).when(productService).findById(1);

        //Perform PUT request
        mockMvc.perform(put("/products/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(HttpHeaders.IF_MATCH,1)
                        .content(new ObjectMapper().writeValueAsString(productToUpdate)))

                //Validate 409 CONFLIC receieved
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Delete a product successfully -DELETE /products/1")
    public void testProductDeletedSuccessfully() throws Exception {
        //Prepare mock
        Product existProduct = new Product(1, "New Product", "Details Products", 5, 1);

        //Prepare mock service method
        doReturn(existProduct).when(productService).findById(1);

        //Perform DELETE Request
        mockMvc.perform(delete("/products/{id}",1)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete a product successfully -DELETE /products/1")
    public void testFailureDeleteNotExit() throws Exception {

        //Prepare mock service method
        doReturn(null).when(productService).findById(1);

        //Perform DELETE Request
        mockMvc.perform(delete("/products/{id}",1)).andExpect(status().isNotFound());
    }
}