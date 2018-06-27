package com.child.entity.doctor;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * 医生
 * Created by somedragon on 2018/1/8.
 */
@Entity
@Data
public class Doctor extends BaseEntity{

    private String name;

    private String phone;

    /** 简介. */
    private String remarks;

    /** 职位. */
    private String position;

    private String department;

    @Column(name = "icon")
    private String icon;

    @Column(name = "good_at")
    private String goodAt;
    /** 执业经历. */
    private String practiceExperience;

    /** 医生寄语. */
    private String doctorWrote;

    /** 任职医院. */
    private String servingHospital;

    /** 医院地址. */
    private String hospitalAddress;

    @Column(name = "province_id")
    private Long provinceId ;

    private String provinceName ;

    private String type;

    @Column(name = "add_time")
    private Date addTime;
    @Column(name = "update_time")
    private Date updateTime;
    // 1. 未删除 2. 删除
    @Column(name = "status")
    private Integer status;

    @Column(name = "on_line")
    private Integer onLine;


}
