package com.alvarez_asencio.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer idProduct;

    @NotBlank(message = "El sku es obligatorio")
    @Size(max = 50, message = "El sku no debe superar los 50 caracteres")
    private String sku;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String name;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    // Si el cliente no lo envía, queda en true
    private Boolean active = true;
}
