package com.agatsenko.mongo.test.mapping;

import com.agatsenko.mongo.mapper.model.EntityMap;
import com.agatsenko.mongo.mapper.model.FieldMap;
import com.agatsenko.mongo.test.mapping.codecs.BoolAsStrCharFieldCodec;
import com.agatsenko.mongo.test.model.RootEntity;
import com.agatsenko.mongo.test.model.RootEntityId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RootEntityMapper {
    public static FieldMap<RootEntity, RootEntityId, String> idField = FieldMap
            .<RootEntity, RootEntityId, String>builder()
            .fieldName("id")
            .fieldType(RootEntityId.class)
            .fieldExtractor(RootEntity::getId)
            .build();

    public static FieldMap<RootEntity, Integer, Integer> intValueField = FieldMap.<RootEntity, Integer>simpleBuilder()
            .fieldName("intValue")
            .fieldType(Integer.class)
            .fieldExtractor(RootEntity::getIntValue)
            .build();

    public static FieldMap<RootEntity, Boolean, String> boolAsStrCharValueField = FieldMap
            .<RootEntity, Boolean, String>builder()
            .fieldName("boolAsStrCharValue")
            .docKey("boolValue")
            .fieldType(Boolean.class)
            .fieldExtractor(RootEntity::isBoolAsStrCharValue)
            .codec(BoolAsStrCharFieldCodec.instance)
            .build();

    public static FieldMap<RootEntity, String, String> strValueField = FieldMap.<RootEntity, String>simpleBuilder()
            .fieldName("strValue")
            .fieldType(String.class)
            .fieldExtractor(RootEntity::getStrValue)
            .build();

    public static FieldMap<RootEntity, Integer, Integer> nullableIntValueField = FieldMap
            .<RootEntity, Integer>simpleBuilder()
            .fieldName("strValue")
            .fieldType(Integer.class)
            .fieldExtractor(RootEntity::getNullableIntValue)
            .build();

//    public static final EntityMap<RootEntity> entityMap;
}
