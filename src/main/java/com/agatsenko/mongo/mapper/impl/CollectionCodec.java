/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapper.impl;

import java.util.Collection;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import io.vavr.Function0;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CollectionCodec<TItem, TCollection extends Collection<TItem>> implements Codec<TCollection> {
    private final Class<TCollection> collectionType;
    private final Function0<TCollection> collectionCreator;
    private final Codec<TItem> itemCodec;

    @Override
    public TCollection decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            return null;
        }
        final var collection = collectionCreator.apply();
        reader.readStartArray();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            collection.add(itemCodec.decode(reader, decoderContext));
        }
        reader.readEndArray();
        return collection;
    }

    @Override
    public void encode(BsonWriter writer, TCollection collection, EncoderContext encoderContext) {
        if (collection == null) {
            writer.writeNull();
        }
        else {
            writer.writeStartArray();
            collection.forEach(item -> itemCodec.encode(writer, item, encoderContext));
            writer.writeEndArray();
        }
    }

    @Override
    public Class<TCollection> getEncoderClass() {
        return collectionType;
    }
}
