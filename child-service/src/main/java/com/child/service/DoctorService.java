package com.child.service;


import com.child.dto.DoctorDTO;
import com.child.entity.doctor.Doctor;
import com.child.form.DoctorForm;
import com.child.vo.DoctorVO;

import java.util.List;

/**
 * Created by somedragon on 2018/3/22.
 */
public interface DoctorService {

    DoctorDTO findByDoctorId(Long id);
    Doctor save(DoctorForm doctorForm);
    Doctor findByid(Long id);

    List<DoctorVO> findAll();
}
