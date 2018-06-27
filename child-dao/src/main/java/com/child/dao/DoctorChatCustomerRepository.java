package com.child.dao;

import com.child.entity.doctor.DoctorChatCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorChatCustomerRepository extends JpaRepository<DoctorChatCustomer,Long> {

    Page<DoctorChatCustomer>  findByDoctorConsultId(Long doctorConsultId,Pageable pageable);

    List<DoctorChatCustomer> findByUserType(Integer userType);

    List<DoctorChatCustomer> findByUserTypeAndDoctorConsultId(Integer userType,Long doctorConsultId);

    List<DoctorChatCustomer> findByUserTypeAndSystemReplyIsNullAndDoctorConsultId(Integer userType,Long doctorConsultId);

    List<DoctorChatCustomer> findByDoctorConsultId(Long id, Sort sort);
}
