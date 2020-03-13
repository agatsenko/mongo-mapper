package com.agatsenko.mongo.mapper.model;

import io.vavr.Function1;
import lombok.Builder;

public final class FieldValueMap<TEntity, TField, TDocValue> extends FieldMap<TEntity, TField, TDocValue> {
    @Builder
    public FieldValueMap(
            String fieldName,
            String docKey,
            Class<TField> fieldType,
            Function1<TEntity, TField> fieldExtractor,
            ValueCodec<TField, TDocValue> codec) {
        super(fieldName, docKey, fieldType, fieldExtractor, codec);
    }

    public static <TEntity, TField> FieldValueMapBuilder<TEntity, TField, TField> directBuilder() {
        return builder();
    }
}
