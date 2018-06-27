package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.PicProcessType;
import com.child.dao.AdminRepository;
import com.child.dao.CustomerRepository;
import com.child.dao.NorImageRepository;
import com.child.dao.PatientEducationRepository;
import com.child.dto.DoctorDTO;
import com.child.entity.WeiXinCustomer;
import com.child.entity.admin.Admin;
import com.child.entity.customer.Customer;
import com.child.entity.doctor.Doctor;
import com.child.entity.doctor.PatientEducation;
import com.child.entity.image.NorImage;
import com.child.enums.RoleEnum;
import com.child.enums.SendStatus;
import com.child.json.doctor.PatientEducationJson;
import com.child.json.doctor.PatientEducationResponseJson;
import com.child.json.image.NorImageJson;
import com.child.service.DoctorService;
import com.child.service.NorImageService;
import com.child.service.WeiXinCustomerService;
import com.child.vo.PatientEducationJsonVO;
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
@RequestMapping(value = "/patientEducation")
@Api(value = "患者教育")
public class PatientEducationController {

    @Autowired
    private PatientEducationRepository patientEducationRepository;
    @Autowired
    private NorImageService norImageService;
    @Autowired
    private NorImageRepository norImageRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private DoctorService doctorService;
    @ApiOperation(value = "保存患者教育")
    @PostMapping(value = "/save")
    public PatientEducationJson savePatientEducation(
            @RequestBody PatientEducationJson patientEducationJson, HttpServletRequest request){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        PatientEducation patientEducation = null;
        if(null == patientEducationJson.getId()) {
            patientEducation = new PatientEducation();
            patientEducation.setUserId(weiXinCustomer.getId());
            if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())){
                patientEducation.setSendType(RoleEnum.DOCTOR.getCode());
            }else if(weiXinCustomer.getType().equals(RoleEnum.ASSISTANT.getCode())){
                patientEducation.setSendType(RoleEnum.ASSISTANT.getCode());
            }
        }else{
            patientEducation = patientEducationRepository.findOne(patientEducationJson.getId());
            patientEducation.setAddTime(patientEducation.getAddTime());
            patientEducation.setCustomerId(weiXinCustomer.getId());  //审核
            //清空图片
            List<NorImage> list = norImageService.findByObjectIdAndType(patientEducation.getId(), 1);
            list.forEach(e->{
                e.setObjectId(null);
                norImageService.save(e);
            });
        }
        BeanCopierUtils.getBeanCopier(PatientEducationJson.class, PatientEducation.class);
        BeanCopierUtils.copyProperties(patientEducationJson,patientEducation);
        patientEducation.setAddTime(new Date());
        patientEducation = patientEducationRepository.save(patientEducation);
        if(patientEducationJson.getImageList().size() >0){
            for(Long imageId:patientEducationJson.getImageList()){
                NorImage norImage =  norImageService.findOne(imageId);
                norImage.setObjectId(patientEducation.getId());
                norImageService.save(norImage);
            }
        }
        return patientEducationJson;
    }

    @ApiOperation(value = "获取患者教育列表")
    @GetMapping(value = "/getAllPatientEducation")
    public ResultsWrapper<PatientEducationResponseJson> getAllPatientEducation(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size,
            HttpServletRequest request
            ){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        Page<PatientEducation> patientEducationPage = null;
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())){
            if(null == title || title.equals("")){
                patientEducationPage = patientEducationRepository.findByUserIdAndSendType(id, RoleEnum.DOCTOR.getCode(),pageable);
            }else{
                patientEducationPage = patientEducationRepository.findByTitleLikeAndUserId(title, id, RoleEnum.DOCTOR.getCode(), pageable);
            }
        }else
        if(weiXinCustomer.getType().equals(RoleEnum.ASSISTANT.getCode())){
            if(null == title || title.equals("")){
                patientEducationPage = patientEducationRepository.findAll(pageable);
            }else{
                patientEducationPage = patientEducationRepository.findByTitleLike(title,pageable);
            }
        }

        List<PatientEducationResponseJson> patientEducationJsonList = new ArrayList<>();
        if(patientEducationPage!=null) {
            List<PatientEducation> patientEducationList = patientEducationPage.getContent();
            for (PatientEducation patientEducation : patientEducationList) {
                PatientEducationResponseJson patientEducationJson = new PatientEducationResponseJson();
                BeanCopierUtils.getBeanCopier(PatientEducation.class, PatientEducationResponseJson.class);
                BeanCopierUtils.copyProperties(patientEducation, patientEducationJson);
                if (patientEducation.getUserId() != null) {
                    if (patientEducation.getSendType() == null || patientEducation.getSendType().equals("")) {
                        Customer customer = customerRepository.findOne(patientEducation.getUserId());
                        patientEducationJson.setName(customer.getName());

                    } else {
                        WeiXinCustomer wx = weiXinCustomerService.findById(patientEducation.getUserId());
                        if (wx.getType().equals(RoleEnum.ASSISTANT.getCode())) {
                            Customer customer = customerRepository.findOne(patientEducation.getUserId());
                            patientEducationJson.setName(customer.getName());
                        } else if (wx.getType().equals(RoleEnum.DOCTOR.getCode())) {
                            Doctor doctor = doctorService.findByid(wx.getDoctorId());
                            patientEducationJson.setName(doctor.getName());
                        }
                    }
                }
                patientEducationJsonList.add(patientEducationJson);
            }
        }
        ResultsWrapper<PatientEducationResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(patientEducationJsonList);
        if(patientEducationPage!=null) {
            resultsWrapper.setPages(patientEducationPage.getTotalPages());
            resultsWrapper.setTotal(patientEducationPage.getTotalElements());
        }else {
            resultsWrapper.setPages(0);
            resultsWrapper.setTotal(0L);
        }
        return resultsWrapper;
    }

    @ApiOperation(value = "获取患者教育详情")
    @GetMapping(value = "/getPatientEducationById")
    public PatientEducationJsonVO getPatientEducationById(
            @ApiParam(value = "患者教育id",required = true)
            @RequestParam(value = "id") Long id){
        PatientEducation patientEducation = patientEducationRepository.findOne(id);
        if(null == patientEducation){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        PatientEducationJsonVO patientEducationJson = new PatientEducationJsonVO();
        BeanCopierUtils.getBeanCopier(PatientEducation.class,PatientEducationJsonVO.class);
        BeanCopierUtils.copyProperties(patientEducation,patientEducationJson);
        List<NorImage> NorImageList = norImageRepository.findByObjectIdAndType(patientEducation.getId(),1);
        if(NorImageList.size() > 0) {
            patientEducationJson.setImageList(NorImageList);
        }else{
            patientEducationJson.setImageList(new ArrayList<>());
        }
        return patientEducationJson;
    }




}
