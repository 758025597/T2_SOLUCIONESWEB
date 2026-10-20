package com.alvarez_asencio.model;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    // obligatroio unico
    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    // obligatorio
    @Column(nullable = false, length = 100)
    private String name;

    // mayor o igual a 0
    @Column(nullable = false,
            check = @CheckConstraint(name = "ck_products_stock", constraint = "stock >= 0"))
    private Integer stock;

    // mayor que 0
    @Column(nullable = false, precision = 10, scale = 2,
            check = @CheckConstraint(name = "ck_products_price", constraint = "price > 0"))
    private BigDecimal price;

    // por defecto true
    @ColumnDefault("true")
    @Column(nullable = false)
    private Boolean active = true;
}

