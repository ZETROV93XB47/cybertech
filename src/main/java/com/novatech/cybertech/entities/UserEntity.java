package com.novatech.cybertech.entities;

import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.entities.enums.Sex;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "email", nullable = false, columnDefinition = "char(50)")
    private String email;

    @Column(name = "firstName", columnDefinition = "char(15)")
    private String firstName;

    @Column(name = "lastName", columnDefinition = "char(15)")
    private String lastName;

    @Column(name = "sex", nullable = false)
    private Sex sex;

    @Column(name = "adress", columnDefinition = "char(50)")
    private String adress;

    @Column(name = "birthDate", columnDefinition = "DATE")
    private Date birthDate;

    @Column(name = "password", columnDefinition = "char(36)")
    private String password;

    @Column(name = "role", nullable = false)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userEntity", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderEntity> orderEntities;

}
