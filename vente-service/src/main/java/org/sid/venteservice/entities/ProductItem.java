package org.sid.venteservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.sid.venteservice.model.Product;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class ProductItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Vente vente;
    private Integer quantity;
    private Double price;
    private Double discount;
    @Transient
    private Product product;
}
