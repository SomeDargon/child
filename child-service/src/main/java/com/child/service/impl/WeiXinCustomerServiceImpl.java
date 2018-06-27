package com.child.service.impl;

import com.child.dao.WeiXinCustomerRepository;
import com.child.entity.WeiXinCustomer;
import com.child.service.WeiXinCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by somedragon on 2018/3/21.
 */
@Service
public class WeiXinCustomerServiceImpl implements WeiXinCustomerService {

    @Autowired
    private WeiXinCustomerRepository weiXinCustomerRepository;

    @Override
    public WeiXinCustomer findByOpenId(String openId) {
        return weiXinCustomerRepository.findByOpenId(openId);
    }

    @Override
    public WeiXinCustomer findById(Long id) {
        return weiXinCustomerRepository.findOne(id);
    }

    @Override
    public WeiXinCustomer save(WeiXinCustomer weiXinCustomer) {
        return weiXinCustomerRepository.save(weiXinCustomer);
    }
}
