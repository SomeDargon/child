package com.child.entity.children;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = ChildrenGrowRecord.TABLE_NAME)
public class ChildrenGrowRecord extends BaseEntity {

    final static String TABLE_NAME="children_grow_record";

    @Column(name = "children_id")
    private Long childrenId;

    @Column(name = "height")
    private String height;

    @Column(name = "weight")
    private String weight;

    @Column(name = "head_circumference")
    private String headCircumference;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "measurement_time")
    private String measurementTime;

}
