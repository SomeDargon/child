package com.child.dao;

import com.child.entity.children.ChildrenRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrenHealthRecordRepository extends JpaRepository<ChildrenRecord,Long> {

    ChildrenRecord findByChildrenId(Long childrenId);

}
