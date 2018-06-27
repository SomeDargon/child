package com.child.entity.children;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = ChildrenRecord.TABLE_NAME )
@Data
public class ChildrenRecord extends BaseEntity{

    final static String TABLE_NAME="children_record";

    @Column(name = "children_id")
    private Long childrenId;

    @Lob
    private String specialHistory;
    @Lob
    private String allergyHistory;

    @Column(name = "expected_date")
    private String expectedDate;



}
