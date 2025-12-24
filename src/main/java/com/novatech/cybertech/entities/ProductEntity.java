package com.novatech.cybertech.entities;

import com.novatech.cybertech.entities.enums.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name = "productTable")
@EqualsAndHashCode(callSuper = true, exclude = {"orderItemEntities", "reviewEntities"})
public class ProductEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand", nullable = false)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "cpu", nullable = false)
    private String cpu;

    @Column(name = "gpu", nullable = false, columnDefinition = "char(50)")
    private String gpu;

    @Enumerated(EnumType.STRING)
    @Column(name = "ram", nullable = false, columnDefinition = "char(50)")
    private Ram ram;

    @Enumerated(EnumType.STRING)
    @Column(name = "ssd", nullable = false, columnDefinition = "char(50)")
    private SSD ssd;

    @Enumerated(EnumType.STRING)
    @Column(name = "displayType")
    private DisplayType displayType;

    @Enumerated(EnumType.STRING)
    @Column(name = "displaySize")
    private DisplaySize displaySize;

    @Enumerated(EnumType.STRING)
    @Column(name = "os", nullable = false, columnDefinition = "char(20)")
    private Os os;

    @Column(name = "connectivity")
    private String connectivity;

    @Column(name = "photo")
    private String photo;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "reservedStock", nullable = false)
    private Integer reservedStock = 0;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @JdbcTypeCode(SqlTypes.JSON) // <--- Indique à Hibernate d'utiliser le type JSON de la BDD
    @Column(columnDefinition = "json") // Spécifique à PostgreSQL (utiliser "json" pour MySQL)
    private Map<String, Object> attributes = new HashMap<>();

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItemEntity> orderItemEntities;

    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReviewEntity> reviewEntities;
}

//@OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//private List<CartItemEntity> cartItemEntities;
