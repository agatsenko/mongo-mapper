/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public final class DefaultFieldCodec<TField, TDocValue> implements FieldCodec<TField, TDocValue> {
    @NonNull
    private final Class<TField> fieldType;
    @NonNull
    private final Function1<TDocValue, TField> toFieldConverter;
    @NonNull
    private final Function1<TField, TDocValue> toDocValueConverter;
    @NonNull
    private final Function2<BsonReader, DecoderContext, TDocValue> docValueReader;
    @NonNull
    private final Function3<BsonWriter, TDocValue, EncoderContext, Void> docValueWriter;

    @Override
    public TField toField(TDocValue docValue) {
        return toFieldConverter.apply(docValue);
    }

    @Override
    public TDocValue toDocValue(TField field) {
        return toDocValueConverter.apply(field);
    }

    @Override
    public TField decode(BsonReader reader, DecoderContext decoderContext) {
        return toFieldConverter.apply(docValueReader.apply(reader, decoderContext));
    }

    @Override
    public void encode(BsonWriter writer, TField value, EncoderContext encoderContext) {
        docValueWriter.apply(writer, toDocValueConverter.apply(value), encoderContext);
    }

    @Override
    public Class<TField> getEncoderClass() {
        return fieldType;
    }
}
