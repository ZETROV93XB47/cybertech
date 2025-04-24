package com.novatech.cybertech.models;

import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.entities.enums.Sex;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

//@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class User {

    private String email;

    private String firstName;

    private String lastName;

    private Sex sex;

    private String adress;

    private Date birthDate;

    private String password;

    private Role role;

    private List<Order> orders;

}
