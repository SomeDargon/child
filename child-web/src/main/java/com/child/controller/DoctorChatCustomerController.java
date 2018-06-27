package com.child.controller;

import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.DatetimeUtil;
import com.child.common.utils.PicProcessType;
import com.child.dao.*;
import com.child.entity.WeiXinCustomer;
import com.child.entity.admin.Admin;
import com.child.entity.children.Children;
import com.child.entity.customer.Customer;
import com.child.entity.doctor.Doctor;
import com.child.entity.doctor.DoctorChatCustomer;
import com.child.entity.doctor.DoctorConsult;
import com.child.json.doctor.DoctorChatCustomerJson;
import com.child.json.doctor.DoctorChatCustomerResponseJson;
import com.child.service.NorImageService;
import com.child.service.WeiXinCustomerService;
import com.child.vo.DoctorChatCustomerVO;
import com.lly835.bestpay.utils.JsonUtil;
import com.querydsl.core.annotations.Config;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping(value = "/doctorChatCustomer")
@Api(value = "图文聊天咨询内容咨询")
@Slf4j
public class DoctorChatCustomerController  {

    @Autowired
    private DoctorChatCustomerRepository doctorChatCustomerRepository;

    @Autowired
    private NorImageService norImageService;
    @Autowired
    private DoctorConsultRepository doctorConsultRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ChildrenRepository childrenRepository;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;

    @ApiOperation(value = "保存图文聊天咨询内容咨询")
    @PostMapping(value = "/save")
    public DoctorChatCustomerVO saveDoctorChatCustomer(
            @RequestBody DoctorChatCustomerVO doctorChatCustomerJson){
        DoctorConsult doctorConsult = doctorConsultRepository.findOne(doctorChatCustomerJson.getDoctorConsultId());

        DoctorChatCustomer doctorChatCustomer = new DoctorChatCustomer();
        // if(null == doctorChatCustomerJson.getCustomerId() || doctorChatCustomerJson.getCustomerId().equals("")) {
         //   Customer customer = (Customer) getSessionByAuthToken(authToken);
         //   doctorChatCustomer.setCustomerId(customer.getId());
        //}
        doctorChatCustomer.setAddTime(new Date());
        doctorChatCustomer.setStatus(1);
        BeanCopierUtils.getBeanCopier(DoctorChatCustomerVO.class, DoctorChatCustomer.class);
        BeanCopierUtils.copyProperties(doctorChatCustomerJson,doctorChatCustomer);
        if(doctorChatCustomerJson.getImageUrl()!=null&&!doctorChatCustomerJson.getImageUrl().equals("")) {
            doctorChatCustomer.setPic( doctorChatCustomerJson.getImageUrl());
        }
        doctorChatCustomer.setChildrenId(doctorConsult.getChildrenId());
        doctorChatCustomer.setDoctorId(doctorConsult.getDoctorId());
        //用户
        if(doctorChatCustomerJson.getType()==null){
            doctorConsult.setAdminStatus(0);
            doctorConsult.setUserStatus(1);
            doctorConsult.setDoctorStatus(0);
            //客服
        } else  if(doctorChatCustomerJson.getType().equals("1")){
            doctorConsult.setAdminStatus(1);
            doctorConsult.setUserStatus(0);
            doctorConsult.setDoctorStatus(0);
            //医生
        } else if(doctorChatCustomerJson.getType().equals("2")){
            doctorConsult.setAdminStatus(0);
            doctorConsult.setUserStatus(0);
            doctorConsult.setDoctorStatus(1);
        }
        doctorConsultRepository.save(doctorConsult);
        doctorChatCustomerRepository.save(doctorChatCustomer);
//        if(null != doctorChatCustomerJson.getUserType() && !doctorChatCustomerJson.getUserType().equals("")) {
//            if (2==doctorChatCustomerJson.getUserType() ||doctorChatCustomerJson.getUserType().equals(2)) {
//                //List<DoctorChatCustomer> doctorChatCustomerList = doctorChatCustomerRepository.findByUserTypeAndDoctorConsultId(2,doctorConsult.getId());
//                //if(doctorChatCustomerList.size() < 2) {
//                    Customer customer = customerRepository.findOne(doctorConsult.getCustomerId());
//                    Doctor doctor = doctorRepository.findOne(doctorConsult.getDoctorId());
//                    try {
//                        /*WxMpKefuMessage message = WxMpKefuMessage.TEXT()
//                                .toUser(customer.getOpenId())
//                                .content("医生回复了您的消息,点击链接查看详细内容 >>>>>>>>>>>>>>>>    http://www.bensonchen.cn/dist/#/graphicConsulting/" + doctorConsult.getId() + "")
//                                .build();*/
//                        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage
//                                .builder()
//                                .toUser(customer.getOpenId())
//                                .templateId("DL_igaDDMu2AVW8Uzc_lWl7ax7VpEhA0sc5pf1Dx1Yg")
//                                .url("http://www.bensonchen.cn/dist/#/graphicConsulting/" + doctorConsult.getId()+"")
//                                .build();
//
//                        wxMpTemplateMessage.getData().add(new WxMpTemplateData("first","您好，"+doctor.getName()+"医生已回复您的消息", "#0000FF"));
//                        wxMpTemplateMessage.getData().add(new WxMpTemplateData("keyword1", doctorChatCustomer.getContent(), "#0000FF"));
//                        wxMpTemplateMessage.getData().add(new WxMpTemplateData("keyword2", DatetimeUtil.formatDatetime(doctorChatCustomer.getAddTime()), "#0000FF"));
//                        //String a = "<a href=\"http://www.bensonchen.cn/dist/#/graphicConsulting/" + doctorConsult.getId()+">点此查看详情</a>";
//                        wxMpTemplateMessage.getData().add(new WxMpTemplateData("remark","点击查看详情", "#0000FF"));
//                        wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
//                        log.info(JsonUtil.toJson(wxMpTemplateMessage));
//                    } catch (WxErrorException e) {
//                        log.error("【微信回复消息】{}", e);
//                    }
//               // }
//            }
//        }
        BeanCopierUtils.getBeanCopier(DoctorChatCustomer.class, DoctorChatCustomerVO.class);
        BeanCopierUtils.copyProperties(doctorChatCustomer,doctorChatCustomerJson);
        return doctorChatCustomerJson;
    }

