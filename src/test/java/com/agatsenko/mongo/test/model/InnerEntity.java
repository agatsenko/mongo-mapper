package com.agatsenko.mongo.test.model;

import lombok.Builder;
import lombok.Value;

@Value
public class InnerEntity {
    private final int intAsStrValue;
    private final boolean boolAsNumStrValue;
    private final String nullableStrValue;

    @Builder
    public InnerEntity(int intAsStrValue, boolean boolAsNumStrValue, String nullableStrValue) {
        this.intAsStrValue = intAsStrValue;
        this.boolAsNumStrValue = boolAsNumStrValue;
        this.nullableStrValue = nullableStrValue;
    }
}
