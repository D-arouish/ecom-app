package org.sid.venteservice.service;


import org.sid.venteservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductRestClient {
    @GetMapping(path = "/products/{id}")
    Product findProductById(@PathVariable Long id);

    @GetMapping("/products")
    PagedModel<Product> allProducts();
}
