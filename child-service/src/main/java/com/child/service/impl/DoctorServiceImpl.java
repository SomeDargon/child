package com.child.service.impl;

import com.child.dao.DoctorRepository;
import com.child.dto.DoctorDTO;
import com.child.entity.doctor.Doctor;
import com.child.form.DoctorForm;
import com.child.service.DoctorService;
import com.child.vo.DoctorVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by somedragon on 2018/3/22.
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorDTO findByDoctorId(Long id) {
        DoctorDTO doctorDTO = new DoctorDTO();
        Doctor doctor = doctorRepository.findOne(id);
        BeanUtils.copyProperties(doctor, doctorDTO);
        return doctorDTO;
    }

    @Override
    public Doctor findByid(Long id) {
        return doctorRepository.findOne(id);
    }

    @Override
    public List<DoctorVO> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorVO> doctorVOs = new ArrayList<>();
        doctors.forEach(e->{
            DoctorVO doctorVO = new DoctorVO();
            doctorVO.setId(e.getId());
            doctorVO.setName(e.getName());
            doctorVOs.add(doctorVO);
        });
        return doctorVOs;
    }


    @Override
    public Doctor save(DoctorForm doctorForm) {
        Doctor doctor = doctorRepository.findOne(doctorForm.getId());
        doctor.setOnLine(doctorForm.getOnline());
        doctor.setType(doctorForm.getType());
        return doctorRepository.save(doctor);
    }
}
