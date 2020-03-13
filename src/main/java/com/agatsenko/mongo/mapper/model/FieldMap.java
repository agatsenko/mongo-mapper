package com.agatsenko.mongo.mapper.model;

import com.agatsenko.mongo.mapper.util.Check;
import io.vavr.Function1;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(of = "fieldName", callSuper = false)
@ToString(of = {"fieldName", "docKey", "fieldType"})
public abstract class FieldMap<TEntity, TField, TDocValue> extends ValueMap<TField, TDocValue> {
    private final String fieldName;
    private final String docKey;

    private final Class<TField> fieldType;
    private final Function1<TEntity, TField> fieldExtractor;

    public FieldMap(
            String fieldName,
            String docKey,
            Class<TField> fieldType,
            Function1<TEntity, TField> fieldExtractor,
            ValueCodec<TField, TDocValue> codec) {
        super(codec);

        Check.argNotNullOrEmpty(fieldName, "fieldName");
        Check.argNotNull(fieldType, "fieldType");
        Check.argNotNull(fieldExtractor, "fieldExtractor");

        this.fieldName = fieldName;
        this.docKey = docKey == null ? fieldName : docKey;
        this.fieldType = fieldType;
        this.fieldExtractor = fieldExtractor;
    }
}
