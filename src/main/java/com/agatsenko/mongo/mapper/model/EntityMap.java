/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper.model;

import java.util.Objects;
import java.util.Set;

import com.agatsenko.mongo.mapper.util.Check;
import com.google.common.base.Strings;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "type")
public final class EntityMap<TEntity> {
    private final Class<TEntity> type;
    private final String docCollectionName;

    private final Set<FieldMap<TEntity, ?, ?>> fields;
    private final FieldMap<TEntity, ?, ?> docIdFieldMap;
    private final boolean writeNulls;

    private final boolean discriminatorEnabled;
    private final String discriminatorKey;
    private final String discriminator;

    private final EntityCreator<TEntity> entityCreator;

    @Builder
    public EntityMap(
            Class<TEntity> type,
            String docCollectionName,
            @Singular Set<FieldMap<TEntity, ?, ?>> fields,
            FieldMap<TEntity, ?, ?> docIdFieldMap,
            boolean writeNulls,
            boolean discriminatorEnabled,
            String discriminatorKey,
            String discriminator,
            EntityCreator<TEntity> entityCreator) {
        Check.argNotNull(type, "type");
        Check.argNotNullOrEmpty(fields, "fields");
        Check.argNotNull(entityCreator, "entityCreator");

        this.type = type;
        this.docCollectionName = docCollectionName == null ? type.getSimpleName() : docCollectionName;
        this.fields = fields;
        if (docIdFieldMap != null) {
            Check.arg(fields.contains(docIdFieldMap), "fields should contain docIdFieldMap");
            this.docIdFieldMap = docIdFieldMap;
        } else {
            this.docIdFieldMap = null;
        }
        this.writeNulls = writeNulls;
        this.discriminatorEnabled = discriminatorEnabled;
        this.discriminatorKey = discriminatorKey;
        this.discriminator = discriminator;
        this.entityCreator = entityCreator;
    }
}
