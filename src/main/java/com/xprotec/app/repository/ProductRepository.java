package com.xprotec.app.repository;


import com.xprotec.app.domain.Product;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository

        //extends CrudRepository<Product, Integer>
{
    Product findProductById(Integer id);
}
