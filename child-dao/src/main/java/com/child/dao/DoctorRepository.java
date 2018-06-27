package com.child.dao;

import com.child.entity.doctor.Doctor;
import com.child.json.doctor.DoctorNameJson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by somedragon on 2018/1/8.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>,QueryDslPredicateExecutor<Doctor> {

    Page<Doctor> findByName(String name, Pageable pageable);


    @Query("SELECT new com.child.json.doctor.DoctorNameJson(d.id,d.name) FROM Doctor d")
    List<DoctorNameJson>  findAllDoctorName();

}
