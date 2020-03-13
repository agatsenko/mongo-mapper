package com.agatsenko.mongo.test.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class RootEntityId {
    @NonNull
    private final String value;
}
