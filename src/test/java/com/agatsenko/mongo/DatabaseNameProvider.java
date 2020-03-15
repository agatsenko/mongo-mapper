/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-15
 */
package com.agatsenko.mongo;

import com.agatsenko.mongo.mapper.util.Check;

public class DatabaseNameProvider {
    private final String databaseName;

    public DatabaseNameProvider(String databaseName) {
        Check.argNotNullOrEmpty(databaseName, "databaseName");

        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
