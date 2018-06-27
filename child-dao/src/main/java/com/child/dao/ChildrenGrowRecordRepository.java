package com.child.dao;

import com.child.entity.children.ChildrenGrowRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrenGrowRecordRepository extends JpaRepository<ChildrenGrowRecord,Long> {

    Page<ChildrenGrowRecord> findByChildrenId(Long childrenId, Pageable pageable);

    ChildrenGrowRecord findByMeasurementTimeAndChildrenId(String measurementTime,Long childrenId);

}
