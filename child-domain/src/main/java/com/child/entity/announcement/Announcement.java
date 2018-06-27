package com.child.entity.announcement;

import com.child.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = Announcement.TABLE_NAME)
public class Announcement extends BaseEntity {
    final static String TABLE_NAME="announcement";

    @Column(name = "type")
    private Integer type;
    //标题
    @Column(name = "title")
    private String title;
    //内容
    @Column(name = "content")
    private String content;
    @Column(name = "sketch")
    private String sketch;
    @Column(name = "number")
    private Long number;
    //添加时间
    @Column(name = "user_id")
    private Long userId;
    //添加时间
    @Column(name = "add_time")
    private Date addTime;
    @Column(name = "activity_time")
    private Date activityTime;;
    // 1. 未删除 2. 删除
    @Column(name = "status")
    private Integer status;

}
