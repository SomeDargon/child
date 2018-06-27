package com.child.json.children;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ChildrenJson {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "头像")
    private String icon;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "是否为当前宝宝 1 是 2 不是")
    private Integer currentChildren;
    @ApiModelProperty(value = "性别 1 男 2 女")
    private Integer sex;
    @ApiModelProperty(value = "出生日期")
    private String birthday;
    @ApiModelProperty(value = "年龄")
    private String age;
    @ApiModelProperty(value = "头像上传数据,文件类型。如果没有修改就不传数据")
    private List<NorImageJson> imageJsonList;


}
