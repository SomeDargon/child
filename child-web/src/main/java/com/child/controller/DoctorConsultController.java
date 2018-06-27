package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.PicProcessType;
import com.child.dao.*;
import com.child.dto.ChildrentDTO;
import com.child.dto.DoctorConsultDTO;
import com.child.dto.ServiceNumDTO;
import com.child.entity.WeiXinCustomer;
import com.child.entity.children.Children;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerRefund;
import com.child.entity.doctor.*;
import com.child.entity.image.NorImage;
import com.child.enums.RoleEnum;
import com.child.json.doctor.DoctorConsultDetailJson;
import com.child.json.doctor.DoctorConsultHistoryJson;
import com.child.json.doctor.DoctorConsultJson;
import com.child.json.image.NorImageJson;
import com.child.service.ChildrenService;
import com.child.service.DoctorConsultService;
import com.child.service.NorImageService;
import com.child.service.WeiXinCustomerService;
import com.child.utils.KeyUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.service.impl.WxPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/doctorConsult")
@Api(value = "咨询")
@Slf4j
public class DoctorConsultController {

    @Autowired
    private DoctorConsultRepository doctorConsultRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ChildrenRepository childrenRepository;

    @Autowired
    private DoctorConsultPriceRepository doctorConsultPriceRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorBespeakTimeRepository doctorBespeakTimeRepository;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
     private DoctorConsultService doctorConsultService;

    @Autowired
    private WeiXinCustomerService weiXinCustomerService;

    @Autowired
    private ChildrenService childrenService;
    @Autowired
    private DoctorChatCustomerRepository doctorChatCustomerRepository;

