package com.child.dao;

import com.child.entity.announcement.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long>{

    @Query("select a from Announcement a  where a.type = ?1 AND a.status = 1")
    Page<Announcement> findByType(Integer type, Pageable pageable);

}
