package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.util.Check;
import com.agatsenko.mongo.mapper.mapping.EntityMap;
import com.agatsenko.mongo.mapper.mapping.FieldMap;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class EntityCodec<TEntity> implements Codec<TEntity> {
    private final EntityMap<TEntity> entityMap;
    private final Map<String, FieldReader<TEntity, ?, ?>> docKeyToFieldReaderMap;
    private final Collection<FieldWriter<TEntity, ?, ?>> fieldWriters;

    public EntityCodec(EntityMap<TEntity> entityMap, CodecRegistry codecRegistry) {
        Check.argNotNull(entityMap, "entityMap");
        Check.argNotNull(codecRegistry, "codecRegistry");

        this.entityMap = entityMap;
        this.docKeyToFieldReaderMap = buildDocKeyToFieldReaderMap(entityMap, codecRegistry);
        this.fieldWriters = buildFieldWriters(entityMap, codecRegistry);
    }

    @Override
    public TEntity decode(BsonReader reader, DecoderContext decoderContext) {
        final var fieldValues = new DefaultEntityFieldValues<>(entityMap);
        final var readContext = new ReadContext<>(entityMap, decoderContext);
        String discriminator = null;
        reader.readStartDocument();
        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            final var docKey = reader.readName();
            if (isDiscriminator(docKey)) {
                discriminator = reader.readString();
            }
            else {
                final var fieldReader = docKeyToFieldReaderMap.get(docKey);
                if (fieldReader != null) {
                    fieldValues.putValue(fieldReader.getFieldMap(), fieldReader.read(reader, readContext));
                } else {
                    reader.skipValue();
                }
            }
        }
        reader.readEndDocument();
        return entityMap.getEntityCreator().create(fieldValues, discriminator);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void encode(BsonWriter writer, TEntity entity, EncoderContext encoderContext) {
        final var writeContext = new WriteContext<>(entityMap, encoderContext);
        writer.writeStartDocument();
        fieldWriters.forEach(fieldWriter -> writeField(writer, fieldWriter, entity, writeContext));
        writeDiscriminator(writer, writeContext);
        writer.writeEndDocument();
    }

    @Override
    public Class<TEntity> getEncoderClass() {
        return entityMap.getType();
    }

    private static <T> Map<String, FieldReader<T, ?, ?>> buildDocKeyToFieldReaderMap(
            EntityMap<T> entityMap,
            CodecRegistry codecRegistry) {
        return entityMap.getFields().stream().collect(Collectors.toUnmodifiableMap(
                FieldMap::getDocKey,
                f -> FieldReader.create(f, codecRegistry)
        ));
    }

    private static <T> Collection<FieldWriter<T, ?, ?>> buildFieldWriters(
            EntityMap<T> entityMap,
            CodecRegistry codecRegistry) {
        return entityMap.getFields().stream()
                .map(f -> FieldWriter.create(f, codecRegistry))
                .collect(Collectors.toUnmodifiableList());
    }

    private boolean isDiscriminator(String docKey) {
        return entityMap.isDiscriminatorEnabled() && entityMap.getDiscriminatorKey().equals(docKey);
    }

    private <TField> void writeField(
            BsonWriter writer,
            FieldWriter<TEntity, TField, ?> fieldWriter,
            TEntity entity,
            WriteContext<TEntity> context) {
        final var fieldMap = fieldWriter.getFieldMap();
        final var field = fieldMap.getFieldExtractor().apply(entity);
        if (field == null) {
            if (context.getEntityMap().isWriteNulls()) {
                writer.writeNull(fieldMap.getDocKey());
            }
        }
        else {
            writer.writeName(fieldMap.getDocKey());
            fieldWriter.write(writer, field, context);
        }
    }

    private void writeDiscriminator(BsonWriter writer, WriteContext<TEntity> context) {
        if (context.getEntityMap().isDiscriminatorEnabled()) {
            writer.writeString(context.getEntityMap().getDiscriminatorKey(), context.getEntityMap().getDiscriminator());
        }
    }
}
