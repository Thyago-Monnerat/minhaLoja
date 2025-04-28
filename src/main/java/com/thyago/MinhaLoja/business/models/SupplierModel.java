package com.thyago.MinhaLoja.business.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_suppliers")
public class SupplierModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private Set<ProductModel> products = new HashSet<>();
}