    @ApiOperation(value = "获取图文聊天咨询内容咨询列表")
    @GetMapping(value = "/getAllDoctorChatCustomer")
    public ResultsWrapper<DoctorChatCustomerResponseJson> getAllDoctorChatCustomer(
            @RequestParam Long doctorConsultId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size,
            HttpServletRequest request
    ){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer =  weiXinCustomerService.findById(id);
        Integer type = weiXinCustomer.getType();
        DoctorConsult doctorConsult = doctorConsultRepository.findOne(doctorConsultId);
        //用户
        if(type.equals(0)){
            doctorConsult.setUserStatus(1);
            //医生
        } else  if(type.equals(1)){
            doctorConsult.setDoctorStatus(1);
            //客服
        } else if(type.equals(2)){
            doctorConsult.setAdminStatus(1);
        }
        doctorConsultRepository.save(doctorConsult);
        Sort sort = new Sort(Sort.Direction.ASC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<DoctorChatCustomer> doctorChatCustomerPage = doctorChatCustomerRepository.findByDoctorConsultId(doctorConsultId,pageable);
        List<DoctorChatCustomerResponseJson> doctorChatCustomerJsonList = new ArrayList<>();
        List<DoctorChatCustomer> doctorChatCustomerList = doctorChatCustomerPage.getContent();
        for(DoctorChatCustomer doctorChatCustomer:doctorChatCustomerList){
            DoctorChatCustomerResponseJson doctorChatCustomerJson = new DoctorChatCustomerResponseJson();
            BeanCopierUtils.getBeanCopier(DoctorChatCustomer.class, DoctorChatCustomerJson.class);
            BeanCopierUtils.copyProperties(doctorChatCustomer,doctorChatCustomerJson);
            if(null != doctorChatCustomer.getDoctorId()){
                Doctor doctor = doctorRepository.findOne(doctorChatCustomer.getDoctorId());
                doctorChatCustomerJson.setDoctorIcon(doctor.getIcon());
                doctorChatCustomerJson.setDoctorId(doctor.getId());
                doctorChatCustomerJson.setDoctorName(doctor.getName());
            }
            if(null != doctorChatCustomer.getChildrenId()){
                Children children = childrenRepository.findOne(doctorChatCustomer.getChildrenId());
                if(null != children.getIcon() && !children.getIcon().equals("")) {
                    doctorChatCustomerJson.setChildrenIcon(children.getIcon());
                }
                doctorChatCustomerJson.setChildrenId(children.getId());
                doctorChatCustomerJson.setChildrenName(children.getName());
            }
            if(null != doctorChatCustomer.getAdminId()){
                Admin admin = adminRepository.findOne(doctorChatCustomer.getAdminId());
                if(admin!=null) {
                    doctorChatCustomerJson.setAdminIcon(admin.getIcon());
                    doctorChatCustomerJson.setAdminId(admin.getId());
                    doctorChatCustomerJson.setAdminName(admin.getName());
                }
            }
            doctorChatCustomerJsonList.add(doctorChatCustomerJson);
        }
        ResultsWrapper<DoctorChatCustomerResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(doctorChatCustomerJsonList);
        resultsWrapper.setPages(doctorChatCustomerPage.getTotalPages());
        resultsWrapper.setTotal(doctorChatCustomerPage.getTotalElements());
        return resultsWrapper;
    }


}
