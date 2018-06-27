package com.child.json.image;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class NorImageJson {

    private Long id;
    @ApiModelProperty(value = "图片类型")
    private String fileType;
    @ApiModelProperty(value = "图片原始名称")
    private String originalName;
    @ApiModelProperty(value = "图片url")
    private String norImageUrl;
    @ApiModelProperty(value = "base64编码后的图片字节")
    private String data;

    public NorImageJson() {
    }

    public NorImageJson(String NorImageUrl) {
        this.norImageUrl = NorImageUrl;
    }

    public NorImageJson(String fileType, String originalName, String data) {
        this.fileType = fileType;
        this.originalName = originalName;
        this.data = data;
    }
}
