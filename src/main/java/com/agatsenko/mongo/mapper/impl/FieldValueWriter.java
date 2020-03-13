/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-13
 */
package com.agatsenko.mongo.mapper.impl;

import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

import com.agatsenko.mongo.mapper.mapping.FieldMap;
import com.agatsenko.mongo.mapper.util.Check;

public final class FieldValueWriter<TEntity, TField, TDocValue> implements FieldWriter<TEntity, TField, TDocValue> {
    private final FieldMap<TEntity, TField, TDocValue> fieldMap;
    private final Codec<TField> codec;

    public FieldValueWriter(
            FieldMap<TEntity, TField, TDocValue> fieldMap,
            CodecRegistry codecRegistry) {
        this.fieldMap = fieldMap;
        this.codec = fieldMap.getCodec() == null ? codecRegistry.get(fieldMap.getFieldType()) : fieldMap.getCodec();
        Check.state(this.codec != null, "unable to resolve codec for %s", fieldMap);
    }

    @Override
    public FieldMap<TEntity, TField, TDocValue> getFieldMap() {
        return fieldMap;
    }

    @Override
    public void write(BsonWriter writer,TField field, WriteContext<TEntity> context) {
        codec.encode(writer, field, context.getEncoderContext());
    }
}
