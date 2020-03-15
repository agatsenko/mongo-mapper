/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-15
 */
package com.agatsenko.mongo;

import java.util.List;
import java.util.UUID;

import com.mongodb.client.MongoClient;
import com.mongodb.client.model.Filters;

import com.agatsenko.mongo.mapping.InnerEntityMap;
import com.agatsenko.mongo.mapping.RootEntityMap;
import com.agatsenko.mongo.model.KeyValue;
import com.agatsenko.mongo.model.RootEntity;

import static com.mongodb.client.model.Filters.eq;

import static com.agatsenko.mongo.mapping.RootEntityMap.*;

public class RootEntityRepo extends EntityRepo<RootEntity, UUID, UUID> {
    public RootEntityRepo(DatabaseNameProvider dbNameProvider, MongoClient mongoClient) {
        super(RootEntityMap.entityMap, idField, dbNameProvider, mongoClient);
    }

    public List<RootEntity> findByBoolAsNumStr(boolean value) {
        return toList(getCollection().find(eq(path(boolAsNumCharField), boolAsNumCharField.filterVal(value))));
    }

    public List<RootEntity> findByInnerEntityStrVal(String strVal) {
        return toList(getCollection().find(eq(path(innerEntityField, InnerEntityMap.strValField), strVal)));
    }

    public List<RootEntity> findByInnerEntitySetStrVal(String strVal) {
        return toList(getCollection().find(eq(path(innerEntitySetField, InnerEntityMap.strValField), strVal)));
    }

    public List<RootEntity> findByKeyValue(KeyValue keyValue) {
        return toList(getCollection().find(eq(path(keyValuesField), keyValuesField.filterVal(keyValue))));
    }

    public List<RootEntity> findByKeyValues(KeyValue keyValue1, KeyValue keyValue2) {
        return toList(getCollection().find(
                Filters.or(
                        eq(path(keyValuesField), keyValuesField.filterVal(keyValue1)),
                        eq(path(keyValuesField), keyValuesField.filterVal(keyValue2))
                )
        ));
    }

    public void removeAll() {
        getCollection().deleteMany(emptyFilter);
    }
}
