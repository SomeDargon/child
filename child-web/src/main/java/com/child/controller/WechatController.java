package com.child.controller;

import com.child.common.config.ProjectUrlConfig;
import com.child.dao.AdminRepository;
import com.child.dao.CustomerConsultCountRepository;
import com.child.dao.CustomerRepository;
import com.child.entity.WeiXinCustomer;
import com.child.entity.admin.Admin;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerConsultCount;
import com.child.entity.doctor.Doctor;
import com.child.enums.ResultEnum;
import com.child.enums.RoleEnum;
import com.child.exception.childException;
import com.child.service.DoctorService;
import com.child.service.WeiXinCustomerService;
import com.child.utils.ResultVOUtil;
import com.child.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;

/**
 */
@Controller
@RequestMapping("/wechat")
@Api(value = "微信登录管理")
@Slf4j
public class WechatController {

    public static String URL = "http://www.bensonchen.cn/rxdoc/#/";
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxMpService wxOpenService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;
    @Autowired
    private CustomerConsultCountRepository customerConsultCountRepository;
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AdminRepository adminRepository;

    HttpSession httpSession;




    @GetMapping("/userInfo")
    @ApiOperation(value = "第二部获取openId", notes = "")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl, HttpServletRequest request) {
        httpSession =  request.getSession();
        WxMpUser wxMpUser ;
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new childException(
                    ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.error("【微信网页授权】创建用户"+openId);
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findByOpenId(openId);
        if(weiXinCustomer==null){
            try {
                weiXinCustomer = new WeiXinCustomer();
                weiXinCustomer.setOpenId(openId);
                wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, "zh_CN");
                weiXinCustomer.setNickname(wxMpUser.getNickname());
                weiXinCustomer.setHeadimgurl(wxMpUser.getHeadImgUrl());
                weiXinCustomer.setSex(wxMpUser.getSex());
                weiXinCustomer.setCreateDate(new Date());
                weiXinCustomer.setCountry(wxMpUser.getCountry());
                weiXinCustomer.setCity(wxMpUser.getCity());
                weiXinCustomer.setType(0);
                weiXinCustomer =  weiXinCustomerService.save(weiXinCustomer);
            } catch (WxErrorException e) {
                log.info("获取信息错误");
                e.printStackTrace();
            }
            return "false";
        }
        if(weiXinCustomer.getDoctorId()==null||weiXinCustomer.getDoctorId().equals("")){
            return "false";
        }
        httpSession.setAttribute("customerId",weiXinCustomer.getId());
        return "redirect:" + URL+returnUrl;
    }



}
