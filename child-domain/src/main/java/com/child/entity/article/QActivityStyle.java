package com.child.entity.article;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QActivityStyle is a Querydsl query type for ActivityStyle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QActivityStyle extends EntityPathBase<ActivityStyle> {

    private static final long serialVersionUID = 1979508766L;

    public static final QActivityStyle activityStyle = new QActivityStyle("activityStyle");

    public final com.child.QBaseEntity _super = new com.child.QBaseEntity(this);

    public final DateTimePath<java.util.Date> addTime = createDateTime("addTime", java.util.Date.class);

    public final NumberPath<Long> cityId = createNumber("cityId", Long.class);

    public final StringPath content = createString("content");

    public final StringPath cover = createString("cover");

    public final StringPath endTime = createString("endTime");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final NumberPath<Long> provinceId = createNumber("provinceId", Long.class);

    public final StringPath sketch = createString("sketch");

    public final StringPath startTime = createString("startTime");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QActivityStyle(String variable) {
        super(ActivityStyle.class, forVariable(variable));
    }

    public QActivityStyle(Path<? extends ActivityStyle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActivityStyle(PathMetadata metadata) {
        super(ActivityStyle.class, metadata);
    }

}

