package com.agatsenko.mongo;

import com.agatsenko.mongo.mapper.util.Check;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class FooCodec implements Codec<Foo> {
    private static final Logger logger = LoggerFactory.getLogger(FooCodec.class);

    @SuppressWarnings("unchecked")
    @Override
    public Foo decode(BsonReader reader, DecoderContext decoderContext) {
        Check.state(
                reader.getCurrentBsonType() == BsonType.DOCUMENT,
                "current bson type should be %s, but it %s",
                BsonType.DOCUMENT,
                reader.getCurrentBsonType()
        );
        reader.readStartDocument();
        final var fields = new HashMap<String, Object>();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            final var name = reader.readName();
            switch (name) {
                case "_id":
                    fields.put("_id", reader.readObjectId());
                    break;
                case "strValue":
                    fields.put("strValue", reader.readString());
                    break;
                case "strSet":
                    final var strSet = new HashSet<>();
                    reader.readStartArray();
                    while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
                        strSet.add(reader.readString());
                    }
                    reader.readEndArray();
                    fields.put("strSet", strSet);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.readEndDocument();
        return new Foo((ObjectId) fields.get("_id"), (String) fields.get("strValue"), (Set<String>) fields.get("strSet"));
    }

    @Override
    public void encode(BsonWriter writer, Foo value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeObjectId("_id", value.getId());
        writer.writeString("strValue", value.getStrValue());
        writer.writeStartArray("strSet");
        value.getStrSet().forEach(writer::writeString);
        writer.writeEndArray();
        writer.writeEndDocument();
    }

    @Override
    public Class<Foo> getEncoderClass() {
        return Foo.class;
    }
}
