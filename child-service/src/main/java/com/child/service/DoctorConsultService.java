package com.child.service;

import com.child.dto.ChildrentDTO;
import com.child.dto.DoctorConsultDTO;
import com.child.dto.ServiceNumDTO;
import com.child.entity.doctor.DoctorConsult;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * Created by somedragon on 2018/3/21.
 */
public interface DoctorConsultService {

    Page<DoctorConsultDTO> findByDoctorId(Pageable pageable, Long doctorId);

    ServiceNumDTO getServiceNum(Long id, String date);

    Page<ChildrentDTO> getChildrent(Pageable pageable, Long doctorId, String name);

}
