package com.child.entity.doctor;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = ConsultPrice.TABLE_NAME)
public class ConsultPrice extends BaseEntity {

    final static String TABLE_NAME="consult_price";

    @Column(name = "type")
    private Integer type;

    @Column(name = "price")
    private String price;

}
