/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.model;

import lombok.Value;

@Value
public class KeyValue {
    private final int key;
    private final String value;
}
