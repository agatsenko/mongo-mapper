/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.types.ObjectId;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;

import com.agatsenko.mongo.mapper.EntityCodecProvider;
import com.agatsenko.mongo.mapping.InnerEntityMap;
import com.agatsenko.mongo.mapping.RootEntityMap;
import com.agatsenko.mongo.mapping.codec.KeyValueCodec;
import com.agatsenko.mongo.model.InnerEntity;
import com.agatsenko.mongo.model.KeyValue;
import com.agatsenko.mongo.model.RootEntity;

public class Tester {
    private static final int STR_MAX_LEN = 10;

    public static void main(String[] args) {
        final var dbNameProvider = new DatabaseNameProvider("test");
        final var connStr = new ConnectionString("mongodb://test:test@localhost/" + dbNameProvider.getDatabaseName());
        final var codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(
                        KeyValueCodec.instance
                ),
                CodecRegistries.fromProviders(
                        new UuidCodecProvider(UuidRepresentation.STANDARD),
                        EntityCodecProvider.builder()
                                .register(RootEntityMap.entityMap)
                                .register(InnerEntityMap.entityMap)
                                .build()
                )
        );
        final var clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connStr)
                .codecRegistry(codecRegistry)
                .build();
        try (final var client = MongoClients.create(clientSettings)) {
            final var repo = new RootEntityRepo(dbNameProvider, client);
            repo.removeAll();
            System.out.println("----------------------------------------------------");
            System.out.println("-- srcEntities");
            final var srcEntities = generateRootEntities(10);
            srcEntities.forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findAll()");
            srcEntities.forEach(repo::save);
            repo.findAll().forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findById");
            System.out.println(repo.findById(srcEntities.stream().findFirst().get().getId()));
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findByBoolAsNumStr(true)");
            repo.findByBoolAsNumStr(true).forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findByBoolAsNumStr(false)");
            repo.findByBoolAsNumStr(false).forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findByInnerEntityStrVal");
            repo.findByInnerEntityStrVal(srcEntities.stream().findAny().get().getInnerEntity().getStrVal())
                    .forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findByInnerEntitySetStrVal");
            repo.findByInnerEntitySetStrVal(
                    srcEntities
                            .stream()
                            .skip(1)
                            .findAny()
                            .get()
                            .getInnerEntitySet()
                            .stream()
                            .findAny()
                            .get()
                            .getStrVal()
            ).forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findByKeyValue");
            repo.findByKeyValue(
                    srcEntities
                            .stream()
                            .skip(2)
                            .findAny()
                            .get()
                            .getKeyValues()
                            .stream()
                            .findAny()
                            .get()
            ).forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
            System.out.println("-- findByKeyValues");
            repo.findByKeyValues(
                    srcEntities
                            .stream()
                            .skip(2)
                            .findAny()
                            .get()
                            .getKeyValues()
                            .stream()
                            .findAny()
                            .get(),
                    srcEntities
                            .stream()
                            .skip(5)
                            .findAny()
                            .get()
                            .getKeyValues()
                            .stream()
                            .findAny()
                            .get()
            ).forEach(System.out::println);
            System.out.println();
            System.out.println("----------------------------------------------------");
        }
    }

    private static boolean newBool() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    private static int newInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    private static String newStr() {
        final var len = ThreadLocalRandom.current().nextInt(1, STR_MAX_LEN + 1);
        final var sb = new StringBuilder(len);
        for (var i = 0; i < len; ++i) {
            sb.append((char) ThreadLocalRandom.current().nextInt(32, 128));
        }
        return sb.toString();
    }

    private static KeyValue generateKeyValue() {
        return new KeyValue(newInt(), newStr());
    }

    private static List<KeyValue> generateKeyValues(int count) {
        final var list = new ArrayList<KeyValue>(count);
        for (int i = 0; i < count; ++i) {
            list.add(generateKeyValue());
        }
        return list;
    }

    private static InnerEntity generateInnerEntity() {
        return new InnerEntity(newInt(), newStr());
    }

    private static <C extends Collection<InnerEntity>> C generateInnerEntities(C collection, int count) {
        for (var i = 0; i < count; ++i) {
            collection.add(generateInnerEntity());
        }
        return collection;
    }

    private static RootEntity generateRootEntity() {
        return RootEntity.builder()
                .id(UUID.randomUUID())
                .boolAsYesNoChar(newBool())
                .boolAsNumChar(newBool())
                .bool(newBool())
                .intAsStr(newInt())
                .intVal(newInt())
                .str(newStr())
                .innerEntity(generateInnerEntity())
                .innerEntitySet(generateInnerEntities(new HashSet<>(), ThreadLocalRandom.current().nextInt(1, 10)))
                .innerEntities(generateInnerEntities(new ArrayList<>(), ThreadLocalRandom.current().nextInt(1, 10)))
                .keyValues(generateKeyValues(ThreadLocalRandom.current().nextInt(1, 10)))
                .build();
    }

    private static List<RootEntity> generateRootEntities(int count) {
        final var list = new ArrayList<RootEntity>(count);
        for (int i = 0; i < count; ++i) {
            list.add(generateRootEntity());
        }
        return list;
    }
}
