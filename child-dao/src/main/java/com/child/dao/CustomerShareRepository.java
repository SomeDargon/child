package com.child.dao;

import com.child.entity.customer.CustomerShare;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerShareRepository extends JpaRepository<CustomerShare,Long> {

    Page<CustomerShare> findByTitle(String title, Pageable pageable);

}
