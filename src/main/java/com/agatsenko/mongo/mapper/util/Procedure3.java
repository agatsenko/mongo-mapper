package com.agatsenko.mongo.mapper.util;

@FunctionalInterface
public interface Procedure3<A1, A2, A3> {
    void apply(A1 a1, A2 a2, A3 a3);
}
