package com.child.entity.doctor;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = DoctorConsultOrder.TABLE_NAME)
public class DoctorConsultOrder extends BaseEntity {

    final static String TABLE_NAME="doctor_consult_order";

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "doctor_consult_id")
    private Long doctorConsultId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_amount")
    private BigDecimal orderAmount;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "remark")
    private String remark;

}
