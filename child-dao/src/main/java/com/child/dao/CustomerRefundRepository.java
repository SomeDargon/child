package com.child.dao;

import com.child.entity.customer.CustomerRefund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRefundRepository extends JpaRepository<CustomerRefund,Long> {

    List<CustomerRefund> findByRemarkIsNull();

}
