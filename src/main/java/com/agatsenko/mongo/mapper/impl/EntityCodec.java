package com.agatsenko.mongo.mapper.impl;

import com.agatsenko.mongo.mapper.util.Check;
import com.agatsenko.mongo.mapper.model.EntityMap;
import com.agatsenko.mongo.mapper.model.FieldMap;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public final class EntityCodec<TEntity> implements Codec<TEntity> {
    private final EntityMap<TEntity> entityMap;
    private final CodecRegistry codecRegistry;
    private final Map<String, FieldReader<TEntity, ?, ?>> docKeyToFieldReaderMap;
    private final Collection<FieldWriter<TEntity, ?, ?>> fieldWriters;

    public EntityCodec(EntityMap<TEntity> entityMap, CodecRegistry codecRegistry) {
        Check.argNotNull(entityMap, "entityMap");
        Check.argNotNull(codecRegistry, "codecRegistry");

        this.entityMap = entityMap;
        this.codecRegistry = codecRegistry;
        this.docKeyToFieldReaderMap = buildDocKeyToFieldReaderMap(entityMap);
        this.fieldWriters = buildFieldWriters(entityMap, codecRegistry);
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

    private static <T> Map<String, FieldReader<T, ?, ?>> buildDocKeyToFieldReaderMap(EntityMap<T> entityMap) {
        return entityMap.getFields().stream().collect(Collectors.toUnmodifiableMap(
                FieldMap::getDocKey,
                FieldReader::create
        ));
    }

    private static <T> Collection<FieldWriter<T, ?, ?>> buildFieldWriters(
            EntityMap<T> entityMap,
            CodecRegistry codecRegistry) {
        return entityMap.getFields().stream()
                .map(f -> FieldWriter.create(f, codecRegistry))
                .collect(Collectors.toUnmodifiableList());
    }

    private boolean readDiscriminator(BsonReader reader, String docKey, ReadContext<TEntity> context) {
        if (!entityMap.isDiscriminatorEnabled() || !entityMap.getDiscriminatorKey().equals(docKey)) {
            return false;
        }
        context.setDiscriminator(reader.readString());
        return true;
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
