package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.DatetimeUtil;
import com.child.dao.ChildrenGrowRecordRepository;
import com.child.entity.children.Children;
import com.child.entity.children.ChildrenGrowRecord;
import com.child.json.children.ChildrenGrowRecordJson;
import com.child.json.children.ChildrenGrowRecordResponseJson;
import com.child.service.ChildrenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/childrenGrowRecord")
@Api(value = "宝宝生长监控")
public class ChildrenGrowRecordController {

    @Autowired
    private ChildrenGrowRecordRepository childrenGrowRecordRepository;

    @Autowired
    private ChildrenService childrenService;

    @ApiOperation(value = "保存宝宝生长监控")
    @PostMapping(value = "/save")
    public ChildrenGrowRecordJson saveChildrenGrowRecord(
            @RequestBody ChildrenGrowRecordJson childrenGrowRecordJson){

        ChildrenGrowRecord childrenGrowRecord1 = childrenGrowRecordRepository.findByMeasurementTimeAndChildrenId(childrenGrowRecordJson.getMeasurementTime(),childrenGrowRecordJson.getChildrenId());
        if(null != childrenGrowRecord1){
            childrenGrowRecordJson.setId(childrenGrowRecord1.getId());
            BeanCopierUtils.getBeanCopier(ChildrenGrowRecordJson.class, ChildrenGrowRecord.class);
            BeanCopierUtils.copyProperties(childrenGrowRecordJson,childrenGrowRecord1);
            childrenGrowRecordRepository.save(childrenGrowRecord1);
            BeanCopierUtils.getBeanCopier(ChildrenGrowRecord.class, ChildrenGrowRecordJson.class);
            BeanCopierUtils.copyProperties(childrenGrowRecord1,childrenGrowRecordJson);
            return childrenGrowRecordJson;
        }

        ChildrenGrowRecord childrenGrowRecord = null;
        if(null == childrenGrowRecordJson.getId()) {
            childrenGrowRecord = new ChildrenGrowRecord();
            childrenGrowRecord.setAddTime(new Date());
        }else{
            childrenGrowRecord = childrenGrowRecordRepository.findOne(childrenGrowRecordJson.getId());
        }
        BeanCopierUtils.getBeanCopier(ChildrenGrowRecordJson.class, ChildrenGrowRecord.class);
        BeanCopierUtils.copyProperties(childrenGrowRecordJson,childrenGrowRecord);
        childrenGrowRecordRepository.save(childrenGrowRecord);
        BeanCopierUtils.getBeanCopier(ChildrenGrowRecord.class, ChildrenGrowRecordJson.class);
        BeanCopierUtils.copyProperties(childrenGrowRecord,childrenGrowRecordJson);
        return childrenGrowRecordJson;
    }
    /**
     * 获取宝宝列表
     * @return
     */
    @ApiOperation(value = "获取宝宝生长监控列表")
    @GetMapping(value = "/getAllChildrenGrowRecord")
    public ResultsWrapper<ChildrenGrowRecordResponseJson> getAllChildrenGrowRecord(
            @ApiParam(value = "宝宝id",required = false)
            @RequestParam(value = "childrenId",required = false) Long childrenId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"measurementTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<ChildrenGrowRecord> childrenGrowRecordPage = childrenGrowRecordRepository.findByChildrenId(childrenId,pageable);
        List<ChildrenGrowRecordResponseJson> childrenGrowRecordJsonList = new ArrayList<>();
        List<ChildrenGrowRecord> childrenGrowRecordList = childrenGrowRecordPage.getContent();
        for(ChildrenGrowRecord childrenGrowRecord:childrenGrowRecordList){

            ChildrenGrowRecordResponseJson childrenGrowRecordResponseJson = new ChildrenGrowRecordResponseJson();
            BeanCopierUtils.getBeanCopier(ChildrenGrowRecord.class, ChildrenGrowRecordJson.class);
            BeanCopierUtils.copyProperties(childrenGrowRecord, childrenGrowRecordResponseJson);
            Children children = childrenService.getChildren(childrenGrowRecord.getChildrenId());
            int day = com.child.common.utils.date.DatetimeUtil.daySpan(new Date(), com.child.common.utils.date.DatetimeUtil.parseDateWithException(children.getBirthday()));
            int mine = day/32+1;
            childrenGrowRecordResponseJson.setAge(mine);
            childrenGrowRecordJsonList.add(childrenGrowRecordResponseJson);
        }
        ResultsWrapper<ChildrenGrowRecordResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(childrenGrowRecordJsonList);
        resultsWrapper.setPages(childrenGrowRecordPage.getTotalPages());
        resultsWrapper.setTotal(childrenGrowRecordPage.getTotalElements());
        return resultsWrapper;
    }

    @ApiOperation(value = "获取宝宝生长监控详情")
    @GetMapping(value = "/getChildrenGrowRecordById")
    public ChildrenGrowRecordResponseJson getChildrenGrowRecordById(
            @ApiParam(value = "宝宝生长监控id",required = true)
            @RequestParam(value = "id") Long id){

        ChildrenGrowRecord childrenGrowRecord = childrenGrowRecordRepository.findOne(id);
        if(null == childrenGrowRecord){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        ChildrenGrowRecordResponseJson childrenGrowRecordJson = new ChildrenGrowRecordResponseJson();
        BeanCopierUtils.getBeanCopier(ChildrenGrowRecord.class,ChildrenGrowRecordJson.class);
        BeanCopierUtils.copyProperties(childrenGrowRecord,childrenGrowRecordJson);
        return childrenGrowRecordJson;
    }

    @ApiOperation(value = "删除宝宝生长监控")
    @DeleteMapping(value = "/deleteChildrenGrowRecordById")
    public void deleteChildrenGrowRecordById(
            @ApiParam(value = "宝宝生长监控id",required = true)
            @RequestParam(value = "id") Long id){
        ;
        ChildrenGrowRecord childrenGrowRecord = childrenGrowRecordRepository.findOne(id);
        if(null == childrenGrowRecord){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        childrenGrowRecordRepository.delete(childrenGrowRecord);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }


}
