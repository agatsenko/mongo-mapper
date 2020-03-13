package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.model.EntityFieldValues;
import com.agatsenko.mongo.mapper.model.EntityMap;
import com.agatsenko.mongo.mapper.model.FieldMap;
import com.agatsenko.mongo.mapper.util.Check;

import java.util.HashMap;
import java.util.Map;

public final class DefaultEntityFieldValues<TEntity> implements EntityFieldValues<TEntity> {
    private final Map<FieldMap<TEntity, ?, ?>, Object> valueMap;

    public DefaultEntityFieldValues(EntityMap<TEntity> entityMap) {
        this.valueMap = new HashMap<>(entityMap.getFields().size());
    }

    @Override
    public <T> T get(FieldMap<TEntity, T, ?> field) {
        Check.argNotNull(field, "field");
        return field.getFieldType().cast(valueMap.get(field));
    }

    public void putValue(FieldMap<TEntity, ?, ?> field, Object value) {
        valueMap.put(field, value);
    }
}
