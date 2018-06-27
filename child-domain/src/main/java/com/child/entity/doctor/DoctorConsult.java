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
@Table(name = DoctorConsult.TABLE_NAME)
public class DoctorConsult extends BaseEntity {

    final static String TABLE_NAME="doctor_consult";

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "children_id")
    private Long childrenId;

    @Column(name = "doctor_bespeak_time_id")
    private Long doctorBespeakTimeId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Lob
    private String problemContent;

    @Column(name = "type")
    private Integer type;

    @Column(name = "status")
    private Integer status;

    @Column(name = "sys_type")
    private Integer sysType;

    @Lob
    private String otherSymptom;

    @Lob
    private String diagnosis;
    @Lob
    private String medicationRecord;
    @Lob
    private String checkRecord;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "visit_time")
    private Date visitTime;

    /** 目前用户 就只有3个 用户， 客户， 医生 所以做了三个 判断， 如果用户多了需要加一张临时表*/
    @Column(name = "user_status")
    private Integer userStatus;
    /** 未读0 只读1 */
    @Column(name = "admin_status")
    private Integer adminStatus;

    @Column(name = "doctor_status")
    private Integer doctorStatus;
}
