/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-13
 */
package com.agatsenko.mongo;

import org.bson.types.ObjectId;

import com.agatsenko.mongo.mapper.model.EntityMap;
import com.agatsenko.mongo.mapper.model.FieldMap;
import com.agatsenko.mongo.mapper.model.FieldValueMap;

public class FooMapper {
    public static final FieldMap<Foo, ObjectId, ObjectId> idField = FieldValueMap.<Foo, ObjectId>directBuilder()
            .fieldName("id")
            .docKey("_id")
            .fieldType(ObjectId.class)
            .fieldExtractor(Foo::getId)
            .build();

    public static final FieldMap<Foo, String, String> strValueField = FieldValueMap.<Foo, String>directBuilder()
            .fieldName("strValue")
            .fieldType(String.class)
            .fieldExtractor(Foo::getStrValue)
            .build();

    public static final EntityMap<Foo> entityMap = EntityMap.<Foo>builder()
            .type(Foo.class)
            .entityCreator((fields, ignore) -> new Foo(
                    fields.get(idField),
                    fields.get(strValueField),
                    null
            ))
            .field(idField)
            .field(strValueField)
            .build();
}
