package com.agatsenko.mongo.mapper_.model;

import org.bson.codecs.Codec;

public interface ValueCodec<TValue, TDocValue> extends Codec<TValue> {
    TValue toValue(TDocValue docValue);

    TDocValue toDocValue(TValue value);
}
