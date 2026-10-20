package com.alvarez_asencio.dto;

import com.alvarez_asencio.model.Order.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Integer idOrder;

    @NotBlank(message = "El número de pedido es obligatorio")
    @Size(max = 50, message = "El número de pedido no debe superar los 50 caracteres")
    private String orderNumber;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 100, message = "El nombre del cliente no debe superar los 100 caracteres")
    private String customerName;

    // Lo asigna el sistema; si el cliente lo envía se ignora
    private LocalDateTime orderDate;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor que 0")
    private BigDecimal total;

    // Si el cliente no lo envía, queda en PENDIENTE
    private OrderStatus status = OrderStatus.PENDIENTE;
}
