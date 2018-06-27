package com.child.entity.customer;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = CustomerShareComment.TABLE_NAME)
public class CustomerShareComment extends BaseEntity {

    final static String TABLE_NAME="customer_share_comment";
    @Column(name = "customer_share_id")
    private Long customerShareId;
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "reply_customer_id")
    private Long replyCustomerId;
    @Column(name = "content")
    private String content;
    @Column(name = "add_time")
    private Date addTime;


}
