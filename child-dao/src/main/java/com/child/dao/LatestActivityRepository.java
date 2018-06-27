package com.child.dao;

import com.child.entity.article.LatestActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LatestActivityRepository extends JpaRepository<LatestActivity,Long>,QueryDslPredicateExecutor<LatestActivity> {

    List<LatestActivity> findByStatus(Integer status);

}
