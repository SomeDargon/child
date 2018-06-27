package com.child.json.children;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChildrenHealthRecordJson {
    private Long id;
    @ApiModelProperty(value = "宝宝id")
    private Long childrenId;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "就诊日期")
    private Date visitTime;
    @ApiModelProperty(value = "用药记录")
    private String medicationRecord;
    @ApiModelProperty(value = "检查结果")
    private String checkRecord;
    @ApiModelProperty(value = "诊断")
    private String diagnosis;
    @ApiModelProperty(value = "用药记录图片")
    private List<NorImageJson> medicationRecordJsonList;

    @ApiModelProperty(value = "检查结果图片")
    private List<NorImageJson> checkRecordJsonList;


}
