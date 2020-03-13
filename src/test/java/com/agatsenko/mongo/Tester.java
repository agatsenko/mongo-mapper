/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo;

import java.util.UUID;
import java.util.function.Consumer;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class Tester {
    public static void main(String[] args) {
        final var dbName = "test";
        final var codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(new FooCodec())
        );
        final var clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://test:test@localhost/" + dbName))
                .codecRegistry(codecRegistry)
                .build();
        try (final var client = MongoClients.create(clientSettings)) {
            final var db = client.getDatabase(dbName);
            final var coll = db.getCollection("Foo", Foo.class);
            coll.deleteMany(new Bson() {
                @Override
                public <TDocument> BsonDocument toBsonDocument(Class<TDocument> tDocumentClass, CodecRegistry codecRegistry) {
                    return new BsonDocument();
                }
            });
            coll.insertOne(Foo.builder()
                    .id(new ObjectId())
                    .strValue(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .build()
            );
            coll.insertOne(Foo.builder()
                    .id(new ObjectId())
                    .strValue(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .build()
            );
            coll.insertOne(Foo.builder()
                    .id(new ObjectId())
                    .strValue(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .build()
            );
            coll.insertOne(Foo.builder()
                    .id(new ObjectId())
                    .strValue(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .strSetItem(UUID.randomUUID().toString())
                    .build()
            );
            coll.find().forEach((Consumer<Foo>) System.out::println);
        }
    }
}
