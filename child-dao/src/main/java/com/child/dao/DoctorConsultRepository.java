package com.child.dao;

import com.child.entity.doctor.DoctorConsult;
import com.child.json.doctor.DoctorConsultResponseJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DoctorConsultRepository extends JpaRepository<DoctorConsult,Long>,QueryDslPredicateExecutor<DoctorConsult> {

    @Query("select new com.child.json.doctor.DoctorConsultResponseJson(dc.id,dc.childrenId,d.id,d.name,d.phone,d.position,d.department,d.icon,d.servingHospital,d.hospitalAddress,dc.type,dc.contactPhone,dc.addTime,dc.status,dc.doctorBespeakTimeId) from DoctorConsult dc ,  Doctor d where dc.doctorId = d.id  and dc.id = ?1")
    DoctorConsultResponseJson getChildrenHealthRecordById(Long id);

    List<DoctorConsult> findByDoctorBespeakTimeId(Long id);

    Page<DoctorConsult> findByCustomerId(Long customerId ,Pageable pageable);

    List<DoctorConsult> findByStatus(Integer status);

    List<DoctorConsult> findByCustomerId(Long customerId);

    List<DoctorConsult> findByCustomerIdAndType(Long customerId,Integer type);
    Page<DoctorConsult> findByDoctorId(Long doctorId, Pageable pageable);
    @Query("select count(d) from DoctorConsult d where d.doctorId=?1 AND to_days(d.addTime)=to_days(?2) AND d.type=?3")
    Integer countByCustomerIdAndAddTimeAndType(Long id, Date date, Integer type);

    @Query("select count(d) from DoctorConsult d where d.doctorId=?1  AND d.type=?2")
    Integer countByCustomerIdAndAndType(Long id,  Integer type);
    List<DoctorConsult> findByDoctorId(Long doctorId);

}
