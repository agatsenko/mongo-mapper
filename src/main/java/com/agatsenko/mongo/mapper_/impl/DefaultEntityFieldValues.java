package com.agatsenko.mongo.mapper_.impl;

import com.agatsenko.mongo.mapper_.model.EntityFieldValues;
import com.agatsenko.mongo.mapper_.model.EntityMap;
import com.agatsenko.mongo.mapper_.model.FieldMap;

import java.util.HashMap;
import java.util.Map;

public class DefaultEntityFieldValues<TEntity> implements EntityFieldValues<TEntity> {
    private final Map<FieldMap<TEntity, ?, ?>, Object> valueMap;

    public DefaultEntityFieldValues(EntityMap<TEntity> entityMap) {
        this.valueMap = new HashMap<>(entityMap.getFields().size());
    }

    @Override
    public <T> T get(FieldMap<TEntity, T, ?> field) {
        // FIXME: not implemented
        throw new IllegalStateException("not yet implemented");
    }

    public void putValue(FieldMap<TEntity, ?, ?> field, Object value) {
        valueMap.put(field, value);
    }
}
