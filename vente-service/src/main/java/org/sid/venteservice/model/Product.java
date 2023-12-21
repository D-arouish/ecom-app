package org.sid.venteservice.model;

import lombok.Data;

@Data
public class Product {
    private  Long id;
    private  String name;
    private Double price;
    private Integer quantity;
}
