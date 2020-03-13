package com.agatsenko.mongo.mapper_.impl;

import com.agatsenko.mongo.mapper_.model.FieldMap;
import org.bson.BsonReader;

public interface FieldReader<TEntity, TField, TDocValue> {
    FieldMap<TEntity, TField, TDocValue> getFieldMap();

    TField read(BsonReader reader, ReadContext<TEntity> context);
}
