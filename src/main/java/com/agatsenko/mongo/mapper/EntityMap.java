/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper;

import java.util.Objects;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Singular;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "type")
public final class EntityMap<TEntity> {
    private final Class<TEntity> type;

    private final Set<FieldMap<TEntity, ?, ?>> fields;
    private final FieldMap<TEntity, ?, ?> docIdFieldMap;
    private final boolean writeNulls;

    private final boolean discriminatorEnabled;
    private final String discriminatorKey;
    private final String discriminator;

    private final EntityCreator<TEntity> entityCreator;

    public EntityMap(
            Class<TEntity> type,
            @Singular Set<FieldMap<TEntity, ?, ?>> fields,
            String docIdFieldName,
            boolean writeNulls,
            boolean discriminatorEnabled,
            String discriminatorKey,
            String discriminator,
            EntityCreator<TEntity> entityCreator) {
        // FIXME: not yet implemented

        this.type = type;
        this.fields = fields;
        this.docIdFieldMap = fields.stream()
                .filter(f -> Objects.equals(f.getFieldName(), docIdFieldName))
                .findAny()
                .orElse(null);
        this.writeNulls = writeNulls;
        this.discriminatorEnabled = discriminatorEnabled;
        this.discriminatorKey = discriminatorKey;
        this.discriminator = discriminator;
        this.entityCreator = entityCreator;
    }
}
