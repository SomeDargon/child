package com.child.entity.doctor;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QDoctorConsult is a Querydsl query type for DoctorConsult
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDoctorConsult extends EntityPathBase<DoctorConsult> {

    private static final long serialVersionUID = -332077750L;

    public static final QDoctorConsult doctorConsult = new QDoctorConsult("doctorConsult");

    public final com.child.QBaseEntity _super = new com.child.QBaseEntity(this);

    public final DateTimePath<java.util.Date> addTime = createDateTime("addTime", java.util.Date.class);

    public final NumberPath<Long> childrenId = createNumber("childrenId", Long.class);

    public final StringPath contactPhone = createString("contactPhone");

    public final NumberPath<Long> customerId = createNumber("customerId", Long.class);

    public final NumberPath<Long> doctorBespeakTimeId = createNumber("doctorBespeakTimeId", Long.class);

    public final NumberPath<Long> doctorId = createNumber("doctorId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath problemContent = createString("problemContent");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final NumberPath<Integer> type = createNumber("type", Integer.class);
    
    public final NumberPath<Integer> sysType = createNumber("sysType", Integer.class);

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QDoctorConsult(String variable) {
        super(DoctorConsult.class, forVariable(variable));
    }

    public QDoctorConsult(Path<? extends DoctorConsult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDoctorConsult(PathMetadata metadata) {
        super(DoctorConsult.class, metadata);
    }

}

