/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapping;

import lombok.experimental.UtilityClass;

import com.agatsenko.mongo.mapper.mapping.EntityMap;
import com.agatsenko.mongo.mapper.mapping.FieldMap;
import com.agatsenko.mongo.mapper.mapping.FieldValueMap;
import com.agatsenko.mongo.model.InnerEntity;

@UtilityClass
public class InnerEntityMap {
    public static final FieldMap<InnerEntity, Integer, Integer> intValField = FieldValueMap
            .<InnerEntity, Integer>directBuilder()
            .fieldName("intVal")
            .fieldType(Integer.class)
            .fieldExtractor(InnerEntity::getIntVal)
            .build();
    public static final FieldMap<InnerEntity, String, String> strValField = FieldValueMap
            .<InnerEntity, String>directBuilder()
            .fieldName("strVal")
            .fieldType(String.class)
            .fieldExtractor(InnerEntity::getStrVal)
            .build();

    public static final EntityMap<InnerEntity> entityMap = EntityMap.<InnerEntity>builder()
            .type(InnerEntity.class)
            .field(intValField)
            .field(strValField)
            .entityCreator((values, ignore) -> new InnerEntity(
                    values.get(intValField),
                    values.get(strValField)
            ))
            .build();
}