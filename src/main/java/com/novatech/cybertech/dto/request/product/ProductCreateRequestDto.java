package com.novatech.cybertech.dto.request.product;

import com.novatech.cybertech.entities.enums.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDto {

    @NotNull(message = "Product name cannot be blank")
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Brand cannot be null")
    private Brand brand;

    @NotNull(message = "Category cannot be null")
    private Category category;

    @NotBlank(message = "CPU information cannot be blank")
    private String cpu;

    @NotNull(message = "GPU information cannot be blank")
    @Size(max = 50, message = "GPU information must be at most 50 characters")
    private String gpu;

    @NotNull(message = "RAM cannot be null")
    private Ram ram;

    @NotNull(message = "SSD cannot be null")
    private SSD ssd;

    @NotNull(message = "SSD cannot be null")
    private DisplayType displayType;

    @NotNull(message = "SSD cannot be null")
    private DisplaySize displaySize;

    @NotNull(message = "Operating System cannot be null")
    private Os os;

    @NotNull(message = "Connectivity cannot be null")
    @Size(max = 255, message = "Connectivity details must be at most 255 characters")
    private String connectivity;

    @NotNull(message = "Connectivity cannot be null")
    @Size(max = 255, message = "Photo URL/path must be at most 255 characters")
    private String photo;

    @Min(value = 0, message = "Stock cannot be negative")
    private int stock = 0; // Valeur par défaut si non fournie

    @NotNull(message = "description cannot be null")
    private String description;
}