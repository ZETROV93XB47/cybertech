package com.novatech.cybertech.entities;

import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.entities.enums.Sex;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = "userTable")
@RequiredArgsConstructor
@ToString(callSuper = true)
public class UserEntity extends BaseEntity {

    @Column(name = "email", nullable = false, columnDefinition = "char(50)")
    private String email;

    @Column(name = "firstName", columnDefinition = "char(15)")
    private String firstName;

    @Column(name = "lastName", columnDefinition = "char(15)")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "address", columnDefinition = "char(50)")
    private String address;

    @Column(name = "birthDate", columnDefinition = "DATE")
    private Date birthDate;

    @Column(name = "password", columnDefinition = "char(36)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;


    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<CartEntity> cartEntities;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<OrderEntity> orderEntities;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviewEntities;
}
