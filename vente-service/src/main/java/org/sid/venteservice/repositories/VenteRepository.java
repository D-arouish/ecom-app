package org.sid.venteservice.repositories;

import feign.Param;
import org.sid.venteservice.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface VenteRepository extends JpaRepository<Vente, Long> {
    @Query("SELECT v FROM Vente v WHERE v.customerId = :customerId")
    List<Vente> findByCustomerId(@Param("customerId") Long customerId);


}
