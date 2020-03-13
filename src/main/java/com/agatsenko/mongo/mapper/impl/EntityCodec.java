/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.model.EntityMap;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class EntityCodec<T> implements Codec<T> {
    private final EntityMap<T> entityMap;

    @Override
    public T decode(BsonReader reader, DecoderContext decoderContext) {
        // FIXME: not yet implemented
        throw new IllegalStateException("not yet implemented");
    }

    @Override
    public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
        // FIXME: not yet implemented
        throw new IllegalStateException("not yet implemented");
    }

    @Override
    public Class<T> getEncoderClass() {
        // FIXME: not yet implemented
        throw new IllegalStateException("not yet implemented");
    }
}
