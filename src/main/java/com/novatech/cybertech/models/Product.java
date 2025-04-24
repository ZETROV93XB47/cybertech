package com.novatech.cybertech.models;

import com.novatech.cybertech.entities.enums.Brand;
import com.novatech.cybertech.entities.enums.Category;
import com.novatech.cybertech.entities.enums.DisplayType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


//@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Product {

    private UUID productUuid;

    private String name;

    private int stockQuantity;

    private BigDecimal price;

    private Brand brand;

    private Category category;

    private String cpu;

    private String gpu;

    private String ram;

    private String hardDrive;

    private String ssd;

    private DisplayType displayType;

    private int displaySize;

    private String os;

    private String connectivity;

    private String photo;

    private int stock;

    private String description;

    private List<OrderItem> orderItems;

    private List<Review> reviews;
}
