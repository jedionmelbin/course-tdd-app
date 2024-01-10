package com.xprotec.app.service;

import com.xprotec.app.domain.Product;
import com.xprotec.app.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultProductService implements ProductService {

    // MockMVC Execute HTTP Request, Validate HTTP Headers, Validate HTTP Body, Validate HTTP Response
    /*private static final Logger LOGGER = LoggerFactory.getLogger(DefaultProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Integer id) {
        LOGGER.info("FINDING product by id: {}", id);
        return productRepository.findProductById(id);
    }

    @Override
    public Product save(Product product) {
        LOGGER.info("Saving new product with name");
        product.setVersion(1);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        LOGGER.info("Updating product with id {} ", product.getId());
        Product exitProduct = productRepository.findProductById(product.getId());
        if (exitProduct != null) {
            exitProduct.setName(product.getName());
            exitProduct.setDescription(product.getDescription());
            exitProduct.setQuantity(product.getQuantity());
            exitProduct = productRepository.save(exitProduct);
        } else {
            LOGGER.error("PRODUCT with id {}", product.getId());
        }
        return exitProduct;
    }

    @Override
    public void delete(Product product) {
        LOGGER.info("Deleting product with id {} ", product.getId());
        Product exitProduct = productRepository.findProductById(product.getId());
        if (exitProduct != null) {
            productRepository.delete(exitProduct);
        }
    }*/

    @Override
    public Iterable<Product> findAll() {
        return null;
    }

    @Override
    public Product findById(Integer id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public Product update(Product existProduct) {
        return null;
    }

    @Override
    public void delete(Product product) {

    }
}
