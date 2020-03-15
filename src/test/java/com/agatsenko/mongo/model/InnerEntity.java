/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.model;

import lombok.Value;

@Value
public class InnerEntity {
    private final int intVal;
    private final String strVal;
}
