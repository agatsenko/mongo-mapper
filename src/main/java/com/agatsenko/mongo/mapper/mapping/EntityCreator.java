package com.agatsenko.mongo.mapper.mapping;

@FunctionalInterface
public interface EntityCreator<TEntity> {
    TEntity create(EntityFieldValues<TEntity> fieldValues, String discriminator);
}