    @ApiOperation(value = "获取咨询列表")
    @GetMapping(value = "/getAllDoctorConsult")
    public ResultsWrapper<DoctorConsultDetailJson> getAllDoctorConsult(
            @ApiParam(value = "咨询类型 1.图文咨询 2.电话咨询 3.网络诊室 4.预约门诊")
            @RequestParam(value = "type",required = false) Integer type,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size,
            HttpServletRequest request){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer =  weiXinCustomerService.findById(id);
        Integer status = weiXinCustomer.getType();
        Pageable pageable = new PageRequest(page-1,size,sort);
        QDoctorConsult doctorConsultName = QDoctorConsult.doctorConsult;
        Predicate predicate = null;
        if(null != type && !type.equals("")) {
            if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())){
                predicate = doctorConsultName.type.like("%" + type + "%").and(doctorConsultName.sysType.eq(1)).and(doctorConsultName.doctorId.eq(weiXinCustomer.getDoctorId()));
            }
        }else {
            if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())){
                predicate = doctorConsultName.sysType.eq(1).and(doctorConsultName.doctorId.eq(weiXinCustomer.getDoctorId()));
            }
            if(weiXinCustomer.getType().equals(RoleEnum.ASSISTANT.getCode())){
                predicate = doctorConsultName.sysType.eq(1);
            }
            if(weiXinCustomer.getType().equals(RoleEnum.TOURIST.getCode())){
                return null;
            }

        }
        Page<DoctorConsult> doctorConsultPage = doctorConsultRepository.findAll(predicate,pageable);
        List<DoctorConsultDetailJson> doctorConsultJsonList = new ArrayList<>();
        List<DoctorConsult> doctorConsultList = doctorConsultPage.getContent();
        for(DoctorConsult doctorConsult:doctorConsultList){
            Children children = childrenRepository.findOne(doctorConsult.getChildrenId());

            if(2 !=children.getStatus()) {
                DoctorConsultDetailJson doctorConsultDetailJson = new DoctorConsultDetailJson();
                BeanCopierUtils.getBeanCopier(DoctorConsult.class, DoctorConsultDetailJson.class);
                BeanCopierUtils.copyProperties(doctorConsult, doctorConsultDetailJson);
                if(status.equals(0)){
                    doctorConsultDetailJson.setUserStatus(doctorConsult.getUserStatus());
                }else if(status.equals(1)){
                    doctorConsultDetailJson.setUserStatus(doctorConsult.getDoctorStatus());
                }else if(status.equals(2)){
                    doctorConsultDetailJson.setUserStatus(doctorConsult.getAdminStatus());
                }

                if(doctorConsult.getType().equals(1)){
                    Sort sortx = new Sort(new Sort.Order(Sort.Direction.DESC,"addTime"));
                    List<DoctorChatCustomer> doctorChatCustomer = doctorChatCustomerRepository.findByDoctorConsultId(doctorConsult.getId(), sortx);
                    if(doctorChatCustomer.size()!=0){
                        doctorConsultDetailJson.setEndTime(doctorChatCustomer.get(doctorChatCustomer.size()-1).getAddTime());
                    }
                }
                doctorConsultDetailJson.setQuestionName(doctorConsult.getProblemContent());
                doctorConsultDetailJson.setPhone(doctorConsult.getContactPhone());
                doctorConsultDetailJson.setChildrenName(children.getName());
                Doctor doctor = doctorRepository.findOne(doctorConsult.getDoctorId());
                doctorConsultDetailJson.setDoctorName(doctor.getName());
                if(null != doctorConsult.getDoctorBespeakTimeId() && !doctorConsult.getDoctorBespeakTimeId().equals("")){
                    DoctorBespeakTime doctorBespeakTime = doctorBespeakTimeRepository.findOne(doctorConsult.getDoctorBespeakTimeId());
                    doctorConsultDetailJson.setTime(doctorBespeakTime.getTime());
                }
                doctorConsultJsonList.add(doctorConsultDetailJson);
            }
        }
        ResultsWrapper<DoctorConsultDetailJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(doctorConsultJsonList);
        resultsWrapper.setPages(doctorConsultPage.getTotalPages());
        resultsWrapper.setTotal(doctorConsultPage.getTotalElements());
        return resultsWrapper;
    }

    @ApiIgnore
    @ApiOperation(value = "获取最新咨询列表")
    @GetMapping(value = "/getNewDoctorConsult")
    public List<DoctorConsultDetailJson> getNewDoctorConsult(HttpServletRequest request){
        Customer customer = new Customer();
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer =  weiXinCustomerService.findById(id);

        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(1-1,2,sort);
        QDoctorConsult doctorConsultName = QDoctorConsult.doctorConsult;

        Predicate predicate = doctorConsultName.customerId.eq(customer.getId());
        Page<DoctorConsult> doctorConsultPage = doctorConsultRepository.findAll(predicate,pageable);
        List<DoctorConsultDetailJson> doctorConsultJsonList = new ArrayList<>();
        List<DoctorConsult> doctorConsultList = doctorConsultPage.getContent();
        for(DoctorConsult doctorConsult:doctorConsultList){
            DoctorConsultDetailJson doctorConsultDetailJson = new DoctorConsultDetailJson();
            BeanCopierUtils.getBeanCopier(DoctorConsult.class, DoctorConsultDetailJson.class);
            BeanCopierUtils.copyProperties(doctorConsult,doctorConsultDetailJson);
            Children children = childrenRepository.findOne(doctorConsult.getChildrenId());
            doctorConsultDetailJson.setChildrenName(children.getName());
            doctorConsultJsonList.add(doctorConsultDetailJson);
        }
        return doctorConsultJsonList;
    }

    @ApiOperation(value = "获取咨询详情")
    @GetMapping(value = "/getDoctorConsultById")
    public DoctorConsultDetailJson getDoctorConsultById(
            @ApiParam(value = "咨询id",required = true)
            @RequestParam(value = "id") Long id){
        DoctorConsult doctorConsult = doctorConsultRepository.findOne(id);
        if(null == doctorConsult){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        DoctorConsultDetailJson doctorConsultDetailJson = new DoctorConsultDetailJson();
        BeanCopierUtils.getBeanCopier(DoctorConsult.class,DoctorConsultDetailJson.class);
        BeanCopierUtils.copyProperties(doctorConsult,doctorConsultDetailJson);
        Children children = childrenRepository.findOne(doctorConsult.getChildrenId());
        doctorConsultDetailJson.setChildrenName(children.getName());
        return doctorConsultDetailJson;
    }


    @ApiOperation(value = "结束咨询")
    @GetMapping(value = "/overDoctorConsult")
    public void overDoctorConsult(
            @RequestParam Long id){
        DoctorConsult doctorConsult = doctorConsultRepository.findOne(id);
        if(null == doctorConsult){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        doctorConsult.setStatus(2);
        doctorConsultRepository.save(doctorConsult);
        Customer customer = customerRepository.findOne(doctorConsult.getCustomerId());
        try {
            Doctor doctor = doctorRepository.findOne(doctorConsult.getDoctorId());
            DoctorConsultPrice doctorConsultPrice = doctorConsultPriceRepository.findByTypeAndDoctorId(doctorConsult.getType().toString(),doctorConsult.getDoctorId());
            WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage
                    .builder()
                    .toUser(customer.getOpenId())
                    .templateId("XLM_el2iNgVnaqo5Ldc_meBL23_EQZ8qWSadpHdL-EM")
                    .url("http://www.bensonchen.cn/dist/#/graphicConsulting/" + doctorConsult.getId()+"")
                    .build();

            wxMpTemplateMessage.getData().add(new WxMpTemplateData("first","您好,您的咨询已经结束", "#0000FF"));
            wxMpTemplateMessage.getData().add(new WxMpTemplateData("keyword1", doctor.getName(), "#0000FF"));
            wxMpTemplateMessage.getData().add(new WxMpTemplateData("keyword2",doctorConsultPrice.getPrice()+"元", "#0000FF"));
            wxMpTemplateMessage.getData().add(new WxMpTemplateData("remark","点击查看详情", "#0000FF"));
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【微信回复消息】{}", e);
        }
    }
    @ApiOperation(value = "获取服务次数")
    @GetMapping(value = "/getServiceNum")
    public ServiceNumDTO getServiceNum(@RequestParam(required=false) String date,
                                       HttpServletRequest request
                                       ){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        if(weiXinCustomer!=null&&weiXinCustomer.getDoctorId()!=null){
            id = weiXinCustomer.getDoctorId();
        }
        return doctorConsultService.getServiceNum(id, date);
    }

    @ApiOperation(value = "我的病人")
    @GetMapping(value = "/getChildrent")
    public Page<ChildrentDTO> getChildrent(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            HttpServletRequest request
    ){
        Pageable pageable = new PageRequest(page-1,size);
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        if(weiXinCustomer!=null&&weiXinCustomer.getDoctorId()!=null){
            id = weiXinCustomer.getDoctorId();
        }
        Page<ChildrentDTO> dtos = null;
        if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())) {
            dtos = doctorConsultService.getChildrent(pageable, id, name);
        }else {
            dtos = childrenService.findAll(pageable, name);
        }
        return dtos;
    }


    @ApiOperation(value = "发起分享")
    @GetMapping(value = "/share")
    public WxJsapiSignature share(
                                  @RequestParam String url){
        Customer customer = new Customer();
        WxJsapiSignature wxJsapiSignature = new WxJsapiSignature();
        try {
            wxJsapiSignature = wxMpService.createJsapiSignature(url);
        }catch (WxErrorException e){
            log.info("获取信息错误");
            e.printStackTrace();
        }
        return wxJsapiSignature;
    }

    private static boolean checkExpired(Date startTime, int expireHours) {
        Instant start = startTime.toInstant();
        start = start.plus(expireHours, ChronoUnit.HOURS);
        return Instant.now().isAfter(start);
    }

//    @ApiOperation(value = "获取咨询列表")
//    @GetMapping(value = "/getAllDoctorConsult")
//    public Page<DoctorConsultDTO> getDoctorConsult(
//            @RequestParam(value = "page",defaultValue = "1") Integer page,
//            @RequestParam(value = "size",defaultValue = "10") Integer size
//    ){
//        Long id = 1L;
//        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
//        Long doctorId = null;
//        if(weiXinCustomer!=null&&weiXinCustomer.getDoctorId()!=null){
//            doctorId = weiXinCustomer.getDoctorId();
//        }
//        PageRequest pageRequest = new PageRequest(page-1, size);
//        Page<DoctorConsultDTO> doctorConsultDTOs = doctorConsultService.findByDoctorId(pageRequest, doctorId);
//        return doctorConsultDTOs;
//    }

    @ApiOperation(value = "通过id获取服务次数")
    @GetMapping(value = "/getDoctorIdServiceNum")
    public ServiceNumDTO getDoctorIdServiceNum(@RequestParam(required=false) String date,
                                       @RequestParam Long id
    ){
        return doctorConsultService.getServiceNum(id, date);
    }
}
