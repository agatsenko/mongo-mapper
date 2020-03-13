package com.agatsenko.mongo.mapper.mapping;

import com.agatsenko.mongo.mapper.util.Check;

import lombok.*;

import java.util.Set;

@Getter
@EqualsAndHashCode(of = "type")
@ToString
public final class EntityMap<TEntity> {
    public static final String DEFAULT_DISCRIMINATOR_KEY = "_class";

    private final Class<TEntity> type;
    private final String docCollectionName;

    private final Set<FieldMap<TEntity, ?, ?>> fields;
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
        this.writeNulls = writeNulls;
        this.discriminatorEnabled = discriminatorEnabled;
        if (discriminatorEnabled) {
            this.discriminatorKey = discriminatorKey == null ? DEFAULT_DISCRIMINATOR_KEY : discriminatorKey;
            this.discriminator = discriminator == null ? type.getName() : discriminator;
        } else {
            this.discriminatorKey = null;
            this.discriminator = null;
        }
        this.entityCreator = entityCreator;
    }
}
