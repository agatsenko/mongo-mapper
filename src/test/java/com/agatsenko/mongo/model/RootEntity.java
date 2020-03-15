/**
 * Author: Alexander Gatsenko (alexandr.gatsenko@gmail.com)
 * Created: 2020-03-14
 */
package com.agatsenko.mongo.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bson.types.ObjectId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Builder
@AllArgsConstructor
@Value
public class RootEntity {
    private final UUID id;
    private final boolean boolAsYesNoChar;
    private final boolean boolAsNumChar;
    private final boolean bool;
    private final int intAsStr;
    private final int intVal;
    private final String str;
    private final InnerEntity innerEntity;
    private final Set<InnerEntity> innerEntitySet;
    private final Collection<InnerEntity> innerEntities;
    private final List<KeyValue> keyValues;
}
