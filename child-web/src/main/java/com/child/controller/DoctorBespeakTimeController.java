package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.dao.DoctorBespeakTimeRepository;
import com.child.dao.DoctorConsultRepository;
import com.child.entity.admin.Admin;
import com.child.entity.doctor.DoctorBespeakTime;
import com.child.entity.doctor.DoctorConsult;
import com.child.json.doctor.DoctorBespeakTimeJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/doctorBespeakTime")
@Api(value = "医生预约时间")
public class DoctorBespeakTimeController  {

    @Autowired
    private DoctorBespeakTimeRepository doctorBespeakTimeRepository;
    @Autowired
    private DoctorConsultRepository doctorConsultRepository;

    @ApiOperation(value = "保存医生预约时间")
    @PostMapping(value = "/save")
    public DoctorBespeakTimeJson saveDoctorBespeakTime(
            @RequestHeader(ConstantsUtils.AUTH_TOKEN_HEADER_NAME)String authToken,
            @RequestBody DoctorBespeakTimeJson doctorBespeakTimeJson){
        DoctorBespeakTime doctorBespeakTime = null;
        if(null == doctorBespeakTimeJson.getId()) {
            doctorBespeakTime = new DoctorBespeakTime();
            doctorBespeakTime.setAddTime(new Date());
        }else{
            doctorBespeakTime = doctorBespeakTimeRepository.findOne(doctorBespeakTimeJson.getId());
        }
        BeanCopierUtils.getBeanCopier(DoctorBespeakTimeJson.class, DoctorBespeakTime.class);
        BeanCopierUtils.copyProperties(doctorBespeakTimeJson,doctorBespeakTime);
        doctorBespeakTimeRepository.save(doctorBespeakTime);
        BeanCopierUtils.getBeanCopier(DoctorBespeakTime.class, DoctorBespeakTimeJson.class);
        BeanCopierUtils.copyProperties(doctorBespeakTime,doctorBespeakTimeJson);
        return doctorBespeakTimeJson;
    }
    /**
     * @return
     */
    @ApiOperation(value = "获取医生预约时间列表")
    @GetMapping(value = "/getAllDoctorBespeakTime")
    public ResultsWrapper<DoctorBespeakTimeJson> getAllDoctorBespeakTime(
            @RequestHeader(ConstantsUtils.AUTH_TOKEN_HEADER_NAME) String authToken,
            @RequestParam(value = "doctorId",required = false) Long doctorId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<DoctorBespeakTime> doctorBespeakTimePage = null;
        if(null != doctorId && !doctorId.equals("")){
            doctorBespeakTimePage = doctorBespeakTimeRepository.findAll(pageable);
        }else{
            doctorBespeakTimePage = doctorBespeakTimeRepository.findByDoctorId(doctorId,pageable);
        }
        List<DoctorBespeakTimeJson> doctorBespeakTimeJsonList = new ArrayList<>();
        List<DoctorBespeakTime> doctorBespeakTimeList = doctorBespeakTimePage.getContent();
        for(DoctorBespeakTime doctorBespeakTime:doctorBespeakTimeList){
            DoctorBespeakTimeJson doctorBespeakTimeJson = new DoctorBespeakTimeJson();
            BeanCopierUtils.getBeanCopier(DoctorBespeakTime.class, DoctorBespeakTimeJson.class);
            BeanCopierUtils.copyProperties(doctorBespeakTime,doctorBespeakTimeJson);
            List<DoctorConsult> doctorConsultList = doctorConsultRepository.findByDoctorBespeakTimeId(doctorBespeakTime.getId());
            if(doctorConsultList.size() > 0) {
                doctorBespeakTimeJson.setNum(doctorBespeakTime.getNum()-doctorConsultList.size());
            }
            doctorBespeakTimeJsonList.add(doctorBespeakTimeJson);
        }
        ResultsWrapper<DoctorBespeakTimeJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(doctorBespeakTimeJsonList);
        resultsWrapper.setPages(doctorBespeakTimePage.getTotalPages());
        resultsWrapper.setTotal(doctorBespeakTimePage.getTotalElements());
        return resultsWrapper;
    }

    @ApiOperation(value = "获取医生预约时间详情")
    @GetMapping(value = "/getDoctorBespeakTimeById")
    public DoctorBespeakTimeJson getDoctorBespeakTimeById(
            @ApiParam(value = "医生预约时间id",required = true)
            @RequestParam(value = "id") Long id){

        DoctorBespeakTime doctorBespeakTime = doctorBespeakTimeRepository.findOne(id);
        if(null == doctorBespeakTime){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        DoctorBespeakTimeJson doctorBespeakTimeJson = new DoctorBespeakTimeJson();
        BeanCopierUtils.getBeanCopier(DoctorBespeakTime.class,DoctorBespeakTimeJson.class);
        BeanCopierUtils.copyProperties(doctorBespeakTime,doctorBespeakTimeJson);
        return doctorBespeakTimeJson;
    }

    @ApiOperation(value = "删除医生预约时间")
    @DeleteMapping(value = "/deleteDoctorBespeakTimeById")
    public void deleteDoctorBespeakTimeById(
            @RequestHeader(ConstantsUtils.AUTH_TOKEN_HEADER_NAME) String authToken,
            @ApiParam(value = "医生预约时间id",required = true)
            @RequestParam(value = "id") Long id){
        DoctorBespeakTime doctorBespeakTime = doctorBespeakTimeRepository.findOne(id);
        if(null == doctorBespeakTime){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        doctorBespeakTimeRepository.delete(doctorBespeakTime);
    }

}
