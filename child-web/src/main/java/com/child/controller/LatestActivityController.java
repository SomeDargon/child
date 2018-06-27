package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.PicProcessType;
import com.child.dao.*;
import com.child.entity.WeiXinCustomer;
import com.child.entity.admin.Admin;
import com.child.entity.article.LatestActivity;
import com.child.entity.article.QLatestActivity;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerActivityRecord;
import com.child.entity.image.NorImage;
import com.child.json.article.LatestActivityJson;
import com.child.json.article.LatestActivityResponseJson;
import com.child.json.image.NorImageJson;
import com.child.service.NorImageService;
import com.child.service.WeiXinCustomerService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/latestActivity")
@Api(value = "最新活动")
public class LatestActivityController  {

    @Autowired
    private LatestActivityRepository latestActivityRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private NorImageService norImageService;
    @Autowired
    private NorImageRepository norImageRepository;

    @Autowired
    private CustomerActivityRecordRepository customerActivityRecordRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;
    @ApiOperation(value = "保存最新活动")
    @PostMapping(value = "/save")
    public LatestActivityJson saveLatestActivity(
            @RequestBody LatestActivityJson latestActivityJson,
            HttpServletRequest request
    ){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        LatestActivity latestActivity = null;
        if(null == latestActivityJson.getId()){
            latestActivity = new LatestActivity();
            latestActivity.setType(1);
            latestActivity.setUserId(weiXinCustomer.getDoctorId());
            latestActivity.setStatus(1);
            latestActivity.setUserId(1L);
            latestActivity.setAddTime(new Date());
        }else {
            latestActivity = latestActivityRepository.findOne(latestActivityJson.getId());
            latestActivity.setUpdateTime(new Date());
        }
        if(null != latestActivityJson.getCoverImageJsonList() && !latestActivityJson.getCoverImageJsonList().equals("")) {
            if (latestActivityJson.getCoverImageJsonList().size() > 0) {
                for(Long imgId :latestActivityJson.getCoverImageJsonList()) {
                    for(Long imageId:latestActivityJson.getImageJsonList()){
                        NorImage norImage =  norImageService.findOne(imageId);
                        norImage.setObjectId(latestActivity.getId());
                        norImageService.save(norImage);
                        latestActivity.setCover(norImage.getNorImageUrl());
                    }
                }
            }
        }
        BeanCopierUtils.getBeanCopier(LatestActivityJson.class, LatestActivity.class);
        BeanCopierUtils.copyProperties(latestActivityJson,latestActivity);
        latestActivity = latestActivityRepository.save(latestActivity);
        if(null != latestActivityJson.getImageJsonList() && !latestActivityJson.getImageJsonList().equals("")) {
            if (latestActivityJson.getImageJsonList().size() > 0) {
                for(Long imageId:latestActivityJson.getImageJsonList()){
                    NorImage norImage =  norImageService.findOne(imageId);
                    norImage.setObjectId(latestActivity.getId());
                    norImageService.save(norImage);
                }
            }
        }
        BeanCopierUtils.getBeanCopier(LatestActivity.class, LatestActivityJson.class);
        BeanCopierUtils.copyProperties(latestActivity,latestActivityJson);
        return latestActivityJson;
    }
    /**
     * @return
     */
    @ApiOperation(value = "获取最新活动列表")
    @GetMapping(value = "/getAllLatestActivity")
    public ResultsWrapper<LatestActivityResponseJson> getAllLatestActivity(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Predicate predicate = null;
        QLatestActivity latestActivityName = QLatestActivity.latestActivity;
        if(null != title && !title.equals("")) {
            predicate = latestActivityName.title.like("%"+title+"%");
        }
        Page<LatestActivity> latestActivityPage = latestActivityRepository.findAll(predicate,pageable);
        List<LatestActivityResponseJson> latestActivityJsonList = new ArrayList<>();
        List<LatestActivity> latestActivityList = latestActivityPage.getContent();
        for(LatestActivity latestActivity:latestActivityList){
            LatestActivityResponseJson latestActivityJson = new LatestActivityResponseJson();
            BeanCopierUtils.getBeanCopier(LatestActivity.class, LatestActivityResponseJson.class);
            BeanCopierUtils.copyProperties(latestActivity,latestActivityJson);
            if(latestActivity.getType()==null||latestActivity.getType().equals("")) {
                Admin admin = adminRepository.findOne(latestActivity.getUserId());
                latestActivityJson.setPublishId(admin.getId());
                latestActivityJson.setPublishName(admin.getName());
            }else {
                Customer customer = customerRepository.findOne(latestActivity.getCustomerId());
                latestActivityJson.setPublishId(customer.getId());
                latestActivityJson.setPublishName(customer.getName());
            }
            List<CustomerActivityRecord> customerActivityRecordList = customerActivityRecordRepository.findByActivityId(latestActivity.getId());
            if(customerActivityRecordList.size() >0){
                latestActivityJson.setSurplusNum(latestActivity.getNum()-customerActivityRecordList.size());
                latestActivityJson.setReportNum(customerActivityRecordList.size());
            }else {
                latestActivityJson.setSurplusNum(latestActivity.getNum());
                latestActivityJson.setReportNum(0);
            }
            List<NorImage> NorImageList = norImageRepository.findByObjectIdAndType(latestActivity.getId(),6);
            if(NorImageList.size() > 0) {
                List<NorImageJson> norImageJsonList = new ArrayList<>();
                for (NorImage NorImage : NorImageList) {
                    NorImageJson NorImageJson = new NorImageJson();
                    BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                    BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                    norImageJsonList.add(NorImageJson);
                }
                latestActivityJson.setImageJsonList(norImageJsonList);
            }
            latestActivityJsonList.add(latestActivityJson);
        }
        ResultsWrapper<LatestActivityResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(latestActivityJsonList);
        resultsWrapper.setPages(latestActivityPage.getTotalPages());
        resultsWrapper.setTotal(latestActivityPage.getTotalElements());
        return resultsWrapper;
    }

