/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapping.codec;

import com.agatsenko.mongo.mapper.support.DefaultValueCodec;

public class BoolAsYesNoCharValueCodec extends DefaultValueCodec<Boolean, String> {
    private static final String TRUE_VALUE = "Y";
    private static final String FALSE_VALUE = "N";

    public static final BoolAsYesNoCharValueCodec instance = new BoolAsYesNoCharValueCodec();

    private BoolAsYesNoCharValueCodec() {
        super(
                // field type
                Boolean.class,
                //toFieldConverter
                TRUE_VALUE::equalsIgnoreCase,
                // toDocValueConverter
                value -> value ? TRUE_VALUE : FALSE_VALUE,
                // docValueReader
                (reader, context) -> reader.readString(),
                // docValueWriter
                (writer, docValue, context) -> writer.writeString(docValue)
        );
    }
}
