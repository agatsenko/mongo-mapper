package com.agatsenko.mongo.mapper.mapping;

import lombok.Getter;

@Getter
public class ValueMap<TValue, TDocValue> {
    private final ValueCodec<TValue, TDocValue> codec;

    public ValueMap(ValueCodec<TValue, TDocValue> codec) {
        this.codec = codec;
    }
}
