package com.thyago.MinhaLoja.business.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_suppliers")
@Getter
@Setter
public class SupplierModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<ProductModel> products = new HashSet<>();
}
