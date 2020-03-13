package com.agatsenko.mongo.test.mapping.codecs;

import com.agatsenko.mongo.test.model.RootEntityId;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class RootEntityIdCodec implements Codec<RootEntityId> {
    public static final RootEntityIdCodec instance = new RootEntityIdCodec();

    private RootEntityIdCodec() {
        // hide ctor
    }

    @Override
    public RootEntityId decode(BsonReader reader, DecoderContext decoderContext) {
        if (reader.getCurrentBsonType() == BsonType.NULL) {
            reader.readNull();
            return null;
        } else {
            return new RootEntityId(reader.readString());
        }
    }

    @Override
    public void encode(BsonWriter writer, RootEntityId value, EncoderContext encoderContext) {
        if (value == null) {
            writer.writeNull();
        } else {
            writer.writeString(value.getValue());
        }
    }

    @Override
    public Class<RootEntityId> getEncoderClass() {
        return RootEntityId.class;
    }
}
