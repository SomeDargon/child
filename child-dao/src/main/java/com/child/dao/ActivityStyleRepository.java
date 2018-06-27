package com.child.dao;

import com.child.entity.article.ActivityStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityStyleRepository extends JpaRepository<ActivityStyle,Long>,QueryDslPredicateExecutor<ActivityStyle> {

    Page<ActivityStyle> findByTitle(String title, Pageable pageable);

    Page<ActivityStyle> findByStatus(Integer status, Pageable pageable);

}
