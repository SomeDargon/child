package com.child.entity.article;

import com.child.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = ActivityStyle.TABLE_NAME)
public class ActivityStyle extends BaseEntity {

    final static String TABLE_NAME="activity_style";

    @Column(name = "cover")
    private String cover;
    //标题
    @Column(name = "title")
    private String title;
    //内容
    @Lob
    private String content;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "province_id")
    private Long provinceId ;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "sketch")
    private String sketch;

    @Column(name = "status")
    private Integer status;

    @Column(name = "num")
    private Integer num;
    @Column(name = "surplus_num")
    private Integer surplusNum;
    @Column(name = "report_num")
    private Integer reportNum;
    //0表示管理员 1：表示小助手
    @Column(name = "type")
    private Integer type;
    //小助手
    @Column(name = "customer_id")
    private Long customerId;


}
