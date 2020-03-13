package com.agatsenko.mongo.test.mapping.codecs;

import com.agatsenko.mongo.mapper.support.DefaultFieldCodec;

public class BoolAsStrCharFieldCodec extends DefaultFieldCodec<Boolean, String> {
    private static final String TRUE_VALUE = "t";
    private static final String FALSE_VALUE = "f";

    public static final BoolAsStrCharFieldCodec instance = new BoolAsStrCharFieldCodec();

    public BoolAsStrCharFieldCodec() {
        super(
                // fieldType
                Boolean.class,
                // toFieldConverter
                docValue -> TRUE_VALUE.equals(TRUE_VALUE),
                // toDocValueConverter
                field -> field ? TRUE_VALUE : FALSE_VALUE,
                // docValueReader
                (reader, context) -> reader.readString(),
                // docValueWriter
                (writer, docValue, context) -> writer.writeString(docValue)
        );
    }
}
