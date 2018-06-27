package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.*;
import com.child.dao.ChildrenDiaryRepository;
import com.child.dao.NorImageRepository;
import com.child.entity.children.ChildrenDiary;
import com.child.entity.image.NorImage;
import com.child.json.children.ChildrenDiaryJson;
import com.child.json.children.ChildrenDiaryResponseJson;
import com.child.json.image.NorImageJson;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/childrenDiary")
@Api(value = "宝宝日记")
public class ChildrenDiaryController  {

    @Autowired
    private ChildrenDiaryRepository childrenDiaryRepository;

    @Autowired
    private NorImageRepository NorImageRepository;

    @Autowired
    private NorImageService norImageService;

    @Autowired
    private  NorImageRepository norImageRepository;

    @ApiOperation(value = "保存宝宝日记")
    @PostMapping(value = "/save")
    public ChildrenDiaryJson saveChildrenDiary(
            @RequestBody ChildrenDiaryJson childrenDiaryJson){
        ChildrenDiary childrenDiary = null;

        if(null == childrenDiaryJson.getId()) {
            childrenDiary = new ChildrenDiary();
            childrenDiary.setAddTime(new Date());
        }else{
            childrenDiary = childrenDiaryRepository.findOne(childrenDiaryJson.getId());
        }
        BeanCopierUtils.getBeanCopier(ChildrenDiaryJson.class, ChildrenDiary.class);
        BeanCopierUtils.copyProperties(childrenDiaryJson,childrenDiary);
        childrenDiaryRepository.save(childrenDiary);
        if(null != childrenDiaryJson.getImageJsonList() && !childrenDiaryJson.getImageJsonList().equals("")) {
            if (childrenDiaryJson.getImageJsonList().size() > 0) {
                for (NorImageJson norImageJson : childrenDiaryJson.getImageJsonList()) {
                    norImageService.save(norImageJson.getData(), norImageJson.getOriginalName(), PicProcessType.CHILDREN, norImageJson.getFileType(), childrenDiary.getId(), 3, true);
                }
            }
        }
        return childrenDiaryJson;
    }
    /**
     * 获取宝宝列表
     * @return
     */
    @ApiOperation(value = "获取宝宝日记列表")
    @GetMapping(value = "/getAllChildrenDiary")
    public ResultsWrapper<ChildrenDiaryResponseJson> getAllChildrenDiary(
            @ApiParam(value = "宝宝id",required = false)
            @RequestParam(value = "childrenId") Long childrenId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<ChildrenDiary> childrenDiaryPage = childrenDiaryRepository.findBychildrenId(childrenId,pageable);
        List<ChildrenDiaryResponseJson> childrenDiaryJsonList = new ArrayList<>();
        List<ChildrenDiary> childrenDiaryList = childrenDiaryPage.getContent();
        for(ChildrenDiary childrenDiary:childrenDiaryList){
            ChildrenDiaryResponseJson childrenDiaryResponseJson = new ChildrenDiaryResponseJson();
            BeanCopierUtils.getBeanCopier(ChildrenDiary.class, ChildrenDiaryResponseJson.class);
            BeanCopierUtils.copyProperties(childrenDiary,childrenDiaryResponseJson);
            List<NorImage> NorImageList = NorImageRepository.findByObjectIdAndType(childrenDiary.getId(),3);
            List<NorImageJson> NorImageJsonList = new ArrayList<>();
            if(NorImageList.size() >0) {
                for (NorImage NorImage:NorImageList) {
                    NorImageJson NorImageJson = new NorImageJson();
                    BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                    BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                    NorImageJsonList.add(NorImageJson);
                }
            }
            childrenDiaryResponseJson.setImageJsonList(NorImageJsonList);
            childrenDiaryJsonList.add(childrenDiaryResponseJson);
        }
        ResultsWrapper<ChildrenDiaryResponseJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(childrenDiaryJsonList);
        resultsWrapper.setPages(childrenDiaryPage.getTotalPages());
        resultsWrapper.setTotal(childrenDiaryPage.getTotalElements());
        return resultsWrapper;
    }

    @ApiOperation(value = "获取宝宝日记详情")
    @GetMapping(value = "/getChildrenDiaryById")
    public ChildrenDiaryResponseJson getChildrenDiaryById(
            @ApiParam(value = "宝宝日记id",required = true)
            @RequestParam(value = "id") Long id){

        ChildrenDiary childrenDiary = childrenDiaryRepository.findOne(id);
        if(null == childrenDiary){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        ChildrenDiaryResponseJson childrenDiaryResponseJson = new ChildrenDiaryResponseJson();
        BeanCopierUtils.getBeanCopier(ChildrenDiary.class,ChildrenDiaryJson.class);
        BeanCopierUtils.copyProperties(childrenDiary,childrenDiaryResponseJson);
        List<NorImage> NorImageList = NorImageRepository.findByObjectIdAndType(childrenDiary.getId(),3);
        List<NorImageJson> NorImageJsonList = new ArrayList<>();
        if(NorImageList.size() >0) {
            for (NorImage NorImage:NorImageList) {
                NorImageJson NorImageJson = new NorImageJson();
                BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                NorImageJsonList.add(NorImageJson);
            }
        }
        childrenDiaryResponseJson.setImageJsonList(NorImageJsonList);
        return childrenDiaryResponseJson;
    }

    @ApiOperation(value = "删除宝宝日记")
    @DeleteMapping(value = "/deleteChildrenDiaryById")
    public void deleteChildrenDiaryById(
            @ApiParam(value = "宝宝日记id",required = true)
            @RequestParam(value = "id") Long id){
        ChildrenDiary childrenDiary = childrenDiaryRepository.findOne(id);
        if(null == childrenDiary){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        childrenDiaryRepository.delete(childrenDiary);
        List<NorImage> norImageList = norImageRepository.findByObjectIdAndType(childrenDiary.getId(),3);
        if(norImageList.size() > 0) {
            norImageRepository.delete(norImageList);
        }
    }



}
