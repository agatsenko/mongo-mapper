/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapper.impl;

import java.util.Collection;
import java.util.List;

import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;

import com.agatsenko.mongo.mapper.mapping.FieldCollectionMap;
import com.agatsenko.mongo.mapper.mapping.FieldMap;

public class FieldCollectionWriter<TEntity, TItem, TDocItem, TCollection extends Collection<TItem>>
        implements FieldWriter<TEntity, TCollection, List<TDocItem>> {
    private final FieldCollectionMap<TEntity, TItem, TDocItem, TCollection> fieldMap;
    private final Codec<TCollection> collectionCodec;

    @Override
    public FieldMap<TEntity, TCollection, List<TDocItem>> getFieldMap() {
        return fieldMap;
    }

    public FieldCollectionWriter(
            FieldCollectionMap<TEntity, TItem, TDocItem, TCollection> fieldMap,
            CodecRegistry codecRegistry) {
        this.fieldMap = fieldMap;
        collectionCodec = CollectionCodecFactory.ensureCreate(fieldMap, codecRegistry);
    }

    @Override
    public void write(BsonWriter writer, TCollection collection, WriteContext<TEntity> context) {
        collectionCodec.encode(writer, collection, context.getEncoderContext());
    }
}
