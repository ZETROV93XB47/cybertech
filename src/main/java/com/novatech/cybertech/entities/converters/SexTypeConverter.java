package com.novatech.cybertech.entities.converters;

import com.novatech.cybertech.entities.enums.Sex;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SexTypeConverter implements AttributeConverter<Sex, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Sex sex) {
        return sex == null ? null : sex.getCode();
    }

    @Override
    public Sex convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Sex.getByCode(code);
    }

}
