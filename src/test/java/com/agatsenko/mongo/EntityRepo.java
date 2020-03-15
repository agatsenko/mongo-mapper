/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-15
 */
package com.agatsenko.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;

import com.agatsenko.mongo.mapper.mapping.EntityMap;
import com.agatsenko.mongo.mapper.mapping.FieldMap;
import com.agatsenko.mongo.mapper.util.Check;

public abstract class EntityRepo<TEntity, TFieldId, TDocValueId> {
    private static final String FIELD_PATH_DELIMITER = ".";

    protected static final ReplaceOptions saveOptions = new ReplaceOptions()
            .upsert(true);
    protected static final Bson emptyFilter = new Bson() {
        @Override
        public <TDocument> BsonDocument toBsonDocument(Class<TDocument> tDocumentClass, CodecRegistry codecRegistry) {
            return new BsonDocument();
        }
    };

    private final EntityMap<TEntity> entityMap;
    private final FieldMap<TEntity, TFieldId, TDocValueId> idFieldMap;
    private final DatabaseNameProvider dbNameProvider;
    private final MongoClient mongoClient;

    public EntityRepo(
            EntityMap<TEntity> entityMap,
            FieldMap<TEntity, TFieldId, TDocValueId> idFieldMap,
            DatabaseNameProvider dbNameProvider,
            MongoClient mongoClient) {
        Check.argNotNull(entityMap, "entityMap");
        Check.argNotNull(idFieldMap, "idFieldMap");
        Check.arg(entityMap.getFields().contains(idFieldMap), "entityMap does not contain idFieldMap");
        Check.argNotNull(dbNameProvider, "dbNameProvider");
        Check.argNotNull(mongoClient, "mongoClient");

        this.entityMap = entityMap;
        this.idFieldMap = idFieldMap;
        this.dbNameProvider = dbNameProvider;
        this.mongoClient = mongoClient;
    }

    public EntityMap<TEntity> getEntityMap() {
        return entityMap;
    }

    public FieldMap<TEntity, TFieldId, TDocValueId> getIdFieldMap() {
        return idFieldMap;
    }

    public Optional<TEntity> findById(TFieldId id) {
        return Optional.ofNullable(
                getCollection().find(Filters.eq(path(idFieldMap), idFieldMap.filterVal(id))).first()
        );
    }

    public List<TEntity> findAll() {
        return toList(getCollection().find());
    }

    public TEntity save(TEntity entity) {
        getCollection().replaceOne(
                Filters.eq(
                        path(idFieldMap),
                        idFieldMap.filterVal(getIdFieldMap().getFieldExtractor().apply(entity))
                ),
                entity,
                saveOptions
        );
        return entity;
    }

    public void removeById(TFieldId id) {
        getCollection().deleteOne(Filters.eq(path(idFieldMap), idFieldMap.filterVal(id)));
    }

    public void remove(TEntity entity) {
        removeById(idFieldMap.getFieldExtractor().apply(entity));
    }

    protected String path(FieldMap<?, ?, ?> field) {
        return field.getDocKey();
    }

    protected String path(FieldMap<?, ?, ?>... fields) {
        Check.argNotNullOrEmpty(fields, "fields");
        if (fields.length == 1) {
            return path(fields[0]);
        }
        final var sb = new StringBuilder();
        for (final var field : fields) {
            sb.append(field.getDocKey()).append(FIELD_PATH_DELIMITER);
        }
        sb.delete(sb.length() - FIELD_PATH_DELIMITER.length(), sb.length());
        return sb.toString();
    }

    protected MongoDatabase getDatabase() {
        return mongoClient.getDatabase(dbNameProvider.getDatabaseName());
    }

    protected MongoCollection<TEntity> getCollection(MongoDatabase db) {
        return db.getCollection(entityMap.getDocCollectionName(), entityMap.getType());
    }

    protected MongoCollection<TEntity> getCollection() {
        return getCollection(getDatabase());
    }

    protected <T> List<T> toList(FindIterable<T> iterable) {
        final var list = new ArrayList<T>();
        for (final var item : iterable) {
            list.add(item);
        }
        return list;
    }
}
