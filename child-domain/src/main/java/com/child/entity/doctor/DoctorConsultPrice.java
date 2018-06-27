package com.child.entity.doctor;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = DoctorConsultPrice.TABLE_NAME)
public class DoctorConsultPrice extends BaseEntity {

    final static String TABLE_NAME="doctor_consult_price";

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type")
    private String type;

    @Column(name = "price")
    private String price;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;



}
