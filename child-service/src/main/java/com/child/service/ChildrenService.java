package com.child.service;

import com.child.dto.ChildrentDTO;
import com.child.entity.children.Children;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Created by somedragon on 2018/3/27.
 */
public interface ChildrenService {

    Page<Children> findById(Collection<Long> ids, Pageable pageable);

    Page<ChildrentDTO> findAll(Pageable pageable, String name);

    Children getChildren(Long id);

    Page<Children> findByIdAndNameLike(Collection<Long> ids, String name, Pageable pageable);
}
