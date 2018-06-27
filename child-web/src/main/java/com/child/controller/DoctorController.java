package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.PicProcessType;
import com.child.common.utils.ip.IPSeeker;
import com.child.dao.*;
import com.child.dto.DoctorDTO;
import com.child.entity.Province;
import com.child.entity.WeiXinCustomer;
import com.child.entity.admin.Admin;
import com.child.entity.customer.Customer;
import com.child.entity.doctor.*;
import com.child.enums.RoleEnum;
import com.child.form.DoctorForm;
import com.child.json.doctor.DoctorJson;
import com.child.json.doctor.DoctorNameJson;
import com.child.service.DoctorService;
import com.child.service.NorImageService;
import com.child.service.WeiXinCustomerService;
import com.child.utils.ResultVOUtil;
import com.child.vo.DoctorVO;
import com.child.vo.ResultVO;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.glassfish.jersey.internal.inject.Custom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping(value = "/doctor")
@Api(value = "医生")
public class DoctorController {
    HttpSession httpSession;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private NorImageService norImageService;

    @Autowired
    private ConsultPriceRepository consultPriceRepository;
    @Autowired
    private DoctorConsultPriceRepository doctorConsultPriceRepository;
    @Autowired
    private DoctorConsultRepository doctorConsultRepository;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;

    @ApiOperation(value = "获取医生详情")
    @GetMapping(value = "/getDoctorById")
    public DoctorJson getDoctorById(HttpServletRequest request){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        Doctor doctor = doctorRepository.findOne(weiXinCustomer.getDoctorId()) ;
        if(null == doctor){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        DoctorJson doctorJson = new DoctorJson();
        BeanCopierUtils.getBeanCopier(Doctor.class,DoctorJson.class);
        BeanCopierUtils.copyProperties(doctor,doctorJson);
        return doctorJson;
    }


    @ApiOperation(value = "获取医生信息")
    @GetMapping(value = "/getDoctorDetail")
    public DoctorDTO getDoctorDetail(HttpServletRequest request
    ){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        DoctorDTO doctor = doctorService.findByDoctorId( weiXinCustomer.getDoctorId());
        return doctor;
    }

    @ApiOperation(value = "保存医生信息")
    @PostMapping(value = "/saveDoctor")
    public ResultVO<Long> saveDoctor(
            @RequestBody DoctorForm doctorForm
    ){
        Doctor doctor = doctorService.save(doctorForm);
        return ResultVOUtil.success(doctor.getId());
    }

    @ApiOperation(value = "获取所有医生信息")
    @PostMapping(value = "/doctorAll")
    public ResultVO<Long> findDoctor(
    ){
        List<DoctorVO> doctor = doctorService.findAll();
        return ResultVOUtil.success(doctor);
    }

    @ApiOperation(value = "通过医生id获取医生信息")
    @GetMapping(value = "/getDoctorIdDetail")
    public DoctorDTO getDoctorIdDetail(@RequestParam Long id
    ){
        DoctorDTO doctor = doctorService.findByDoctorId( id);
        return doctor;
    }

    @GetMapping("/loginDoctor")
    @ApiOperation(value = "登录医生", notes = "")
    public ResultVO loginDoctor(HttpServletRequest request){
        httpSession =  request.getSession();
        httpSession.setAttribute("customerId", 4L);
        return ResultVOUtil.success("医生登录成功");
    }

    @GetMapping("/loginZuShou")
    @ApiOperation(value = "登录小组手", notes = "")
    public ResultVO loginZuShou(HttpServletRequest request){
        httpSession =  request.getSession();
        httpSession.setAttribute("customerId", 2L);
        return ResultVOUtil.success("小组手登录成功");
    }

    @GetMapping("/getUserType")
    @ApiOperation(value = "获取用户信息", notes = "")
    public ResultVO getUserType(HttpServletRequest request){
        httpSession =  request.getSession();
        Long id = (Long)httpSession.getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        Map<String, Object> map = new HashMap<>();
        if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())){
            Doctor doctor = doctorService.findByid(weiXinCustomer.getDoctorId());
            map.put("name", doctor.getName());
            map.put("type", RoleEnum.DOCTOR.getCode());
        }else if(weiXinCustomer.getType().equals(RoleEnum.ASSISTANT.getCode())){
            Customer admin = customerRepository.findOne(weiXinCustomer.getDoctorId());
            map.put("name", admin.getName());
            map.put("type", RoleEnum.ASSISTANT.getCode());
        }
        return ResultVOUtil.success(map);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取用户信息", notes = "")
    public ResultVO getUserInfo(HttpServletRequest request){
        httpSession =  request.getSession();
        Long id = (Long)httpSession.getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        if(weiXinCustomer.getType().equals(RoleEnum.DOCTOR.getCode())){
            Doctor doctor = doctorService.findByid(weiXinCustomer.getDoctorId());
            return ResultVOUtil.success(doctor);
        }else if(weiXinCustomer.getType().equals(RoleEnum.ASSISTANT.getCode())){
            Customer admin = customerRepository.findOne(weiXinCustomer.getDoctorId());
            return ResultVOUtil.success(admin);
        }
        return ResultVOUtil.success("");
    }
}
