package com.novatech.cybertech.dto.response;

import com.novatech.cybertech.entities.enums.Role;
import com.novatech.cybertech.entities.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
// Importe d'autres DTOs si nécessaire (par exemple, OrderSummaryDto)

@Data // Génère getters, setters, toString, equals, hashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private UUID uuid;
    private String email;
    private String firstName;
    private String lastName;
    private Sex sex;
    private String address;
    private Date birthDate;
    private Role role;

// Remarque : Le mot de passe est intentionnellement omis.
// Remarque : La liste des commandes (orders) est omise ici.
// Tu pourrais avoir un endpoint séparé /users/{userld]/orders
// ou inclure une liste de DTOs simplifiés (ex: List«OrderSummaryDto>) si nécessaire.
}