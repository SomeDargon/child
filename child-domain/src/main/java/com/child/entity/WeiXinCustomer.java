package com.child.entity;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by somedragon on 2018/3/20.
 * 登录公众号的用户
 */
@Data
@Entity
public class WeiXinCustomer extends BaseEntity {
    /** 微信OpenId. */
    private String openId;

    private String nickname;

    // 值为1时是男性，值为2时是女性，值为0时是未知
    private String sex;

    private String country;

    private String province;

    private String city;

    private Integer status = 0;

    private String unionid;

    private Date createDate;

    private String headimgurl;

    private Long doctorId;

    /** 角色 0：普通用户 1:医生 2:助手**/
    private Integer type;
}
