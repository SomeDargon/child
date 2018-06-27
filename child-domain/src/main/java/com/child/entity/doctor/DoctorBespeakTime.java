package com.child.entity.doctor;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = DoctorBespeakTime.TABLE_NAME)
public class DoctorBespeakTime extends BaseEntity {

    final static String TABLE_NAME="doctor_bespeak_time";

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "num")
    private Integer num;

    @Column(name = "time")
    private String time;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;


}
