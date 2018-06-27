package com.child.entity.children;

import com.child.BaseEntity;
import com.sun.xml.internal.rngom.parse.host.Base;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = ChildrenDiary.TABLE_NAME )
public class ChildrenDiary extends BaseEntity{

    final static String TABLE_NAME="children_diary";

    @Column(name = "children_id")
    private Long childrenId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "address")
    private String address;


}
