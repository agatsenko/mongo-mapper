package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.model.FieldMap;
import com.agatsenko.mongo.mapper.model.FieldValueMap;

import org.bson.BsonReader;

public interface FieldReader<TEntity, TField, TDocValue> {
    FieldMap<TEntity, TField, TDocValue> getFieldMap();

    TField read(BsonReader reader, ReadContext<TEntity> context);

    static <TEntity, TField, TDocValue> FieldReader<TEntity, TField, TDocValue> create(
            FieldMap<TEntity, TField, TDocValue> fieldMap) {
        if (fieldMap instanceof FieldValueMap) {
            return new FieldValueReader<>((FieldValueMap<TEntity, TField, TDocValue>) fieldMap);
        }
        else {
            throw new IllegalStateException(String.format("%s field map is not supported", fieldMap));
        }
    }
}
