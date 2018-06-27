package com.child.json.customer;

import com.child.json.image.NorImageJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.Date;
import java.util.List;

@Data
public class CustomerShareJson {

    private Long id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "内容")
    private String content;
    @ApiModelProperty(value = "图片")
    private List<NorImageJson> imageJsonList;






}
