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
import com.child.entity.article.ActivityStyle;
import com.child.entity.article.QActivityStyle;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerActivityRecord;
import com.child.entity.image.NorImage;
import com.child.json.article.ActivityStyleJson;
import com.child.json.article.ActivityStyleResponseJson;
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
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping(value = "/activityStyle")
@Api(value = "活动风采")
public class ActivityStyleController  {

    @Autowired
    private ActivityStyleRepository activityStyleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private NorImageService norImageService;
    @Autowired
    private NorImageRepository norImageRepository;

    @Autowired
    private CustomerActivityRecordRepository customerActivityRecordRepository;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;
    @Autowired
    private CustomerRepository customerRepository;
    @ApiOperation(value = "保存活动风采")
    @PostMapping(value = "/save")
    public ActivityStyleJson saveActivityStyle(

            HttpServletRequest request,
            @RequestBody ActivityStyleJson activityStyleJson){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        ActivityStyle activityStyle = null;
        if(null == activityStyleJson.getId()){
            activityStyle = new ActivityStyle();
            activityStyle.setUserId(1L);
            activityStyle.setType(1);
            activityStyle.setCustomerId(weiXinCustomer.getDoctorId());
            activityStyle.setStatus(1);
            activityStyle.setAddTime(new Date());
        }else {
            activityStyle = activityStyleRepository.findOne(activityStyleJson.getId());
            activityStyle.setUpdateTime(new Date());
        }
        if(null != activityStyleJson.getCoverImageJsonList() && !activityStyleJson.getCoverImageJsonList().equals("")) {
            if (activityStyleJson.getCoverImageJsonList().size() > 0) {
                for(Long imageId:activityStyleJson.getCoverImageJsonList()){
                    NorImage norImage =  norImageService.findOne(imageId);
                    norImage.setObjectId(activityStyleJson.getId());
                    norImageService.save(norImage);
                    activityStyle.setCover(norImage.getNorImageUrl());
                }
            }
        }
        BeanCopierUtils.getBeanCopier(ActivityStyleJson.class, ActivityStyle.class);
        BeanCopierUtils.copyProperties(activityStyleJson,activityStyle);
        activityStyleRepository.save(activityStyle);
        if(null != activityStyleJson.getImageJsonList() && !activityStyleJson.getImageJsonList().equals("")) {
            if (activityStyleJson.getImageJsonList().size() > 0) {
                for(Long imageId:activityStyleJson.getImageJsonList()){
                    NorImage norImage =  norImageService.findOne(imageId);
                    norImage.setObjectId(activityStyleJson.getId());
                    norImageService.save(norImage);
                }
            }
        }
        BeanCopierUtils.getBeanCopier(ActivityStyle.class, ActivityStyleJson.class);
        BeanCopierUtils.copyProperties(activityStyle,activityStyleJson);
        return activityStyleJson;
    }
    /**
     * @return
     */
    @ApiOperation(value = "获取活动风采列表")
    @GetMapping(value = "/getAllActivityStyle")
    public ResultsWrapper<ActivityStyleResponseJson> getAllActivityStyle(
            @RequestParam(value = "title",required = false) String title,
            @ApiParam(value = "类型  1 最新活动  2 活动风采")
            @RequestParam(value = "type",required = false) Integer type,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Predicate predicate = null;
        QActivityStyle activityStyleName = QActivityStyle.activityStyle;
        if(null != title && !title.equals("")) {
            predicate = activityStyleName.title.like("%"+title+"%");
        }
        Page<ActivityStyle> activityStylePage = activityStyleRepository.findAll(predicate,pageable);
        List<ActivityStyleResponseJson> activityStyleJsonList = new ArrayList<>();
        List<ActivityStyle> activityStyleList = activityStylePage.getContent();
        for(ActivityStyle activityStyle:activityStyleList){
            ActivityStyleResponseJson activityStyleJson = new ActivityStyleResponseJson();
            BeanCopierUtils.getBeanCopier(ActivityStyle.class, ActivityStyleResponseJson.class);
            BeanCopierUtils.copyProperties(activityStyle,activityStyleJson);
            if(activityStyle.getType()==null||activityStyle.getType().equals("")) {
                Admin admin = adminRepository.findOne(activityStyle.getUserId());
                activityStyleJson.setPublishId(admin.getId());
                activityStyleJson.setPublishName(admin.getName());
            }else {
                Customer customer = customerRepository.findOne(activityStyle.getCustomerId());
                activityStyleJson.setPublishId(customer.getId());
                activityStyleJson.setPublishName(customer.getName());
            }

            List<NorImage> NorImageList = norImageRepository.findByObjectIdAndType(activityStyle.getId(),5);
            if(NorImageList.size() > 0) {
                List<NorImageJson> norImageJsonList = new ArrayList<>();
                for (NorImage NorImage : NorImageList) {
                    NorImageJson NorImageJson = new NorImageJson();
                    BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                    BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                    norImageJsonList.add(NorImageJson);
                }
                activityStyleJson.setImageJsonList(norImageJsonList);
            }
            activityStyleJsonList.add(activityStyleJson);
        }
        ResultsWrapper<ActivityStyleResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(activityStyleJsonList);
        resultsWrapper.setPages(activityStylePage.getTotalPages());
        resultsWrapper.setTotal(activityStylePage.getTotalElements());
        return resultsWrapper;
    }

