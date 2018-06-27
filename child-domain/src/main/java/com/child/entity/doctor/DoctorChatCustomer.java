package com.child.entity.doctor;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = DoctorChatCustomer.TABLE_NAME)
public class DoctorChatCustomer extends BaseEntity {

    final static String TABLE_NAME="doctor_chat_customer";

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "children_id")
    private Long childrenId;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "doctor_consult_id")
    private Long doctorConsultId;

    @Column(name = "type")
    private Integer type;

    @Column(name = "content")
    private String content;

    @Column(name = "pic")
    private String pic;

    @Column(name = "status")
    private Integer status;

    @Column(name = "user_type")
    private Integer userType;

    @Column(name = "system_reply")
    private Integer systemReply;

    @Column(name = "add_time")
    private Date addTime;

}
