package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.util.Check;
import com.agatsenko.mongo.mapper.mapping.FieldMap;
import com.agatsenko.mongo.mapper.mapping.FieldValueMap;
import org.bson.BsonReader;

public final class FieldValueReader<TEntity, TField, TDocValue> implements FieldReader<TEntity, TField, TDocValue> {
    private final FieldValueMap<TEntity, TField, TDocValue> fieldMap;

    public FieldValueReader(FieldValueMap<TEntity, TField, TDocValue> fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public FieldMap<TEntity, TField, TDocValue> getFieldMap() {
        return fieldMap;
    }

    @Override
    public TField read(BsonReader reader, ReadContext<TEntity> context) {
        final var codec = fieldMap.getCodec() == null
                ? context.getCodecRegistry().get(fieldMap.getFieldType())
                : fieldMap.getCodec();
        Check.state(codec != null, "codec is not found for %s field", fieldMap);
        return codec.decode(reader, context.getDecoderContext());
    }
}
