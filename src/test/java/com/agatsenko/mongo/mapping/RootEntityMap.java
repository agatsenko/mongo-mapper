/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapping;

import java.util.*;

import lombok.experimental.UtilityClass;

import com.agatsenko.mongo.mapper.mapping.EntityMap;
import com.agatsenko.mongo.mapper.mapping.FieldCollectionMap;
import com.agatsenko.mongo.mapper.mapping.FieldMap;
import com.agatsenko.mongo.mapper.mapping.FieldValueMap;
import com.agatsenko.mongo.mapping.codec.BoolAsNumCharValueCodec;
import com.agatsenko.mongo.mapping.codec.BoolAsYesNoCharValueCodec;
import com.agatsenko.mongo.mapping.codec.IntAsStrValueCodec;
import com.agatsenko.mongo.model.InnerEntity;
import com.agatsenko.mongo.model.KeyValue;
import com.agatsenko.mongo.model.RootEntity;

@UtilityClass
public class RootEntityMap {
    public static final FieldMap<RootEntity, UUID, UUID> idField = FieldValueMap
            .<RootEntity, UUID>directBuilder()
            .fieldName("id")
            .docKey(EntityMap.ID_DOC_KEY)
            .fieldType(UUID.class)
            .fieldExtractor(RootEntity::getId)
            .build();
    public static final FieldMap<RootEntity, Boolean, String> boolAsYesNoCharField = FieldValueMap
            .<RootEntity, Boolean, String>builder()
            .fieldName("boolAsYesNoChar")
            .fieldType(Boolean.class)
            .fieldExtractor(RootEntity::isBoolAsNumChar)
            .codec(BoolAsYesNoCharValueCodec.instance)
            .build();
    public static final FieldMap<RootEntity, Boolean, String> boolAsNumCharField = FieldValueMap
            .<RootEntity, Boolean, String>builder()
            .fieldName("boolAsNumChar")
            .fieldType(Boolean.class)
            .fieldExtractor(RootEntity::isBoolAsNumChar)
            .codec(BoolAsNumCharValueCodec.instance)
            .build();
    public static final FieldMap<RootEntity, Boolean, Boolean> boolField = FieldValueMap
            .<RootEntity, Boolean>directBuilder()
            .fieldName("bool")
            .fieldType(Boolean.class)
            .fieldExtractor(RootEntity::isBool)
            .build();
    public static final FieldMap<RootEntity, Integer, String> intAsStrField = FieldValueMap
            .<RootEntity, Integer, String>builder()
            .fieldName("intAsStr")
            .fieldType(Integer.class)
            .fieldExtractor(RootEntity::getIntAsStr)
            .codec(IntAsStrValueCodec.instance)
            .build();
    public static final FieldMap<RootEntity, Integer, Integer> intValField = FieldValueMap
            .<RootEntity, Integer>directBuilder()
            .fieldName("intVal")
            .fieldType(Integer.class)
            .fieldExtractor(RootEntity::getIntVal)
            .build();
    public static final FieldMap<RootEntity, String, String> strField = FieldValueMap
            .<RootEntity, String>directBuilder()
            .fieldName("str")
            .fieldType(String.class)
            .fieldExtractor(RootEntity::getStr)
            .build();
    public static final FieldMap<RootEntity, InnerEntity, InnerEntity> innerEntityField = FieldValueMap
            .<RootEntity, InnerEntity>directBuilder()
            .fieldName("innerEntity")
            .fieldType(InnerEntity.class)
            .fieldExtractor(RootEntity::getInnerEntity)
            .build();
    @SuppressWarnings("unchecked")
    public static final FieldMap<RootEntity, Set<InnerEntity>, List<InnerEntity>> innerEntitySetField =
            FieldCollectionMap
                    .<RootEntity, InnerEntity, Set<InnerEntity>>directBuilder()
                    .fieldName("innerEntitySet")
                    .collectionType((Class<Set<InnerEntity>>) (Class<?>) Set.class)
                    .collectionCreator(HashSet::new)
                    .itemType(InnerEntity.class)
                    .fieldExtractor(RootEntity::getInnerEntitySet)
                    .build();
    @SuppressWarnings("unchecked")
    public static final FieldMap<RootEntity, Collection<InnerEntity>, List<InnerEntity>> innerEntitiesField =
            FieldCollectionMap
                    .<RootEntity, InnerEntity, Collection<InnerEntity>>directBuilder()
                    .fieldName("innerEntities")
                    .collectionType((Class<Collection<InnerEntity>>) (Class<?>) Collection.class)
                    .collectionCreator(LinkedList::new)
                    .itemType(InnerEntity.class)
                    .fieldExtractor(RootEntity::getInnerEntities)
                    .build();
    @SuppressWarnings("unchecked")
    public static final FieldMap<RootEntity, List<KeyValue>, List<KeyValue>> keyValuesField =
            FieldCollectionMap
                    .<RootEntity, KeyValue, List<KeyValue>>directBuilder()
                    .fieldName("keyValues")
                    .collectionType((Class<List<KeyValue>>) (Class<?>) List.class)
                    .collectionCreator(LinkedList::new)
                    .itemType(KeyValue.class)
                    .fieldExtractor(RootEntity::getKeyValues)
                    .build();

    public static final EntityMap<RootEntity> entityMap = EntityMap.<RootEntity>builder()
            .type(RootEntity.class)
            .docCollectionName("entities")
            .field(idField)
            .field(boolAsYesNoCharField)
            .field(boolAsNumCharField)
            .field(boolField)
            .field(intAsStrField)
            .field(intValField)
            .field(strField)
            .field(innerEntityField)
            .field(innerEntitySetField)
            .field(innerEntitiesField)
            .field(keyValuesField)
            .entityCreator((values, ignore) -> new RootEntity(
                    values.get(idField),
                    values.get(boolAsYesNoCharField),
                    values.get(boolAsNumCharField),
                    values.get(boolField),
                    values.get(intAsStrField),
                    values.get(intValField),
                    values.get(strField),
                    values.get(innerEntityField),
                    values.get(innerEntitySetField),
                    values.get(innerEntitiesField),
                    values.get(keyValuesField)
            ))
            .build();
}
