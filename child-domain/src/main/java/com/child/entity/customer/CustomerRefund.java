package com.child.entity.customer;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class CustomerRefund extends BaseEntity {

    final static String TABLE_NAME="customer_refund";

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "doctor_consult_id")
    private Long doctorConsultId;

    @Column(name = "doctor_consult_order_id")
    private Long doctorConsultOrderId;

    @Column(name = "orderNumber")
    private String orderNumber;

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "out_trade_no")
    private String outTradeNo;

    @Column(name = "out_refund_no")
    private String outRefundNo;

    @Column(name = "out_trade_time")
    private Date outTradeTime;

    @Column(name = "out_trade_amount")
    private String outTradeAmount;

    @Column(name = "remark")
    private String remark;


}
