/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.mapping.codec;

import com.agatsenko.mongo.mapper.support.DefaultValueCodec;

public class IntAsStrValueCodec extends DefaultValueCodec<Integer, String> {
    public static final IntAsStrValueCodec instance = new IntAsStrValueCodec();

    private IntAsStrValueCodec() {
        super(
                // field type
                Integer.class,
                //toFieldConverter
                Integer::parseInt,
                // toDocValueConverter
                Object::toString,
                // docValueReader
                (reader, context) -> reader.readString(),
                // docValueWriter
                (writer, docValue, context) -> writer.writeString(docValue)
        );
    }
}

