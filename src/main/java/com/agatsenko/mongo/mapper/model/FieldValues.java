/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper.model;

import java.util.Map;

import com.agatsenko.mongo.mapper.util.Check;
import lombok.NonNull;
import lombok.Value;

@Value
public final class FieldValues<TEntity> {
    private final Map<FieldMap<TEntity, ?, ?>, Object> values;

    FieldValues(Map<@NonNull FieldMap<TEntity, ?, ?>, Object> values) {
        this.values = values;
    }

    public <TField> TField get(FieldMap<TEntity, TField, ?> fieldMap) {
        final var valueObj = values.get(fieldMap);
        return valueObj == null ? null : fieldMap.getFieldType().cast(valueObj);
    }
}
