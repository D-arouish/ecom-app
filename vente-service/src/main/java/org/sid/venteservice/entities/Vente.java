package org.sid.venteservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.sid.venteservice.model.Customer;

import java.util.Date;
import java.util.List;


@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
@Entity
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date VenteDate;
    private Long customerId;
    @OneToMany(mappedBy = "vente")
    private List<ProductItem> productItems;

    @Transient
    private Customer customer;

}
