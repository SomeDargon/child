package com.child.entity.customer;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by somedragon on 2018/1/11.
 * 用户表
 */
@Data
@Entity
public class Customer extends BaseEntity{

    /** 微信OpenId. */
    private String openId;

    private String name;

    /** 身份证. */
    private String idNumber;

    private String realName;
    // 值为1时是男性，值为2时是女性，值为0时是未知
    private String gender;

    private double money;

    private Date birthday;

    private String phone;

    private String address;

    private Integer status = 0;

    /** 简介. */
    private String remarks;

    /** 紧急联系人 */
    private String urgentName;

    /** 紧急联系方式 */
    private String urgentPhone;

    private Date createDate;

    @Column(name = "icon")
    private String icon;

    /** 就诊卡号. */
    private String medicalCard;


    /** 是否禁言**/
    private Integer isGag;

}
