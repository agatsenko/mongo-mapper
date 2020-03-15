package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.util.Check;
import com.agatsenko.mongo.mapper.mapping.FieldMap;
import com.agatsenko.mongo.mapper.mapping.FieldValueMap;
import org.bson.BsonReader;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

public final class FieldValueReader<TEntity, TField, TDocValue> implements FieldReader<TEntity, TField, TDocValue> {
    private final FieldValueMap<TEntity, TField, TDocValue> fieldMap;
    private final Codec<TField> codec;

    public FieldValueReader(FieldValueMap<TEntity, TField, TDocValue> fieldMap, CodecRegistry registry) {
        this.fieldMap = fieldMap;
        this.codec = fieldMap.getCodec() == null ? registry.get(fieldMap.getFieldType()) : fieldMap.getCodec();
        Check.state(codec != null, "unable to resolve codec for %s", fieldMap);
    }

    @Override
    public FieldMap<TEntity, TField, TDocValue> getFieldMap() {
        return fieldMap;
    }

    @Override
    public TField read(BsonReader reader, ReadContext<TEntity> context) {
        return codec.decode(reader, context.getDecoderContext());
    }
}
