/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-13
 */
package com.agatsenko.mongo.mapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import com.agatsenko.mongo.mapper.impl.EntityCodec;
import com.agatsenko.mongo.mapper.mapping.EntityMap;
import com.agatsenko.mongo.mapper.util.Check;

public final class EntityCodecProvider implements CodecProvider {
    private final Map<Class<?>, EntityMap<?>> entityMaps;

    public EntityCodecProvider(Collection<EntityMap<?>> entityMaps) {
        this.entityMaps = entityMaps.stream().collect(Collectors.toUnmodifiableMap(EntityMap::getType, m -> m));
    }

    public static Builder builder() {
        return new Builder();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry codecRegistry) {
        return (Codec<T>) new EntityCodec<>(entityMaps.get(clazz), codecRegistry);
    }

    public static final class Builder {
        private final Set<EntityMap<?>> entityMaps = new HashSet<>();

        private Builder() {
            // hide ctor
        }

        public Builder register(EntityMap<?>... maps) {
            Check.argNotNull(maps, "maps");
            for (final var map : maps) {
                if (map == null) {
                    continue;
                }
                entityMaps.add(map);
            }
            return this;
        }

        public Builder register(Collection<EntityMap<?>> maps) {
            Check.argNotNull(maps, "maps");
            for (final var map : maps) {
                if (map == null) {
                    continue;
                }
                entityMaps.add(map);
            }
            return this;
        }

        public EntityCodecProvider build() {
            return new EntityCodecProvider(entityMaps);
        }
    }
}
