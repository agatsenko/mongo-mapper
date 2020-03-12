/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-12
 */
package com.agatsenko.mongo.mapper;

import java.util.function.Consumer;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;

public class Tester {
    public static void main(String[] args) {
        final var dbName = "test";
        final var clientSettings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://test:test@localhost/" + dbName))
                .build();
        try (final var client = MongoClients.create(clientSettings)) {
            final var db = client.getDatabase(dbName);
            db.listCollectionNames().forEach((Consumer<String>) System.out::println);
        }
    }
}
