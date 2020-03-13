/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-13
 */
package com.agatsenko.mongo.mapper.impl;

import org.bson.BsonWriter;
import org.bson.codecs.configuration.CodecRegistry;

import com.agatsenko.mongo.mapper.model.FieldMap;
import com.agatsenko.mongo.mapper.model.FieldValueMap;


public interface FieldWriter<TEntity, TField, TDocValue> {
    FieldMap<TEntity, TField, TDocValue> getFieldMap();

    void write(BsonWriter writer, TField field, WriteContext<TEntity> context);

    static <TEntity, TField, TDocValue> FieldWriter<TEntity, TField, TDocValue> create(
            FieldMap<TEntity, TField, TDocValue> fieldMap,
            CodecRegistry codecRegistry) {
        if (fieldMap instanceof FieldValueMap) {
            return new FieldValueWriter<>(fieldMap, codecRegistry);
        }
        else {
            throw new IllegalStateException(String.format("%s field map is unsupported", fieldMap));
        }
    }
}
