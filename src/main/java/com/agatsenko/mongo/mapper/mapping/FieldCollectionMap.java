/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapper.mapping;

import java.util.Collection;
import java.util.List;

import io.vavr.Function0;
import io.vavr.Function1;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import com.agatsenko.mongo.mapper.util.Check;

@ToString(of = "itemType", callSuper = true)
@Getter
public final class FieldCollectionMap<TEntity, TItem, TDocItem, TCollection extends Collection<TItem>>
        extends FieldMap<TEntity, TCollection, List<TDocItem>> {
    private final Function0<TCollection> collectionCreator;
    private final Class<TItem> itemType;
    private final ValueCodec<TItem, TDocItem> itemCodec;

    @Builder
    public FieldCollectionMap(
            String fieldName,
            String docKey,
            Class<TItem> itemType,
            Class<TCollection> collectionType,
            Function0<TCollection> collectionCreator,
            Function1<TEntity, TCollection> fieldExtractor,
            ValueCodec<TItem, TDocItem> itemCodec,
            ValueCodec<TCollection, List<TDocItem>> collectionCodec) {
        super(fieldName, docKey, collectionType, fieldExtractor, collectionCodec);

        Check.argNotNull(collectionCreator, "collectionCreator");
        Check.argNotNull(itemType, "itemType");

        this.collectionCreator = collectionCreator;
        this.itemType = itemType;
        this.itemCodec = itemCodec;
    }

    public static <TEntity, TItem, TCollection extends Collection<TItem>>
    FieldCollectionMapBuilder<TEntity, TItem, TItem, TCollection> directBuilder() {
        return FieldCollectionMap.builder();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected Object notNullFilterVal(Object field) {
        return itemCodec == null ? field : ((ValueCodec) itemCodec).toDocValue(field);
    }
}
