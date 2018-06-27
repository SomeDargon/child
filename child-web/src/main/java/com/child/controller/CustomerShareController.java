package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.PicProcessType;
import com.child.dao.CustomerRepository;
import com.child.dao.CustomerShareCommentRepository;
import com.child.dao.CustomerShareRepository;
import com.child.dao.NorImageRepository;
import com.child.entity.WeiXinCustomer;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerShare;
import com.child.entity.customer.CustomerShareComment;
import com.child.entity.image.NorImage;
import com.child.enums.RoleEnum;
import com.child.form.CustomerShareResponseFrom;
import com.child.json.customer.CustomerShareJson;
import com.child.json.customer.CustomerShareResponseJson;
import com.child.json.image.NorImageJson;
import com.child.service.NorImageService;
import com.child.service.WeiXinCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/customerShare")
@Api(value = "共享之家")
public class CustomerShareController  {

    @Autowired
    private CustomerShareRepository customerShareRepository;
    @Autowired
    private CustomerShareCommentRepository customerShareCommentRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private NorImageService norImageService;

    @Autowired
    private NorImageRepository norImageRepository;

    @Autowired
    private WeiXinCustomerService weiXinCustomerService;
    @ApiOperation(value = "保存共享之家")
    @PostMapping(value = "/save")
    public CustomerShareResponseFrom saveCustomerShare(
            HttpServletRequest request,
            @RequestBody CustomerShareResponseFrom customerShareJson){
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        Customer customer = new Customer();
        if(weiXinCustomer.getType().equals(RoleEnum.ASSISTANT.getCode())){
            customer = customerRepository.findOne(weiXinCustomer.getDoctorId());
        }
        CustomerShare customerShare = null;
        if(null == customerShareJson.getId()) {
            customerShare = new CustomerShare();
            customerShare.setAddTime(new Date());
            customerShare.setCustomerId(customer.getId());
        }else{
            customerShare = customerShareRepository.findOne(customerShareJson.getId());
            customerShare.setUpdateTime(new Date());
        }
        BeanCopierUtils.getBeanCopier(CustomerShareJson.class, CustomerShare.class);
        BeanCopierUtils.copyProperties(customerShareJson,customerShare);
        customerShareRepository.save(customerShare);
        if(null != customerShareJson.getImageJsonList() && !customerShareJson.getImageJsonList().equals("")) {
            if (customerShareJson.getImageJsonList().size() > 0) {

                for(Long imageId:customerShareJson.getImageJsonList()){
                    NorImage norImage =  norImageService.findOne(imageId);
                    norImage.setObjectId(customerShareJson.getId());
                    norImageService.save(norImage);
                }
            }
        }
        BeanCopierUtils.getBeanCopier(CustomerShare.class, CustomerShareJson.class);
        BeanCopierUtils.copyProperties(customerShare,customerShareJson);
        return customerShareJson;
    }
    /**
     * @return
     */
    @ApiOperation(value = "获取共享之家列表")
    @GetMapping(value = "/getAllCustomerShare")
    public ResultsWrapper<CustomerShareResponseJson> getAllCustomerShare(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<CustomerShare> customerSharePage = null;
        if(null == title || title.equals("")) {
            customerSharePage = customerShareRepository.findAll(pageable);
        }else{
            customerSharePage = customerShareRepository.findByTitle(title,pageable);
        }
        List<CustomerShareResponseJson> customerShareJsonList = new ArrayList<>();
        List<CustomerShare> customerShareList = customerSharePage.getContent();
        for(CustomerShare customerShare:customerShareList){
            CustomerShareResponseJson customerShareJson = new CustomerShareResponseJson();
            BeanCopierUtils.getBeanCopier(CustomerShare.class, CustomerShareJson.class);
            BeanCopierUtils.copyProperties(customerShare,customerShareJson);
            List<NorImage> NorImageList = norImageRepository.findByObjectIdAndType(customerShare.getId(),4);
            List<NorImageJson> NorImageJsonList = new ArrayList<>();
            if(NorImageList.size() >0) {
                for (NorImage NorImage:NorImageList) {
                    NorImageJson NorImageJson = new NorImageJson();
                    BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                    BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                    NorImageJsonList.add(NorImageJson);
                }
            }
            List<CustomerShareComment> customerShareCommentList = customerShareCommentRepository.findByCustomerShareIdAndReplyCustomerIdIsNull(customerShare.getId());
            customerShareJson.setCommentCount(customerShareCommentList.size());
            customerShareJson.setImageJsonList(NorImageJsonList);
            Customer customer = customerRepository.findOne(customerShare.getCustomerId());
            customerShareJson.setCustomerName(customer.getName());
            if(null != customer.getIcon() && !customer.getIcon().equals("")) {
                customerShareJson.setCustomerIcon(customer.getIcon());
            }
            customerShareJsonList.add(customerShareJson);
        }
        ResultsWrapper<CustomerShareResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(customerShareJsonList);
        resultsWrapper.setPages(customerSharePage.getTotalPages());
        resultsWrapper.setTotal(customerSharePage.getTotalElements());
        return resultsWrapper;
    }

