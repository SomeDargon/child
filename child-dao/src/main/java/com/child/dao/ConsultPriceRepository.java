package com.child.dao;

import com.child.entity.doctor.ConsultPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultPriceRepository extends JpaRepository<ConsultPrice,Long> {

    ConsultPrice findByType(Integer type);

}
