package com.agatsenko.mongo.mapper_.impl;

import com.agatsenko.mongo.mapper.util.Check;
import com.agatsenko.mongo.mapper_.model.EntityMap;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Map;

public class EntityCodec<TEntity> implements Codec<TEntity> {
    private final EntityMap<TEntity> entityMap;
    private final CodecRegistry codecRegistry;
    private final Map<String, FieldReader<TEntity, ?, ?>> docKeyToFieldReaderMap;

    public EntityCodec(EntityMap<TEntity> entityMap, CodecRegistry codecRegistry) {
        Check.argNotNull(entityMap, "entityMap");
        Check.argNotNull(codecRegistry, "codecRegistry");

        this.entityMap = entityMap;
        this.codecRegistry = codecRegistry;
        this.docKeyToFieldReaderMap = buildDocKeyToFieldReaderMap(entityMap);
    }

    @Override
    public TEntity decode(BsonReader reader, DecoderContext decoderContext) {
        final var fieldValues = new DefaultEntityFieldValues<>(entityMap);
        reader.readStartDocument();
        final var readContext = new ReadContext<>(entityMap, codecRegistry, decoderContext);
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            final var docKey = reader.readName();
            if (!readDiscriminator(reader, docKey, readContext)) {
                final var fieldReader = docKeyToFieldReaderMap.get(docKey);
                if (fieldReader != null) {
                    fieldValues.putValue(fieldReader.getFieldMap(), fieldReader.read(reader, readContext));
                } else {
                    reader.skipValue();
                }
            }
        }
        reader.readEndDocument();
        return entityMap.getEntityCreator().create(fieldValues, readContext.getDiscriminator());
    }

    @Override
    public void encode(BsonWriter writer, TEntity value, EncoderContext encoderContext) {
        // FIXME: not implemented
        throw new IllegalStateException("not yet implemented");
    }

    @Override
    public Class<TEntity> getEncoderClass() {
        return entityMap.getType();
    }

    private static <T> Map<String, FieldReader<T, ?, ?>> buildDocKeyToFieldReaderMap(EntityMap<T> entityMap) {
        // FIXME: not implemented
        throw new IllegalStateException("not yet implemented");
    }

    private boolean readDiscriminator(BsonReader reader, String docKey, ReadContext<TEntity> context) {
        if (!entityMap.isDiscriminatorEnabled() || !entityMap.getDiscriminatorKey().equals(docKey)) {
            return false;
        }
        context.setDiscriminator(reader.readString());
        return true;
    }
}
