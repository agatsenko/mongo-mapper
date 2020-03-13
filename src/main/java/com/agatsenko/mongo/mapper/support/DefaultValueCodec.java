/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper.support;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import io.vavr.Function1;
import io.vavr.Function2;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.agatsenko.mongo.mapper.model.ValueCodec;
import com.agatsenko.mongo.mapper.util.Procedure3;

@Builder
@RequiredArgsConstructor
public class DefaultValueCodec<TField, TDocValue> implements ValueCodec<TField, TDocValue> {
    @NonNull
    private final Class<TField> fieldType;
    @NonNull
    private final Function1<TDocValue, TField> toFieldConverter;
    @NonNull
    private final Function1<TField, TDocValue> toDocValueConverter;
    @NonNull
    private final Function2<BsonReader, DecoderContext, TDocValue> docValueReader;
    @NonNull
    private final Procedure3<BsonWriter, TDocValue, EncoderContext> docValueWriter;

    @Override
    public TField toValue(TDocValue docValue) {
        return toFieldConverter.apply(docValue);
    }

    @Override
    public TDocValue toDocValue(TField field) {
        return toDocValueConverter.apply(field);
    }

    @Override
    public TField decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            reader.readNull();
            return null;
        }
        else {
            return toFieldConverter.apply(docValueReader.apply(reader, decoderContext));
        }
    }

    @Override
    public void encode(BsonWriter writer, TField value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        }
        else {
            docValueWriter.apply(writer, toDocValueConverter.apply(value), encoderContext);
        }
    }

    @Override
    public Class<TField> getEncoderClass() {
        return fieldType;
    }
}
