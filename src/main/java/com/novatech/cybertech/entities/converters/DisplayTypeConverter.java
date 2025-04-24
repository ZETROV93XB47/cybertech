package com.novatech.cybertech.entities.converters;

import com.novatech.cybertech.entities.enums.DisplayType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DisplayTypeConverter implements AttributeConverter<DisplayType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DisplayType displayType) {
        return displayType == null ? null : displayType.getCode();
    }

    @Override
    public DisplayType convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return DisplayType.getByCode(code);
    }

}
