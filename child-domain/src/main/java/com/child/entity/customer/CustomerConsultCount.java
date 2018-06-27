package com.child.entity.customer;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = CustomerConsultCount.TABLE_NAME)
public class CustomerConsultCount extends BaseEntity {

    final static String TABLE_NAME="customer_consult_count";
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "type")
    private Integer type;
    @Column(name = "count")
    private Integer count;

}