    @ApiOperation(value = "获取活动风采详情")
    @GetMapping(value = "/getActivityStyleById")
    public ActivityStyleResponseJson getActivityStyleById(
            @ApiParam(value = "活动风采id",required = true)
            @RequestParam(value = "id") Long id){
        ActivityStyle activityStyle = activityStyleRepository.findOne(id);
        if(null == activityStyle){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        ActivityStyleResponseJson activityStyleJson = new ActivityStyleResponseJson();
        BeanCopierUtils.getBeanCopier(ActivityStyle.class,ActivityStyleResponseJson.class);
        BeanCopierUtils.copyProperties(activityStyle,activityStyleJson);
        if(activityStyle.getType()==null||activityStyle.getType().equals("")) {
            Admin admin = adminRepository.findOne(activityStyle.getUserId());
            activityStyleJson.setPublishId(admin.getId());
            activityStyleJson.setPublishName(admin.getName());
        }else {
            Customer customer = customerRepository.findOne(activityStyle.getCustomerId());
            activityStyleJson.setPublishId(customer.getId());
            activityStyleJson.setPublishName(customer.getName());
        }
        List<NorImage> NorImageList = norImageRepository.findByObjectIdAndType(activityStyle.getId(),5);
        if(NorImageList.size() > 0) {
            List<NorImageJson> norImageJsonList = new ArrayList<>();
            for (NorImage NorImage : NorImageList) {
                NorImageJson NorImageJson = new NorImageJson();
                BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                norImageJsonList.add(NorImageJson);
            }
            activityStyleJson.setImageJsonList(norImageJsonList);
        }
        return activityStyleJson;
    }

    @ApiOperation(value = "删除活动风采")
    @DeleteMapping(value = "/deleteActivityStyleById")
    public void deleteActivityStyleById(
            @ApiParam(value = "活动风采id",required = true)
            @RequestParam(value = "id") Long id){
        Admin admin  = new Admin();
        ActivityStyle activityStyle = activityStyleRepository.findOne(id);
        if(null == activityStyle){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        if(!admin.getId().equals(activityStyle.getUserId())){
            throw new BusinessException(ErrorCode.NOT_DELETE_OTHERS);
        }
        activityStyleRepository.delete(activityStyle);
    }


    @ApiOperation(value = "结束活动风采")
    @GetMapping(value = "/overActivityStyle")
    @ApiIgnore
    public ActivityStyleJson overActivityStyle(
            @RequestHeader(ConstantsUtils.AUTH_TOKEN_HEADER_NAME)String authToken,
            @RequestParam Long id){
        Admin admin = new Admin();
        ActivityStyle activityStyle = activityStyleRepository.findOne(id);
        if(null == activityStyle){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        if(!admin.getId().equals(activityStyle.getUserId())){
            throw new  BusinessException(ErrorCode.NOT_DELETE_OTHERS);
        }
        activityStyle.setStatus(2);
        activityStyleRepository.save(activityStyle);
        ActivityStyleJson activityStyleJson = new ActivityStyleJson();
        BeanCopierUtils.getBeanCopier(ActivityStyle.class, ActivityStyleJson.class);
        BeanCopierUtils.copyProperties(activityStyle,activityStyleJson);
        return activityStyleJson;
    }

}
