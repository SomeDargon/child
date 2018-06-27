package com.child.dao;

import com.child.entity.children.ChildrenDiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrenDiaryRepository extends JpaRepository<ChildrenDiary,Long> {


    Page<ChildrenDiary> findBychildrenId(Long childrenId, Pageable pageable);

}
