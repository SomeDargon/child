package com.child.dao;

import com.child.entity.image.NorImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by somedragon on 2018/1/8.
 */
@Repository
public interface NorImageRepository extends JpaRepository<NorImage, Long> {

    List<NorImage> findByObjectIdAndType(Long objectId, Integer type);

    int deleteByObjectIdAndType(Long objectId, Integer type);

}