    @ApiOperation(value = "获取最新活动详情")
    @GetMapping(value = "/getLatestActivityById")
    public LatestActivityResponseJson getLatestActivityById(
            @ApiParam(value = "最新活动id",required = true)
            @RequestParam(value = "id") Long id){
        LatestActivity latestActivity = latestActivityRepository.findOne(id);
        if(null == latestActivity){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        LatestActivityResponseJson latestActivityJson = new LatestActivityResponseJson();
        BeanCopierUtils.getBeanCopier(LatestActivity.class,LatestActivityResponseJson.class);
        BeanCopierUtils.copyProperties(latestActivity,latestActivityJson);
        Admin admin = adminRepository.findOne(latestActivity.getUserId());
        latestActivityJson.setPublishId(admin.getId());
        latestActivityJson.setPublishName(admin.getName());
        List<CustomerActivityRecord> customerActivityRecordList = customerActivityRecordRepository.findByActivityId(latestActivity.getId());
        if(customerActivityRecordList.size() >0){
            latestActivityJson.setSurplusNum(latestActivity.getNum()-customerActivityRecordList.size());
            latestActivityJson.setReportNum(customerActivityRecordList.size());
        }else {
            latestActivityJson.setSurplusNum(latestActivity.getNum());
            latestActivityJson.setReportNum(0);
        }
        List<NorImage> NorImageList = norImageRepository.findByObjectIdAndType(latestActivity.getId(),6);
        if(NorImageList.size() > 0) {
            List<NorImageJson> norImageJsonList = new ArrayList<>();
            for (NorImage NorImage : NorImageList) {
                NorImageJson NorImageJson = new NorImageJson();
                BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                norImageJsonList.add(NorImageJson);
            }
            latestActivityJson.setImageJsonList(norImageJsonList);
        }
        return latestActivityJson;
    }

    @ApiOperation(value = "删除最新活动")
    @DeleteMapping(value = "/deleteLatestActivityById")
    public void deleteLatestActivityById(
            @ApiParam(value = "最新活动id",required = true)
            @RequestParam(value = "id") Long id){
        Admin admin  = new Admin();
        LatestActivity latestActivity = latestActivityRepository.findOne(id);
        if(null == latestActivity){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        if(!admin.getId().equals(latestActivity.getUserId())){
            throw new BusinessException(ErrorCode.NOT_DELETE_OTHERS);
        }
        latestActivityRepository.delete(latestActivity);
    }

}
