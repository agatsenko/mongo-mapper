/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-15
 */
package com.agatsenko.mongo.mapping.codec;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.agatsenko.mongo.model.KeyValue;

public class KeyValueCodec implements Codec<KeyValue> {
    public static final KeyValueCodec instance = new KeyValueCodec();

    private KeyValueCodec() {
        // hide ctor
    }

    @Override
    public KeyValue decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            reader.readNull();
            return null;
        }
        else {
            final KeyValue keyValue;
            reader.readStartDocument();
            keyValue = new KeyValue(Integer.parseInt(reader.readName()), reader.readString());
            reader.readEndDocument();
            return keyValue;
        }
    }

    @Override
    public void encode(BsonWriter writer, KeyValue value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        }
        else {
            writer.writeStartDocument();
            writer.writeString(Integer.toString(value.getKey()), value.getValue());
            writer.writeEndDocument();
        }
    }

    @Override
    public Class<KeyValue> getEncoderClass() {
        return KeyValue.class;
    }
}
