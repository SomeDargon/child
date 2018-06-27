package com.child.dao;

import com.child.entity.children.Children;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ChildrenRepository extends JpaRepository<Children,Long> {

    Page<Children> findByCustomerIdAndStatus(Long customerId, Pageable pageable,Integer status);

    List<Children> findByStatus(Sort sort , Integer status);

    Page<Children> findByNameLike(String name, Pageable pageable);

    List<Children> findByCustomerIdAndStatus(Long customerId,Integer status);

    Page<Children> findByIdIn(Collection<Long> customerId, Pageable pageable);

    Page<Children> findByIdInAndNameLike(Collection<Long> customerId, String name, Pageable pageable);
}
