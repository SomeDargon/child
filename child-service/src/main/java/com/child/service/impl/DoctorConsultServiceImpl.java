package com.child.service.impl;

import com.child.common.utils.date.DatetimeUtil;
import com.child.dao.ConsultPriceRepository;
import com.child.dao.DoctorConsultPriceRepository;
import com.child.dao.DoctorConsultRepository;
import com.child.dto.ChildrentDTO;
import com.child.dto.DoctorConsultDTO;
import com.child.dto.ServiceNumDTO;
import com.child.entity.children.Children;
import com.child.entity.doctor.ConsultPrice;
import com.child.entity.doctor.DoctorConsult;
import com.child.entity.doctor.DoctorConsultPrice;
import com.child.service.ChildrenService;
import com.child.service.DoctorConsultService;
import com.sun.tools.javac.util.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by somedragon on 2018/3/21.
 */
@Service
public class DoctorConsultServiceImpl implements DoctorConsultService {

    @Autowired
    private DoctorConsultRepository doctorConsultRepository;

    @Autowired
    private ChildrenService childrenService;

    @Autowired
    private DoctorConsultPriceRepository doctorConsultPriceRepository;
    @Autowired
    private ConsultPriceRepository consultPriceRepository;

    @Override
    public Page<DoctorConsultDTO> findByDoctorId(Pageable pageable, final Long doctorId) {
        Page<DoctorConsult> doctorConsults = null;
        if(doctorId==null){
            doctorConsults = doctorConsultRepository.findAll( pageable);
        }else {
            doctorConsults = doctorConsultRepository.findByDoctorId(doctorId, pageable);
        }
        List<DoctorConsult> doctorConsultList = doctorConsults.getContent();
        List<DoctorConsultDTO> list = new ArrayList<>();
        for(DoctorConsult doctorConsult:doctorConsultList){
            DoctorConsultDTO d = new DoctorConsultDTO();
            d.setStatus(doctorConsult.getStatus());
            d.setId(doctorConsult.getId());
            d.setCreateDate(doctorConsult.getVisitTime());
            d.setType(doctorConsult.getType());
            list.add(d);
        }
        return new PageImpl<DoctorConsultDTO>(list, pageable, doctorConsults.getTotalPages());
    }

    @Override
    public ServiceNumDTO getServiceNum(Long id, String d) {
        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM");
        Date date = null;
        try {
            if(d!=null&&!d.equals("")) {
                date = sb.parse(d);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ServiceNumDTO serviceNumDTO = new ServiceNumDTO();
        if(date==null){
            serviceNumDTO.setOnlineNum(doctorConsultRepository.countByCustomerIdAndAndType(id,1));
            serviceNumDTO.setPhoneNum(doctorConsultRepository.countByCustomerIdAndAndType(id, 2));
            serviceNumDTO.setVideoNum(doctorConsultRepository.countByCustomerIdAndAndType(id,3));
        }else{
            serviceNumDTO.setOnlineNum(doctorConsultRepository.countByCustomerIdAndAddTimeAndType(id, date, 1));
            serviceNumDTO.setPhoneNum(doctorConsultRepository.countByCustomerIdAndAddTimeAndType(id, date, 2));
            serviceNumDTO.setVideoNum(doctorConsultRepository.countByCustomerIdAndAddTimeAndType(id, date, 3));
        }
        DoctorConsultPrice onlinePrice = doctorConsultPriceRepository.findByTypeAndDoctorId("1", id);
        if(onlinePrice==null){
            serviceNumDTO.setOnlinePrice(Double.valueOf(consultPriceRepository.findByType(1).getPrice()));
        }else{
            serviceNumDTO.setOnlinePrice(Double.valueOf(onlinePrice.getPrice()));
        }

        DoctorConsultPrice phonePrice = doctorConsultPriceRepository.findByTypeAndDoctorId("2", id);
        if(phonePrice==null){
            serviceNumDTO.setPhonePrice(Double.valueOf(consultPriceRepository.findByType(2).getPrice()));
        }else{
            serviceNumDTO.setPhonePrice(Double.valueOf(phonePrice.getPrice()));
        }
        DoctorConsultPrice videoPrice = doctorConsultPriceRepository.findByTypeAndDoctorId("3", id);
        if(videoPrice==null){
            serviceNumDTO.setVideoPrice(Double.valueOf(consultPriceRepository.findByType(3).getPrice()));
        }else{
            serviceNumDTO.setVideoPrice(Double.valueOf(videoPrice.getPrice()));
        }

        serviceNumDTO.setTotal(
                serviceNumDTO.getOnlinePrice()*serviceNumDTO.getOnlineNum() +
                serviceNumDTO.getPhoneNum()*serviceNumDTO.getPhonePrice() +
                serviceNumDTO.getVideoNum()*serviceNumDTO.getVideoPrice()
        );

        return serviceNumDTO;
    }

    @Override
    public Page<ChildrentDTO> getChildrent(Pageable pageable, Long doctorId, String name) {
        List<DoctorConsult> doctorConsultList = doctorConsultRepository.findByDoctorId(doctorId);
        Map<Long, Object> map = new HashMap<>();
        for (DoctorConsult doctorConsult:doctorConsultList){
            map.put(doctorConsult.getChildrenId(), 0);
        }
        Collection<Long> ids =new ArrayList<Long>();
        Iterator<Map.Entry<Long, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Long, Object> m = iterator.next();
            ids.add(m.getKey());
        }
        Page<Children> childrenPackage = null;
        if(name!=null&&!name.equals("")){
            name = "%"+name+"%";
            childrenPackage = childrenService.findByIdAndNameLike(ids, name, pageable);
        }else {
            childrenPackage = childrenService.findById(ids, pageable);
        }

        List<ChildrentDTO> childrentDTOs = new ArrayList<>();
        childrenPackage.getContent().forEach(e ->{
           childrentDTOs.add(setChildrent(e));
        });

        return new PageImpl<ChildrentDTO>(childrentDTOs, pageable, childrenPackage.getTotalPages());
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
