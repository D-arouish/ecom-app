package org.sid.productservice.repositories;

import org.sid.productservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ProductRepository extends JpaRepository<Product,Long> {
}
