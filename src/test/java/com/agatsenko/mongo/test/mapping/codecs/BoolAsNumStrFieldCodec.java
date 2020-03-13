package com.agatsenko.mongo.test.mapping.codecs;

import com.agatsenko.mongo.mapper.support.DefaultFieldCodec;

public class BoolAsNumStrFieldCodec extends DefaultFieldCodec<Boolean, String> {
    private static final String TRUE_VALUE = "1";
    private static final String FALSE_VALUE = "0";

    public static final BoolAsNumStrFieldCodec instance = new BoolAsNumStrFieldCodec();

    private BoolAsNumStrFieldCodec() {
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
