package com.agatsenko.mongo.mapper.mapping;

public interface EntityFieldValues<TEntity> {
    <T> T get(FieldMap<TEntity, T, ?> field);
}
