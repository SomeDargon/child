package com.child.entity.children;

import com.child.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name =Children.TABLE_NAME )
@Data
public class Children extends BaseEntity {

    final static String TABLE_NAME="children";

    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "icon")
    private String icon;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private Integer sex;
    @Column(name = "birthday")
    private String birthday;
    @Column(name = "add_time")
    private Date addTime;
    @Column(name = "update_time")
    private Date updateTime;
    // 1. 未删除 2. 删除
    @Column(name = "status")
    private Integer status;
    @Column(name = "current_children")
    private Integer currentChildren;

}
