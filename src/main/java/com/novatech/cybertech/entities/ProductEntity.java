package com.novatech.cybertech.entities;

import com.novatech.cybertech.entities.enums.Brand;
import com.novatech.cybertech.entities.enums.Category;
import com.novatech.cybertech.entities.enums.DisplayType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@ToString
@EqualsAndHashCode
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", nullable = false)
    private Long productId;

    @Column(name = "productUuid", nullable = false)
    private UUID productUuid;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "stockQuantity", nullable = false)
    private int stockQuantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "cpu", nullable = false)
    private String cpu;

    @Column(name = "gpu", nullable = false, columnDefinition = "char(50)")
    private String gpu;

    @Column(name = "ram", nullable = false, columnDefinition = "char(50)")
    private String ram;

    @Column(name = "hardDrive", nullable = false, columnDefinition = "char(50)")
    private String hardDrive;

    @Column(name = "ssd", nullable = false, columnDefinition = "char(50)")
    private String ssd;

    @Column(name = "displayType")
    private DisplayType displayType;

    @Column(name = "displaySize")
    private int displaySize;

    @Column(name = "os", nullable = false, columnDefinition = "char(20)")
    private String os;

    @Column(name = "connectivity")
    private String connectivity;

    @Column(name = "photo")
    private String photo;

    @Column(name = "stock")
    private int stock;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "productEntityId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItemEntity> orderItemEntities;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReviewEntity> reviewEntities;
}
