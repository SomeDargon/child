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
 * QLatestActivity is a Querydsl query type for LatestActivity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLatestActivity extends EntityPathBase<LatestActivity> {

    private static final long serialVersionUID = 1236392698L;

    public static final QLatestActivity latestActivity = new QLatestActivity("latestActivity");

    public final com.child.QBaseEntity _super = new com.child.QBaseEntity(this);

    public final StringPath startTime = createString("startTime");

    public final StringPath endTime = createString("endTime");

    public final DateTimePath<java.util.Date> addTime = createDateTime("addTime", java.util.Date.class);

    public final StringPath content = createString("content");

    public final StringPath cover = createString("cover");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final StringPath sketch = createString("sketch");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QLatestActivity(String variable) {
        super(LatestActivity.class, forVariable(variable));
    }

    public QLatestActivity(Path<? extends LatestActivity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLatestActivity(PathMetadata metadata) {
        super(LatestActivity.class, metadata);
    }

}

