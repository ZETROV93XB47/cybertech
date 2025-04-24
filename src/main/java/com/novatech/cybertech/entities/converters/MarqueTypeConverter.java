package com.novatech.cybertech.entities.converters;

import com.novatech.cybertech.entities.enums.Brand;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MarqueTypeConverter implements AttributeConverter<Brand, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Brand brand) {
        return brand == null ? null : brand.getCode();
    }

    @Override
    public Brand convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Brand.getByCode(code);
    }

}
