package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.dao.CustomerActivityRecordRepository;
import com.child.dao.CustomerRepository;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerActivityRecord;
import com.child.json.customer.CustomerActivityRecordJson;
import com.child.json.customer.CustomerActivityRecordResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/customerActivityRecord")
@Api(value = "最新活动报名")
public class CustomerActivityRecordController  {

    @Autowired
    private CustomerActivityRecordRepository customerActivityRecordRepository;
    @Autowired
    private CustomerRepository customerRepository;

//    @ApiOperation(value = "保存最新活动报名")
//    @PostMapping(value = "/save")
//    public CustomerActivityRecordJson saveCustomerActivityRecord(
//            @RequestBody CustomerActivityRecordJson customerActivityRecordJson){
//        Customer customer = new Customer();
//
//        CustomerActivityRecord customerActivityRecord = null;
//
//        if(null == customerActivityRecordJson.getId()) {
//            customerActivityRecord = new CustomerActivityRecord();
//            customerActivityRecord.setCustomerId(customer.getId());
//            customerActivityRecord.setAddTime(new Date());
//        }else{
//            customerActivityRecord = customerActivityRecordRepository.findOne(customerActivityRecordJson.getId());
//        }
//        BeanCopierUtils.getBeanCopier(CustomerActivityRecordJson.class, CustomerActivityRecord.class);
//        BeanCopierUtils.copyProperties(customerActivityRecordJson,customerActivityRecord);
//        customerActivityRecordRepository.save(customerActivityRecord);
//        BeanCopierUtils.getBeanCopier(CustomerActivityRecord.class, CustomerActivityRecordJson.class);
//        BeanCopierUtils.copyProperties(customerActivityRecord,customerActivityRecordJson);
//        return customerActivityRecordJson;
//    }

    @ApiOperation(value = "获取最新活动报名详情")
    @GetMapping(value = "/getCustomerActivityRecordById")
    public CustomerActivityRecordResponseJson getCustomerActivityRecordById(
            @ApiParam(value = "最新活动报名id",required = true)
            @RequestParam(value = "id") Long id){

        CustomerActivityRecord customerActivityRecord = customerActivityRecordRepository.findOne(id);
        if(null == customerActivityRecord){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        CustomerActivityRecordResponseJson customerActivityRecordJson = new CustomerActivityRecordResponseJson();
        BeanCopierUtils.getBeanCopier(CustomerActivityRecord.class,CustomerActivityRecordResponseJson.class);
        BeanCopierUtils.copyProperties(customerActivityRecord,customerActivityRecordJson);
        Customer customer = customerRepository.findOne(customerActivityRecord.getCustomerId());
        customerActivityRecordJson.setCustomerId(customer.getId());
        customerActivityRecordJson.setCustomerName(customer.getName());
        if(null != customer.getIcon() && !customer.getIcon().equals("")) {
            customerActivityRecordJson.setIcon(customer.getIcon());
        }
        return customerActivityRecordJson;
    }


}
