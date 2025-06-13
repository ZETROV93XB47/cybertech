package com.novatech.cybertech.dto.request.user;

import com.novatech.cybertech.entities.enums.Sex;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotNull(message = "Sex cannot be null")
    private Sex sex;

    // L'adresse peut être optionnelle selon tes besoins
    private String address;

    // La date de naissance peut être optionnelle
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
// Tu peux ajouter une @Pattern pour la complexité du mot de passe si nécessaire
    private String password;

// Le rôle est souvent défini par défaut ou par un admin,
// donc il n'est pas toujours inclus dans la requête de création standard.
// Si l'utilisateur peut choisir son rôle à la création, ajoute-le ici.
// private Role role;

}