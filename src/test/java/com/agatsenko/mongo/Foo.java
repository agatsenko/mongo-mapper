package com.agatsenko.mongo;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.bson.types.ObjectId;

import java.util.Set;

@Value
public class Foo {
    private final ObjectId id;
    private final String strValue;
    private final Set<String> strSet;

    @Builder
    public Foo(ObjectId id, String strValue, @Singular("strSetItem") Set<String> strSet) {
        this.id = id;
        this.strValue = strValue;
        this.strSet = strSet;
    }
}
