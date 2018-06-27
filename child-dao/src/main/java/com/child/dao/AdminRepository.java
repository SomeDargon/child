package com.child.dao;

import com.child.entity.admin.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Admin findByAccount(String account);

    Page<Admin> findByAccount(String account, Pageable pageable);

    @Query("select a from Admin a  where  a.status = 1")
    Page<Admin> findByStatus(Pageable pageable);

}
