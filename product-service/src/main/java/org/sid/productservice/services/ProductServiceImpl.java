package org.sid.productservice.services;

import org.sid.productservice.entities.Product;
import org.sid.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return StreamSupport.stream(productRepository
                .findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findOne(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public boolean isExists(Long id) {
        return productRepository.existsById(id);
    }

    @Override
    public Product partialUpdate(Long id, Product product) {
        product.setId(id);
        return productRepository.findById(id).map(existingProduct ->
        {
            Optional.ofNullable(product.getName()).ifPresent(existingProduct::setName);
            Optional.ofNullable(product.getPrice()).ifPresent(existingProduct::setPrice);
            Optional.ofNullable(product.getQuantity()).ifPresent(existingProduct::setQuantity);
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new RuntimeException("Product does not exist !"));
    }
}
