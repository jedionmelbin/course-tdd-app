package com.xprotec.app.service;

import com.xprotec.app.domain.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Iterable<Product> findAll();

    Product findById(Integer id);

    Product save(Product product);

    Product update(Product existProduct);

    void delete(Product product);
}
