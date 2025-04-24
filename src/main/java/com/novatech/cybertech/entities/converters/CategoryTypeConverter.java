package com.novatech.cybertech.entities.converters;

import com.novatech.cybertech.entities.enums.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryTypeConverter implements AttributeConverter<Category, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Category category) {
        return category == null ? null : category.getCode();
    }

    @Override
    public Category convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }

        return Category.getByCode(code);
    }
}
