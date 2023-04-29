package com.xprotec.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    private Integer id;
    private String name;
    private String description;
    //private Float price;
    private Integer quantity;
    private Integer version;
}
