package com.xprotec.app.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "persons")
public class Person {

    @Id
    @Column
    private Integer id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;
}
