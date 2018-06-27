package com.child.dto;

import lombok.Data;

/**
 * Created by somedragon on 2018/3/22.
 */
@Data
public class DoctorDTO {

    private Long id;

    private String name;

    private String type;

    private Integer onLine;

    private String remarks;
}
