package com.agatsenko.mongo.mapper_.model;

import com.agatsenko.mongo.mapper.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@EqualsAndHashCode(of = "type")
@ToString
public class EntityMap<TEntity> {
    public static final String DEFAULT_DISCRIMINATOR_KEY = "_class";

    private final Class<TEntity> type;
    private final String docCollectionName;

    private final Set<FieldValueMap<TEntity, ?, ?>> fields;
    private final FieldValueMap<TEntity, ?, ?> docIdField;
    private final boolean writeNulls;

    private final boolean discriminatorEnabled;
    private final String discriminatorKey;
    private final String discriminator;

    private final EntityCreator<TEntity> entityCreator;

    @Builder
    public EntityMap(
            Class<TEntity> type,
            String docCollectionName,
            Set<FieldValueMap<TEntity, ?, ?>> fields,
            FieldValueMap<TEntity, ?, ?> docIdField,
            boolean writeNulls,
            boolean discriminatorEnabled,
            String discriminatorKey,
            String discriminator,
            EntityCreator<TEntity> entityCreator) {
        Check.argNotNull(type, "type");
        Check.argNotNullOrEmpty(fields, "fields");
        if (docIdField != null) {
            Check.arg(fields.contains(docIdField), "fields should contain docIdField");
        }
        Check.argNotNull(entityCreator, "entityCreator");

        this.type = type;
        this.docCollectionName = docCollectionName == null ? type.getSimpleName() : docCollectionName;
        this.fields = fields;
        this.docIdField = docIdField;
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
