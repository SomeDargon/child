package com.child.entity.customer;

import com.child.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = CustomerActivityRecord.TABLE_NAME)
public class CustomerActivityRecord extends BaseEntity {

    static final String TABLE_NAME="customer_activity_record";

    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "activity_id")
    private Long activityId;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "name")
    private String name;
    @Column(name = "num")
    private Integer num;
    //添加时间
    @Column(name = "add_time")
    private Date addTime;

}
