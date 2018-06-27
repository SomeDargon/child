package com.child.json.children;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChildrenRecordJson {

    private Long childrenId;

    @ApiModelProperty(value = "特殊病史")
    private String specialHistory;
    @ApiModelProperty(value = "过敏史")
    private String allergyHistory;
    @ApiModelProperty(value = "预产日期")
    private String expectedDate;


}
