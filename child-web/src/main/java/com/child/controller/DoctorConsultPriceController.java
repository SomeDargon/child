package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.dao.DoctorConsultPriceRepository;
import com.child.dto.DoctorConsultDTO;
import com.child.dto.ServiceNumDTO;
import com.child.entity.WeiXinCustomer;
import com.child.entity.admin.Admin;
import com.child.entity.doctor.DoctorConsult;
import com.child.entity.doctor.DoctorConsultPrice;
import com.child.entity.doctor.QDoctorConsultPrice;
import com.child.enums.RoleEnum;
import com.child.json.doctor.DoctorConsultPriceJson;
import com.child.service.DoctorConsultService;
import com.child.service.WeiXinCustomerService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/doctorConsultPrice")
@Api(value = "医生咨询价格")
public class DoctorConsultPriceController {

    @Autowired
    private DoctorConsultService doctorConsultService;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;
    @Autowired
    private DoctorConsultPriceRepository doctorConsultPriceRepository;
    @ApiOperation(value = "保存医生咨询价格")
    @PostMapping(value = "/save")
    public DoctorConsultPriceJson saveDoctorConsultPrice(
            @RequestBody DoctorConsultPriceJson doctorConsultPriceJson,
            HttpServletRequest request){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer =  weiXinCustomerService.findById(id);
        if(weiXinCustomer==null){  return null;}
        if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())){

        }
        Admin admin = new Admin();
        DoctorConsultPrice doctorConsultPrice = null;
        if(null == doctorConsultPriceJson.getId()) {
            doctorConsultPrice = new DoctorConsultPrice();
            doctorConsultPrice.setAddTime(new Date());
            doctorConsultPrice.setUserId(admin.getId());
        }else{
            doctorConsultPrice = doctorConsultPriceRepository.findOne(doctorConsultPriceJson.getId());
        }
        BeanCopierUtils.getBeanCopier(DoctorConsultPriceJson.class, DoctorConsultPrice.class);
        BeanCopierUtils.copyProperties(doctorConsultPriceJson,doctorConsultPrice);
        doctorConsultPriceRepository.save(doctorConsultPrice);
        BeanCopierUtils.getBeanCopier(DoctorConsultPrice.class, DoctorConsultPriceJson.class);
        BeanCopierUtils.copyProperties(doctorConsultPrice,doctorConsultPriceJson);
        return doctorConsultPriceJson;
    }






}
