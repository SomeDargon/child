package com.child.dao;

import com.child.entity.doctor.DoctorConsultOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorConsultOrderRepository extends JpaRepository<DoctorConsultOrder,Long> {

    DoctorConsultOrder findByOrderNumber(String orderNumber);

    DoctorConsultOrder findByDoctorConsultId(Long doctorConsultId);
}
