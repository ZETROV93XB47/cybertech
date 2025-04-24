package com.novatech.cybertech.entities.converters;

import com.novatech.cybertech.entities.enums.Role;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class RoleTypeConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role == null ? null : role.getCode();
    }

    @Override
    public Role convertToEntityAttribute(Integer role) {
        if (role == null) {
            return null;
        }

        return Role.getByCode(role);
    }
}