    @ApiOperation(value = "获取共享之家详情")
    @GetMapping(value = "/getCustomerShareById")
    public CustomerShareResponseJson getCustomerShareById(
            @ApiParam(value = "共享之家id",required = true)
            @RequestParam(value = "id") Long id){

        CustomerShare customerShare = customerShareRepository.findOne(id);
        if(null == customerShare){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        CustomerShareResponseJson customerShareJson = new CustomerShareResponseJson();
        BeanCopierUtils.getBeanCopier(CustomerShare.class,CustomerShareJson.class);
        BeanCopierUtils.copyProperties(customerShare,customerShareJson);
        List<NorImage> NorImageList = norImageRepository.findByObjectIdAndType(customerShare.getId(),4);
        List<NorImageJson> NorImageJsonList = new ArrayList<>();
        if(NorImageList.size() >0) {
            for (NorImage NorImage:NorImageList) {
                NorImageJson NorImageJson = new NorImageJson();
                BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                NorImageJsonList.add(NorImageJson);
            }
        }
        List<CustomerShareComment> customerShareCommentList = customerShareCommentRepository.findByCustomerShareIdAndReplyCustomerIdIsNull(customerShare.getId());
        customerShareJson.setCommentCount(customerShareCommentList.size());
        customerShareJson.setImageJsonList(NorImageJsonList);
        Customer customer = customerRepository.findOne(customerShare.getCustomerId());
        customerShareJson.setCustomerName(customer.getName());
        if(null != customer.getIcon() && !customer.getIcon().equals("")) {
            customerShareJson.setCustomerIcon(customer.getIcon());
        }
        return customerShareJson;
    }

    @ApiOperation(value = "删除共享之家")
    @DeleteMapping(value = "/deleteCustomerShareById")
    public void deleteCustomerShareById(
            @ApiParam(value = "共享之家id",required = true)
            @RequestParam(value = "id") Long id){
        Customer customer = new Customer();
        CustomerShare customerShare = customerShareRepository.findOne(id);
        if(null == customerShare){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        if(!customer.getId().equals(customerShare.getCustomerId())){
            throw new BusinessException(ErrorCode.NOT_DELETE_OTHERS);
        }
        customerShareRepository.delete(customerShare);
        List<NorImage> norImageList = norImageRepository.findByObjectIdAndType(customerShare.getId(),4);
        if(norImageList.size() > 0){
            norImageRepository.delete(norImageList);
        }
        List<CustomerShareComment> customerShareCommentList = customerShareCommentRepository.findByCustomerShareId(customerShare.getId());
        if(customerShareCommentList.size() > 0){
            customerShareCommentRepository.delete(customerShareCommentList);
        }
    }





}
