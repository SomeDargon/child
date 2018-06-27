package com.child.dao;

import com.child.entity.WeiXinCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by somedragon on 2018/3/21.
 */
public interface WeiXinCustomerRepository extends JpaRepository<WeiXinCustomer, Long>{

    WeiXinCustomer findByOpenId(String openId);

}
