package com.child.entity.customer;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = CustomerShare.TABLE_NAME)
public class CustomerShare extends BaseEntity {

    final static String TABLE_NAME="customer_share";

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "title")
    private String title;

    @Lob
    private String content;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;


}
