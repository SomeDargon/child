package com.child.dto;

import com.child.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * Created by somedragon on 2018/3/21.
 */
@Data
public class DoctorConsultDTO {

    private Long id;

    private Integer type;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createDate;

    private Integer status;
}
