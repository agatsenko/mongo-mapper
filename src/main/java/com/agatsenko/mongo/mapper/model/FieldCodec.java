/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper.model;

import org.bson.codecs.Codec;

public interface FieldCodec<TField, TDocValue> extends Codec<TField> {
    TField toField(TDocValue docValue);

    TDocValue toDocValue(TField field);
}
