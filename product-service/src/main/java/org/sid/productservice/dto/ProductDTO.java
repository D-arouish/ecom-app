package org.sid.productservice.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
}
