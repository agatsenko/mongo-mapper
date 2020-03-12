/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper;

@FunctionalInterface
public interface EntityCreator<T> {
    T create(FieldValues values);
}
