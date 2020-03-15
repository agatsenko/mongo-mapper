package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.mapping.FieldCollectionMap;
import com.agatsenko.mongo.mapper.mapping.FieldMap;
import com.agatsenko.mongo.mapper.mapping.FieldValueMap;

import org.bson.BsonReader;
import org.bson.codecs.configuration.CodecRegistry;

public interface FieldReader<TEntity, TField, TDocValue> {
    FieldMap<TEntity, TField, TDocValue> getFieldMap();

    TField read(BsonReader reader, ReadContext<TEntity> context);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    static <TEntity, TField, TDocValue> FieldReader<TEntity, TField, TDocValue> create(
            FieldMap<TEntity, TField, TDocValue> fieldMap,
            CodecRegistry codecRegistry) {
        if (fieldMap instanceof FieldValueMap) {
            return new FieldValueReader<>((FieldValueMap<TEntity, TField, TDocValue>) fieldMap, codecRegistry);
        }
        else if (fieldMap instanceof FieldCollectionMap) {
            return new FieldCollectionReader<>((FieldCollectionMap) fieldMap, codecRegistry);
        }
        else {
            throw new IllegalStateException(String.format("%s field map is not supported", fieldMap));
        }
    }
}
