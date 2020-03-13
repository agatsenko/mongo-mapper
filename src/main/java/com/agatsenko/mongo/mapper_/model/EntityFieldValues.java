package com.agatsenko.mongo.mapper_.model;

public interface EntityFieldValues<TEntity> {
    <T> T get(FieldMap<TEntity, T, ?> field);
}
