package com.child.json.doctor;

import lombok.Data;

@Data
public class DoctorNameJson {

    private Long id;

    private String name;


    public DoctorNameJson(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
