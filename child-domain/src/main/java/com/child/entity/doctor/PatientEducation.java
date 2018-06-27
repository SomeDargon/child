package com.child.entity.doctor;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = PatientEducation.TABLE_NAME)
public class PatientEducation extends BaseEntity {

    final static String TABLE_NAME="patient_education";

    @Column(name = "user_id")
    private Long userId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "title")
    private String title;
    @Column(name = "resource")
    private String resource;
    @Column(name = "sketch")
    private String sketch;
    @Column(name = "link_address")
    private String linkAddress;
    @Lob
    private String details;
    @Column(name = "add_time")
    private Date addTime;
    //0 待审核 ， 1审核通过 ， 2审核不通过
    @Column(name = "status")
    private Integer status;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "not_pass_remarks")
    private String notPassRemarks;
    //发送人类型 用户0 , 医生1, 助手2
    @Column(name = "send_type")
    private Integer sendType;


}
