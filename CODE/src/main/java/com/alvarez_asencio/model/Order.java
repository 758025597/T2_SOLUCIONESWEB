package com.alvarez_asencio.model;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders") // "order" es palabra reservada en SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrder;

    // obligatorio, unico
    @Column(nullable = false, unique = true, length = 50)
    private String orderNumber;

    // obligatorio
    @Column(nullable = false, length = 100)
    private String customerName;

    // se asigna automaticamente al insertar y no cambia al actualizar
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    // mayor que 0
    @Column(nullable = false, precision = 10, scale = 2,
            check = @CheckConstraint(name = "ck_orders_total", constraint = "total > 0"))
    private BigDecimal total;

    // PENDIENTE, ENVIADO, ENTREGADO, CANCELADO (se guarda como texto)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.PENDIENTE;

    // estados: pendiente, enviado, entregado y cancelado
    public enum OrderStatus {
        PENDIENTE,
        ENVIADO,
        ENTREGADO,
        CANCELADO
    }
}
