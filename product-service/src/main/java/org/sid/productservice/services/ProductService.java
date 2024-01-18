package org.sid.productservice.services;

import org.sid.productservice.entities.Product;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> findAll();

    void delete(Long id);

    Product findOne(Long id);

    boolean isExists(Long id);

    Product partialUpdate(Long id, Product product);
}
