/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.vavr.Function1;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "fieldName")
public final class FieldMap<TEntity, TField, TDocValue> {
    private final String fieldName;
    private final String docKey;

    private final Class<TEntity> entityType;
    private final Class<TField> fieldType;
    private final Function1<TEntity, TField> fieldExtractor;

    private final FieldCodec<TField, TDocValue> codec;

    @Builder
    public FieldMap(
            @NonNull String fieldName,
            @NonNull String docKey,
            @NonNull Class<TEntity> entityType,
            @NonNull Class<TField> fieldType,
            @NonNull Function1<TEntity, TField> fieldExtractor,
            FieldCodec<TField, TDocValue> codec) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(fieldName), "fieldName is null or empty");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(docKey), "docKey is null or empty");
        Preconditions.checkArgument(entityType != null, "entityType is null");
        Preconditions.checkArgument(fieldType != null, "fieldType is null");
        Preconditions.checkArgument(fieldExtractor != null, "fieldExtractor is null");

        this.fieldName = fieldName;
        this.docKey = docKey;

        this.entityType = entityType;
        this.fieldType = fieldType;
        this.fieldExtractor = fieldExtractor;

        this.codec = codec;
    }
}
