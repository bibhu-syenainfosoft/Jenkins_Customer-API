package com.bibhu.Customer_API.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long categoryId;
    private String categoryName;
    private String categoryIcon;

    @OneToMany(mappedBy = "categories")
    private Set<Products> products = new HashSet<>();
}
