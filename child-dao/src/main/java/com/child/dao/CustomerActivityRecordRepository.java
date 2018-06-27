package com.child.dao;

import com.child.entity.customer.CustomerActivityRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerActivityRecordRepository extends JpaRepository<CustomerActivityRecord,Long> {

    List<CustomerActivityRecord> findByActivityId(Long activityId);

}
