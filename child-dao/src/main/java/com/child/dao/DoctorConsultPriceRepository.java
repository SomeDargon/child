package com.child.dao;

import com.child.entity.doctor.DoctorConsultPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorConsultPriceRepository extends JpaRepository<DoctorConsultPrice,Long>,QueryDslPredicateExecutor<DoctorConsultPrice> {


    DoctorConsultPrice findByTypeAndDoctorId(String type, Long doctorId);

    List<DoctorConsultPrice> findByDoctorId(Long doctorId);


}
