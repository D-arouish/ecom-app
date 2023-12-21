package org.sid.venteservice.repositories;

import org.sid.venteservice.entities.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface VenteRepository extends JpaRepository<Vente, Long> {
}
