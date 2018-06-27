package com.child.dao;

import com.child.entity.customer.CustomerConsultCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerConsultCountRepository extends JpaRepository<CustomerConsultCount,Long> {

    CustomerConsultCount findByCustomerIdAndType(Long customerId,Integer type);

}
