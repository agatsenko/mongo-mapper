/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper;

import java.util.Map;

import lombok.NonNull;
import lombok.Value;

@Value
public final class FieldValues<TEntity> {
    private final Map<FieldMap<TEntity, ?, ?>, Object> values;

    FieldValues(Map<@NonNull FieldMap<TEntity, ?, ?>, Object> values) {
        this.values = values;
    }

    public <T> T get(String field) {
        // FIXME: not yet implemented
        throw new IllegalStateException("not yet implemented");
    }

    public <TField> TField get(FieldMap<TEntity, TField, ?> fieldMap) {
        // FIXME: not yet implemented
        throw new IllegalStateException("not yet implemented");
    }
}
