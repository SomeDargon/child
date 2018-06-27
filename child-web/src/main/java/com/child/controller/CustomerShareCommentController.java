package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.dao.CustomerRepository;
import com.child.dao.CustomerShareCommentRepository;
import com.child.entity.WeiXinCustomer;
import com.child.entity.customer.Customer;
import com.child.entity.customer.CustomerShare;
import com.child.entity.customer.CustomerShareComment;
import com.child.enums.RoleEnum;
import com.child.json.customer.CustomerShareCommentJson;
import com.child.json.customer.CustomerShareCommentReplyJson;
import com.child.json.customer.CustomerShareCommentResponseJson;
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
@RequestMapping(value = "/customerShareComment")
@Api(value = "共享之家评论")
public class CustomerShareCommentController  {

    @Autowired
    private CustomerShareCommentRepository customerShareCommentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private WeiXinCustomerService weiXinCustomerService;
    @ApiOperation(value = "保存共享之家评论")
    @PostMapping(value = "/save")
    public CustomerShareCommentJson saveCustomerShareComment(
            @RequestBody CustomerShareCommentJson customerShareCommentJson,
            HttpServletRequest request
            ){
        Customer customer = new Customer();
        Long id = (Long)request.getSession().getAttribute("customerId");
        WeiXinCustomer weiXinCustomer = weiXinCustomerService.findById(id);
        if(weiXinCustomer.getType().equals(RoleEnum.ASSISTANT.getCode())){
            customer = customerRepository.findOne(weiXinCustomer.getDoctorId());
        }else {
            return null;
        }

        CustomerShareComment customerShareComment = null;
        if(null == customerShareCommentJson.getId()) {
            customerShareComment = new CustomerShareComment();
            customerShareComment.setAddTime(new Date());
            if(null != customerShareCommentJson.getCustomerShareId() && !customerShareCommentJson.getCustomerShareId().equals("")){
                customerShareComment.setCustomerId(customer.getId());
            }else{
                customerShareComment.setReplyCustomerId(customer.getId());
            }
        }
        BeanCopierUtils.getBeanCopier(CustomerShareCommentJson.class, CustomerShareComment.class);
        BeanCopierUtils.copyProperties(customerShareCommentJson,customerShareComment);
        customerShareCommentRepository.save(customerShareComment);
        BeanCopierUtils.getBeanCopier(CustomerShareComment.class, CustomerShareCommentJson.class);
        BeanCopierUtils.copyProperties(customerShareComment,customerShareCommentJson);
        return customerShareCommentJson;
    }

    /**
     * @return
     */
    @ApiOperation(value = "获取共享之家评论列表")
    @GetMapping(value = "/getAllCustomerShareComment")
    public ResultsWrapper<CustomerShareCommentResponseJson> getAllCustomerShareComment(
            @RequestParam(value = "customerShareId") Long customerShareId,
            @RequestParam(value = "customerId",required = false) Long customerId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<CustomerShareComment> customerShareCommentPage = null;
        if(null == customerId || customerId.equals("")) {
            customerShareCommentPage = customerShareCommentRepository.findByCustomerShareId(customerShareId, pageable);
        }else{
            customerShareCommentPage = customerShareCommentRepository.findByCustomerId(customerId,pageable);
        }
        List<CustomerShareCommentResponseJson> customerShareCommentJsonList = new ArrayList<>();
        List<CustomerShareComment> customerShareCommentList = customerShareCommentPage.getContent();
        for(CustomerShareComment customerShareComment:customerShareCommentList){
            Customer customer = customerRepository.findOne(customerShareComment.getCustomerId());
            CustomerShareCommentResponseJson customerShareCommentJson = new CustomerShareCommentResponseJson();
            BeanCopierUtils.getBeanCopier(CustomerShareComment.class, CustomerShareCommentJson.class);
            BeanCopierUtils.copyProperties(customerShareComment, customerShareCommentJson);
            if(2 !=customer.getStatus()) {
                customerShareCommentJson.setCustomerName(customer.getName());
                if(null != customer.getIcon() && !customer.getIcon().equals("")) {
                    customerShareCommentJson.setCustomerIcon(customer.getIcon());
                }
                customerShareCommentJson.setCustomerId(customer.getId());
            }
            if(null !=customerShareComment.getReplyCustomerId() && !customerShareComment.getReplyCustomerId().equals("")){
                Customer replyCustomer = customerRepository.findOne(customerShareComment.getReplyCustomerId());
                if(2 !=replyCustomer.getStatus()) {
                    customerShareCommentJson.setReplyCustomerName(replyCustomer.getName());
                    if(null != customer.getIcon() && !customer.getIcon().equals("")) {
                        customerShareCommentJson.setReplyCustomerIcon(replyCustomer.getIcon());
                    }
                    customerShareCommentJson.setReplyCustomerId(replyCustomer.getId());
                }
            }
            /*List<CustomerShareComment> customerShareCommentList1 = customerShareCommentRepository.findByCustomerShareIdAndReplyCustomerIdIsNotNull(customerShareComment.getCustomerShareId());
            if(customerShareCommentList1.size() > 0){
                List<CustomerShareCommentReplyJson> customerShareCommentReplyJsonList = new ArrayList<>();
                for(CustomerShareComment customerShareComment1:customerShareCommentList1){
                    CustomerShareCommentReplyJson customerShareCommentReplyJson = new CustomerShareCommentReplyJson();
                    Customer replyCustomer = customerRepository.findOne(customerShareComment1.getReplyCustomerId());
                    if(2 !=replyCustomer.getStatus()) {
                        customerShareCommentReplyJson.setReplyCustomerName(replyCustomer.getName());
                        if(null != customer.getIcon() && !customer.getIcon().equals("")) {
                            customerShareCommentReplyJson.setReplyCustomerIcon(replyCustomer.getIcon());
                        }
                        customerShareCommentReplyJson.setReplyCustomerId(replyCustomer.getId());
                    }
                }
                customerShareCommentJson.setCustomerShareCommentReplyList(customerShareCommentReplyJsonList);
            }*/
            customerShareCommentJsonList.add(customerShareCommentJson);
        }
        ResultsWrapper<CustomerShareCommentResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(customerShareCommentJsonList);
        resultsWrapper.setPages(customerShareCommentPage.getTotalPages());
        resultsWrapper.setTotal(customerShareCommentPage.getTotalElements());
        return resultsWrapper;
    }


    @ApiOperation(value = "删除共享之家评论")
    @DeleteMapping(value = "/deleteCustomerShareCommentById")
    public void deleteCustomerShareCommentById(
            @ApiParam(value = "共享之家评论id",required = true)
            @RequestParam(value = "id") Long id){
        Customer customer = new Customer();
        CustomerShareComment customerShareComment = customerShareCommentRepository.findOne(id);
        if(null == customerShareComment){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        if(!customer.getId().equals(customerShareComment.getCustomerId())){
            throw new BusinessException(ErrorCode.NOT_DELETE_OTHERS);
        }
        customerShareCommentRepository.delete(customerShareComment);
    }


}
