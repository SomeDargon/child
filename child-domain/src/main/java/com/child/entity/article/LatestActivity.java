package com.child.entity.article;

import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = LatestActivity.TABLE_NAME)
public class LatestActivity extends BaseEntity {

    final static String TABLE_NAME="latest_activity";

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

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "sketch")
    private String sketch;

    @Column(name = "status")
    private Integer status;

    @Column(name = "num")
    private Integer num;

    //0表示管理员 1：表示小助手
    @Column(name = "type")
    private Integer type;

    //小助手
    @Column(name = "customer_id")
    private Long customerId;

}
