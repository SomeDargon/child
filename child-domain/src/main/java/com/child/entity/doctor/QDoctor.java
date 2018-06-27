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
 * QDoctor is a Querydsl query type for Doctor
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDoctor extends EntityPathBase<Doctor> {

    private static final long serialVersionUID = 1660960834L;

    public static final QDoctor doctor = new QDoctor("doctor");

    public final com.child.QBaseEntity _super = new com.child.QBaseEntity(this);

    public final DateTimePath<java.util.Date> addTime = createDateTime("addTime", java.util.Date.class);

    public final StringPath department = createString("department");

    public final StringPath doctorWrote = createString("doctorWrote");

    public final StringPath goodAt = createString("goodAt");

    public final StringPath hospitalAddress = createString("hospitalAddress");

    public final StringPath icon = createString("icon");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> provinceId = createNumber("provinceId",Long.class);

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final StringPath position = createString("position");

    public final StringPath practiceExperience = createString("practiceExperience");

    public final StringPath remarks = createString("remarks");

    public final StringPath servingHospital = createString("servingHospital");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath type = createString("type");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public QDoctor(String variable) {
        super(Doctor.class, forVariable(variable));
    }

    public QDoctor(Path<? extends Doctor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDoctor(PathMetadata metadata) {
        super(Doctor.class, metadata);
    }

}

