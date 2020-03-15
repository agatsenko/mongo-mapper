/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapping.codec;

import com.agatsenko.mongo.mapper.support.DefaultValueCodec;

public class BoolAsNumCharValueCodec extends DefaultValueCodec<Boolean, String> {
    private static final String TRUE_VALUE = "1";
    private static final String FALSE_VALUE = "0";

    public static final BoolAsNumCharValueCodec instance = new BoolAsNumCharValueCodec();

    private BoolAsNumCharValueCodec() {
        super(
                // field type
                Boolean.class,
                //toFieldConverter
                TRUE_VALUE::equals,
                // toDocValueConverter
                value -> value ? TRUE_VALUE : FALSE_VALUE,
                // docValueReader
                (reader, context) -> reader.readString(),
                // docValueWriter
                (writer, docValue, context) -> writer.writeString(docValue)
        );
    }
}

