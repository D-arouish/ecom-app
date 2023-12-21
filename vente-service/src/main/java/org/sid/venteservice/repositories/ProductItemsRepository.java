package org.sid.venteservice.repositories;

import org.sid.venteservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ProductItemsRepository extends JpaRepository<ProductItem, Long> {
}
