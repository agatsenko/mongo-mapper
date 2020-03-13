package com.agatsenko.mongo.test.mapping;

import com.agatsenko.mongo.mapper.model.EntityMap;
import com.agatsenko.mongo.mapper.model.FieldMap;
import com.agatsenko.mongo.test.mapping.codecs.BoolAsNumStrFieldCodec;
import com.agatsenko.mongo.test.mapping.codecs.IntAsStrFieldCodec;
import com.agatsenko.mongo.test.model.InnerEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class InnerEntityMappper {
    public static final FieldMap<InnerEntity, Integer, String> intAsStrValueField = FieldMap
            .<InnerEntity, Integer, String>builder()
            .fieldName("intAsStrValue")
            .docKey("int_as_str_value")
            .fieldType(Integer.class)
            .fieldExtractor(InnerEntity::getIntAsStrValue)
            .codec(IntAsStrFieldCodec.instance)
            .build();

    public static final FieldMap<InnerEntity, Boolean, String> boolAsNumStrValueField = FieldMap
            .<InnerEntity, Boolean, String>builder()
            .fieldName("boolAsNumStrValue")
            .fieldType(Boolean.class)
            .fieldExtractor(InnerEntity::isBoolAsNumStrValue)
            .codec(BoolAsNumStrFieldCodec.instance)
            .build();

    public static final FieldMap<InnerEntity, String, String> nullableStrValueField = FieldMap
            .<InnerEntity, String>simpleBuilder()
            .fieldName("nullableStrValue")
            .fieldType(String.class)
            .fieldExtractor(InnerEntity::getNullableStrValue)
            .build();

    public static final EntityMap<InnerEntity> entityMap = EntityMap.<InnerEntity>builder()
            .type(InnerEntity.class)
            .field(intAsStrValueField)
            .field(boolAsNumStrValueField)
            .field(nullableStrValueField)
            .entityCreator(values -> new InnerEntity(
                    values.get(intAsStrValueField),
                    values.get(boolAsNumStrValueField),
                    values.get(nullableStrValueField)
            ))
            .build();
}
