package com.child.dao;

import com.child.entity.doctor.DoctorBespeakTime;
import com.child.entity.doctor.DoctorConsultPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorBespeakTimeRepository extends JpaRepository<DoctorBespeakTime,Long> {

    Page<DoctorBespeakTime> findByDoctorId(Long doctorId, Pageable pageable);

}
