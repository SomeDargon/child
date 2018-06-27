package com.child.controller;

import com.child.UserSession;
import com.child.common.utils.DatetimeUtil;
import com.child.dao.CustomerRefundRepository;
import com.child.dao.DoctorConsultRepository;
import com.child.dao.LatestActivityRepository;
import com.child.dao.UserSessionRepository;
import com.child.entity.article.LatestActivity;
import com.child.entity.article.QLatestActivity;
import com.child.entity.customer.CustomerRefund;
import com.child.entity.doctor.DoctorConsult;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

//@Component
//@Slf4j
//public class SystemServiceController {
//
////    @Autowired
////    private LatestActivityRepository latestActivityRepository;
////    @Autowired
////    private DoctorConsultRepository doctorConsultRepository;
////    @Autowired
////    private UserSessionRepository userSessionRepository;
////    @Autowired
////    private CustomerRefundRepository customerRefundRepository;
////    @Autowired
////    private BestPayServiceImpl bestPayService;
////
////
////    //@Scheduled(cron = "0 0/30 * * *  ?")
////    @Scheduled(cron = "0 */1 * * * ?")
////    public  void latestActivityFinish(){
////        System.out.println("开始结束最新活动");
////        List<LatestActivity> latestActivityList = latestActivityRepository.findByStatus(1);
////        if(latestActivityList.size() > 0){
////            for(LatestActivity latestActivity:latestActivityList){
////                if(checkExpired(DatetimeUtil.parseDate(latestActivity.getEndTime()),1)){
////                    latestActivity.setStatus(2);
////                    latestActivityRepository.save(latestActivity);
////                }
////            }
////        }
////
////    }
////
////    @Scheduled(cron = "0 */1 * * * ?")
////    public  void doctorConsultFinish(){
////        System.out.println("开始结束超过3天的咨询活动");
////        List<DoctorConsult> doctorConsultList = doctorConsultRepository.findByStatus(1);
////        if(doctorConsultList.size() > 0){
////            for(DoctorConsult doctorConsult:doctorConsultList){
////                if(checkExpiredDays(doctorConsult.getAddTime(),3)){
////                    doctorConsult.setStatus(2);
////                    doctorConsultRepository.save(doctorConsult);
////                }
////            }
////        }
////
////    }
////
////    @Scheduled(cron = "0 */1 * * * ?")
////    public  void doctorConsultRefound(){
////        System.out.println("开始进行退款");
////        List<CustomerRefund> customerRefundList = customerRefundRepository.findByRemarkIsNull();
////        if(customerRefundList.size() > 0){
////            for(CustomerRefund customerRefund:customerRefundList){
////                RefundRequest refundRequest = new RefundRequest();
////                refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
////                refundRequest.setOrderId(customerRefund.getOrderNumber());
////                refundRequest.setOrderAmount(customerRefund.getOrderAmount().doubleValue());
////                try {
////                    log.info("【微信退款】发起退款, request={}", JsonUtil.toJson(refundRequest));
////                    RefundResponse refundResponse = bestPayService.refund(refundRequest);
////                    log.info("【微信退款】发起退款, refundResponse={}", JsonUtil.toJson(refundResponse));
////                    customerRefund.setOutRefundNo(refundResponse.getOutRefundNo());
////                    customerRefund.setOutTradeNo(refundResponse.getOutTradeNo());
////                    customerRefund.setOutTradeTime(new Date());
////                    customerRefund.setOutTradeAmount(refundResponse.getOrderAmount().toString());
////                    customerRefund.setRemark("退款成功");
////                    customerRefundRepository.save(customerRefund);
////                }catch (Exception e){
////                    customerRefund.setRemark("退款失败");
////                    customerRefundRepository.save(customerRefund);
////                }
////
////            }
////        }
////
////    }
////
////
////    //@Scheduled(cron = "0 */1 * * * ?")
////    public  void deleteUserSession(){
////        System.out.println("开始结束昨天的用户session");
////        List<UserSession> userSessionList = userSessionRepository.findAll();
////        if(userSessionList.size() > 0){
////            for(UserSession userSession:userSessionList){
////                if(checkExpiredDaysBefore(userSession.getStartTime(),24)){
////                    userSessionRepository.delete(userSession);
////                }
////            }
////        }
////    }
////
////    private static boolean checkExpired(Date startTime, int expireHours) {
////        Instant start = startTime.toInstant();
////        start = start.plus(expireHours, ChronoUnit.MINUTES);
////        return Instant.now().isAfter(start);
////    }
////
////    private static boolean checkExpiredDays(Date startTime, int expireHours) {
////        Instant start = startTime.toInstant();
////        start = start.plus(expireHours, ChronoUnit.DAYS);
////        return Instant.now().isAfter(start);
////    }
////
////    private static boolean checkExpiredDaysBefore(Date startTime, int expireHours) {
////        Instant start = startTime.toInstant();
////        start = start.plus(expireHours, ChronoUnit.DAYS);
////        return Instant.now().isBefore(start);
////    }
//
//}
