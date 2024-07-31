package br.com.stoom.store.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "PRODUCT_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    @Column(name = "price")
    private Double price;

    @Column(name = "enabled")
    private Boolean enabled = true;
}