package com.agatsenko.mongo.mapper.model;

public interface EntityFieldValues<TEntity> {
    <T> T get(FieldMap<TEntity, T, ?> field);
}
