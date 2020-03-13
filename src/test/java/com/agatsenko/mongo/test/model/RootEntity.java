package com.agatsenko.mongo.test.model;

import com.agatsenko.mongo.mapper.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Value
public class RootEntity {
    private final RootEntityId id;
    private final int intValue;
    private final boolean boolAsStrCharValue;
    private final String strValue;
    private final Integer nullableIntValue;

//    private final InnerEntity innerEntity;

//    private final Set<InnerEntity> innerEntitySet;
//    private final List<InnerEntity> innerEntityList;
//    private final Collection<InnerEntity> innerEntityColl;

    @Builder
    public RootEntity(
            @NonNull RootEntityId id,
            int intValue,
            boolean boolAsStrCharValue,
            @NonNull String strValue,
            Integer nullableIntValue/*,
            @NonNull InnerEntity innerEntity,
            @NonNull Set<InnerEntity> innerEntitySet,
            @NonNull List<InnerEntity> innerEntityList,
            @NonNull Collection<InnerEntity> innerEntityColl*/
    ) {
        Check.argNotNull(id, "id");
        Check.argNotNull(strValue, "strValue");
//        Check.argNotNull(innerEntity, "innerEntity");
//        Check.argNotNullOrEmpty(innerEntitySet, "innerEntitySet");
//        Check.argNotNullOrEmpty(innerEntityList, "innerEntityList");
//        Check.argNotNullOrEmpty(innerEntityColl, "innerEntityColl");

        this.id = id;
        this.intValue = intValue;
        this.boolAsStrCharValue = boolAsStrCharValue;
        this.strValue = strValue;
        this.nullableIntValue = nullableIntValue;
//        this.innerEntity = innerEntity;
//        this.innerEntitySet = innerEntitySet;
//        this.innerEntityList = innerEntityList;
//        this.innerEntityColl = innerEntityColl;
    }
}
