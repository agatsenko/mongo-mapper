package com.agatsenko.mongo.mapper.model;

@FunctionalInterface
public interface EntityCreator<TEntity> {
    TEntity create(EntityFieldValues<TEntity> fieldValues, String discriminator);
}
