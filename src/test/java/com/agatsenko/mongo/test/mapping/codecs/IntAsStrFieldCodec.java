package com.agatsenko.mongo.test.mapping.codecs;

import com.agatsenko.mongo.mapper.support.DefaultFieldCodec;

public class IntAsStrFieldCodec extends DefaultFieldCodec<Integer, String> {
    public static final IntAsStrFieldCodec instance = new IntAsStrFieldCodec();

    private IntAsStrFieldCodec() {
        super(
                // fieldType
                Integer.class,
                // toFieldConverter
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
