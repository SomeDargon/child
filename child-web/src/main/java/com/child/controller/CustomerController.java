package com.child.controller;

import com.child.SecureTokenhelper;
import com.child.UserSession;
import com.child.UserType;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.dao.CustomerConsultCountRepository;
import com.child.dao.CustomerRepository;
import com.child.dao.UserSessionRepository;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerConsultCount;
import com.child.enums.ResultEnum;
import com.child.exception.childException;
import com.child.json.customer.CustomerJson;
import com.child.json.customer.CustomerResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by somedragon on 2018/1/16.
 */

@RestController
@RequestMapping(value = "/customer")
@Api(value = "用户管理")
@Slf4j
public class CustomerController  {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;

    @ApiOperation(value = "查询所有用户")
    @GetMapping(value = "/findAll")
    public Page<Customer> findAll(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size
    ){
        Pageable pageable = new PageRequest(page-1,size);
        return customerRepository.findAll(pageable);
    }


    @ApiOperation(value = "设置状态")
    @ApiParam(value = "0禁言 1不禁言")
    @GetMapping(value = "/setGag")
    public Customer setGag(
            @RequestParam Long id, @RequestParam Integer type
    ){
        Customer customer = customerRepository.findOne(id);
        customer.setIsGag(type);
        return customerRepository.save(customer);
    }




}
