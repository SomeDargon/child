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
 * QDoctorConsultPrice is a Querydsl query type for DoctorConsultPrice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDoctorConsultPrice extends EntityPathBase<DoctorConsultPrice> {

    private static final long serialVersionUID = -587884001L;

    public static final QDoctorConsultPrice doctorConsultPrice = new QDoctorConsultPrice("doctorConsultPrice");

    public final com.child.QBaseEntity _super = new com.child.QBaseEntity(this);

    public final DateTimePath<java.util.Date> addTime = createDateTime("addTime", java.util.Date.class);

    public final NumberPath<Long> doctorId = createNumber("doctorId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath price = createString("price");

    public final StringPath type = createString("type");

    public final DateTimePath<java.util.Date> updateTime = createDateTime("updateTime", java.util.Date.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QDoctorConsultPrice(String variable) {
        super(DoctorConsultPrice.class, forVariable(variable));
    }

    public QDoctorConsultPrice(Path<? extends DoctorConsultPrice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDoctorConsultPrice(PathMetadata metadata) {
        super(DoctorConsultPrice.class, metadata);
    }

}

