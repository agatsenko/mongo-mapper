/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper.model;

@FunctionalInterface
public interface EntityCreator<T> {
    T create(FieldValues<T> values);
}
