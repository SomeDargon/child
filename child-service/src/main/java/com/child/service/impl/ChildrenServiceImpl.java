package com.child.service.impl;

import com.child.common.utils.date.DatetimeUtil;
import com.child.dao.ChildrenRepository;
import com.child.dto.ChildrentDTO;
import com.child.entity.children.Children;
import com.child.service.ChildrenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
/**
 * Created by somedragon on 2018/3/27.
 */
@Service
public class ChildrenServiceImpl implements ChildrenService {

    @Autowired
    private ChildrenRepository childrenRepository;

    @Override
    public Page<ChildrentDTO> findAll(Pageable pageable, String name ) {
        Page<Children> childrens = null;
        if(name!=null&&!name.equals("")){
            name = "%"+name+"%";
            childrens = childrenRepository.findByNameLike( name, pageable);
        }else {
            childrens = childrenRepository.findAll( pageable);
        }

        List<ChildrentDTO> childrentDTOs = new ArrayList<>();
        childrens.getContent().forEach(e ->{
            childrentDTOs.add(setChildrent(e));
        });
        return new PageImpl<ChildrentDTO>(childrentDTOs, pageable, childrens.getTotalPages());
    }

    @Override
    public Children getChildren(Long id) {
        return childrenRepository.findOne(id);
    }

    @Override
    public Page<Children> findByIdAndNameLike(Collection<Long> ids, String name, Pageable pageable) {
        return childrenRepository.findByIdInAndNameLike(ids, name, pageable);
    }

    @Override
    public Page<Children> findById(Collection<Long> ids, Pageable pageable) {
        return childrenRepository.findByIdIn(ids, pageable);
    }
    public ChildrentDTO setChildrent(Children children){
        ChildrentDTO childrentDTO =  new ChildrentDTO();
        BeanUtils.copyProperties(children, childrentDTO);
        childrentDTO.setSex(children.getSex().equals(1)?"男":"女");
        int day = DatetimeUtil.daySpan(new Date(), DatetimeUtil.parseDateWithException(children.getBirthday()));
        int mine = day/32+1;
        childrentDTO.setAge(mine);
        return childrentDTO;
    }
}
