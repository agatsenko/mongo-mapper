/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-13
 */
package com.agatsenko.mongo.mapper.impl;

import org.bson.codecs.EncoderContext;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.agatsenko.mongo.mapper.mapping.EntityMap;

@RequiredArgsConstructor
@Getter
public final class WriteContext<TEntity> {
    @NonNull
    private final EntityMap<TEntity> entityMap;
    @NonNull
    private final EncoderContext encoderContext;
}
