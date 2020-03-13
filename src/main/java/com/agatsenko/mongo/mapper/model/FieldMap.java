/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper.model;

import com.agatsenko.mongo.mapper.util.Check;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.vavr.Function1;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "fieldName")
public class FieldMap<TEntity, TField, TDocValue> {
    private final String fieldName;
    private final String docKey;

    private final Class<TField> fieldType;
    private final Function1<TEntity, TField> fieldExtractor;

    private final FieldCodec<TField, TDocValue> codec;

    @Builder
    public FieldMap(
            @NonNull String fieldName,
            String docKey,
            @NonNull Class<TField> fieldType,
            @NonNull Function1<TEntity, TField> fieldExtractor,
            FieldCodec<TField, TDocValue> codec) {
        Check.argNotNullOrEmpty(fieldName, "fieldName");
        Check.argNotNull(fieldType, "fieldType");
        Check.argNotNull(fieldExtractor, "fieldExtractor");

        this.fieldName = fieldName;
        this.docKey = docKey == null ? fieldName : docKey;

        this.fieldType = fieldType;
        this.fieldExtractor = fieldExtractor;

        this.codec = codec;
    }

    public static <TEntity, TField> FieldMapBuilder<TEntity, TField, TField> simpleBuilder() {
        return FieldMap.builder();
    }
}
