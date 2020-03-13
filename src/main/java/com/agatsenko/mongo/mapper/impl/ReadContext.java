package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.model.EntityMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.configuration.CodecRegistry;

@RequiredArgsConstructor
@Getter
public final class ReadContext<TEntity> {
    @NonNull
    private final EntityMap<TEntity> entityMap;
    @NonNull
    private final CodecRegistry codecRegistry;
    @NonNull
    private final DecoderContext decoderContext;

    @Setter
    private volatile String discriminator;
}