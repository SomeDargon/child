package com.child.service;


import com.child.entity.WeiXinCustomer;

/**
 * Created by somedragon on 2018/3/21.
 */
public interface WeiXinCustomerService {

    WeiXinCustomer findByOpenId(String openId);

    WeiXinCustomer findById(Long id);

    WeiXinCustomer save(WeiXinCustomer weiXinCustomer);

}
