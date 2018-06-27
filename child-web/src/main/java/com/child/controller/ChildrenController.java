package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.DatetimeUtil;
import com.child.common.utils.PicProcessType;
import com.child.dao.ChildrenRepository;
import com.child.entity.children.Children;
import com.child.entity.customer.Customer;
import com.child.json.children.ChildrenJson;
import com.child.service.NorImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/children")
@Api(value = "宝宝管理")
public class ChildrenController  {

    @Autowired
    private ChildrenRepository childrenRepository;
    @Autowired
    private NorImageService norImageService;



    @ApiOperation(value = "保存宝宝")
    @PostMapping(value = "/save")
    public ChildrenJson saveChildren(
            @RequestBody ChildrenJson childrenJson)throws Exception{
        Customer customer = new Customer();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(DatetimeUtil.parseDate(childrenJson.getBirthday()));
        c2.setTime(new Date());
        if (c1.after(c2)) {
            throw new BusinessException(ErrorCode.BIRTHDAY_NOT_OVER_NOW);
        }
        Children children = null;
        if(null == childrenJson.getId()) {
            children = new Children();
            children.setAddTime(new Date());
            children.setCustomerId(customer.getId());
            children.setStatus(1);
        }else{
            children = childrenRepository.findOne(childrenJson.getId());
            children.setUpdateTime(new Date());
        }
        BeanCopierUtils.getBeanCopier(ChildrenJson.class, Children.class);
        BeanCopierUtils.copyProperties(childrenJson,children);
        if(null != childrenJson.getImageJsonList() && !childrenJson.getImageJsonList().equals("")) {
            if (childrenJson.getImageJsonList().size() > 0) {
                if (null != childrenJson.getImageJsonList().get(0).getData() && !childrenJson.getImageJsonList().get(0).getData().equals("")) {
                    String icon = norImageService.save(childrenJson.getImageJsonList().get(0).getData(), childrenJson.getImageJsonList().get(0).getOriginalName(), PicProcessType.ICON, childrenJson.getImageJsonList().get(0).getFileType(), children.getId(), null, false);
                    children.setIcon(icon);
                }
            }
        }
        if(null == childrenJson.getId()){
            List<Children> childrenList = childrenRepository.findByCustomerIdAndStatus(customer.getId(),1);
            if(childrenList.size() >= 1) {
                children.setCurrentChildren(2);
            }else{
                children.setCurrentChildren(1);
            }
        }
        childrenRepository.save(children);
        BeanCopierUtils.getBeanCopier(Children.class, ChildrenJson.class);
        BeanCopierUtils.copyProperties(children,childrenJson);
        return childrenJson;
    }
    /**
     * 获取宝宝列表
     * @return
     */
    @ApiOperation(value = "获取宝宝列表")
    @GetMapping(value = "/getAllChildren")
    public ResultsWrapper<ChildrenJson> getAllChildren(
            @RequestParam(value = "childrenId",required = false) Long childrenId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Customer customer = new Customer();
        if(null != childrenId && !childrenId.equals("")) {
            List<Children> childrenList = childrenRepository.findByCustomerIdAndStatus(customer.getId(),1);
            for(Children children :childrenList) {
                if(children.getId().equals(childrenId)) {
                    children.setCurrentChildren(1);
                }else{
                    children.setCurrentChildren(2);
                }
                childrenRepository.save(children);
            }
        }
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<Children> childrenPage = childrenRepository.findByCustomerIdAndStatus(customer.getId(),pageable,1);
        List<ChildrenJson> childrenJsonList = new ArrayList<>();
        List<Children> childrenList = childrenPage.getContent();
        for(Children children:childrenList){
            ChildrenJson childrenJson = new ChildrenJson();
            childrenJson.setAge(DatetimeUtil.countAge(DatetimeUtil.parseDate(children.getBirthday())));
            BeanCopierUtils.getBeanCopier(Children.class, ChildrenJson.class);
            BeanCopierUtils.copyProperties(children,childrenJson);
            childrenJsonList.add(childrenJson);
        }
        ResultsWrapper<ChildrenJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(childrenJsonList);
        resultsWrapper.setPages(childrenPage.getTotalPages());
        resultsWrapper.setTotal(childrenPage.getTotalElements());
        return resultsWrapper;
    }

    @ApiOperation(value = "获取宝宝详情")
    @GetMapping(value = "/getChildrenById")
    public ChildrenJson getChildrenById(
            @ApiParam(value = "宝宝id",required = true)
            @RequestParam(value = "id") Long id){

        Children children = childrenRepository.findOne(id);
        if(null == children){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        ChildrenJson childrenJson = new ChildrenJson();
        BeanCopierUtils.getBeanCopier(Children.class,ChildrenJson.class);
        BeanCopierUtils.copyProperties(children,childrenJson);
        return childrenJson;
    }

    @ApiOperation(value = "删除宝宝")
    @DeleteMapping(value = "/deleteChildrenById")
    public void deleteChildrenById(
            @ApiParam(value = "宝宝id",required = true)
            @RequestParam(value = "id") Long id){
        Children children = childrenRepository.findOne(id);
        if(null == children){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        children.setStatus(2);
        childrenRepository.save(children);
        if(children.getCurrentChildren().equals(1)){
            Sort sort = new Sort(Sort.Direction.DESC, "addTime");
            List<Children> childrenList = childrenRepository.findByStatus(sort, 1);
            if(childrenList.size() < 0) {
                Children children1 = childrenList.get(0);
                children1.setCurrentChildren(1);
                childrenRepository.save(children1);
            }
        }
    }

}
