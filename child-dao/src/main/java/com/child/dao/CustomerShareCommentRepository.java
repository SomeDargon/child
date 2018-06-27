package com.child.dao;

import com.child.entity.customer.CustomerShareComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerShareCommentRepository extends JpaRepository<CustomerShareComment,Long> {

    Page<CustomerShareComment>  findByCustomerShareId(Long customerShareId, Pageable pageable);

    List<CustomerShareComment>  findByCustomerShareIdAndReplyCustomerIdIsNull(Long customerShareId);

    Page<CustomerShareComment>  findByCustomerId(Long customerId, Pageable pageable);

    List<CustomerShareComment> findByCustomerShareIdAndReplyCustomerIdIsNotNull(Long customerShareId);

    List<CustomerShareComment> findByCustomerShareId(Long customerShareId);
}
