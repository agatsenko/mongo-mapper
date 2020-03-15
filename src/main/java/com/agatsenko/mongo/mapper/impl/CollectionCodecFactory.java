/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapper.impl;

import java.util.Collection;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;
import lombok.experimental.UtilityClass;

import com.agatsenko.mongo.mapper.mapping.FieldCollectionMap;
import com.agatsenko.mongo.mapper.util.Check;

@UtilityClass
public class CollectionCodecFactory {
    public static <TEntity, TItem, TDocItem, TCollection extends Collection<TItem>>
    Codec<TCollection> ensureCreate(
            FieldCollectionMap<TEntity, TItem, TDocItem, TCollection> fieldMap,
            CodecRegistry codecRegistry) {
        if (fieldMap.getCodec() != null) {
            return fieldMap.getCodec();
        }
        final var itemCodec = fieldMap.getItemCodec() == null
                ? codecRegistry.get(fieldMap.getItemType())
                : fieldMap.getItemCodec();
        Check.state(itemCodec != null, "unable to resolve codec for %s type", fieldMap.getItemType());
        return new CollectionCodec<>(fieldMap.getFieldType(), fieldMap.getCollectionCreator(), itemCodec);
    }
}
