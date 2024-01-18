package org.sid.venteservice.repositories;

import org.sid.venteservice.entities.ProductItem;
import org.sid.venteservice.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestController
public interface ProductItemsRepository extends JpaRepository<ProductItem, Long> {
    @Transactional
    void deleteByVente(Vente vente);

}
