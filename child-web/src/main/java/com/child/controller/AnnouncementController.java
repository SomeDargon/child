package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.dao.AnnouncementRepository;
import com.child.entity.admin.Admin;
import com.child.entity.announcement.Announcement;
import com.child.json.announcement.AnnouncementJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/announcement")
@Api(value = "公告管理")
@ApiIgnore
public class AnnouncementController  {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @ApiOperation(value = "保存公告")
    @PostMapping(value = "/save")
    public Announcement saveAnnouncement(
            @RequestBody AnnouncementJson announcementJson){
            Admin admin = new Admin();
            Announcement announcement = null;
            if(null == announcementJson.getId()) {
                announcement = new Announcement();
                announcement.setUserId(admin.getId());
                announcement.setAddTime(new Date());
                announcement.setStatus(1);
            }else{
                announcement = announcementRepository.findOne(announcementJson.getId());
            }
            BeanCopierUtils.getBeanCopier(AnnouncementJson.class, Announcement.class);
            BeanCopierUtils.copyProperties(announcementJson,announcement);
            announcementRepository.save(announcement);
            BeanCopierUtils.getBeanCopier(Announcement.class, AnnouncementJson.class);
            BeanCopierUtils.copyProperties(announcement,announcementJson);
            return announcement;
    }

    /**
     * 获取公告列表
     * @return
     */
    @ApiOperation(value = "获取公告列表")
    @GetMapping(value = "/getAllAnnouncement/{type}")
    public ResultsWrapper<AnnouncementJson> getAllAnnouncement(
            @ApiParam(value = "公告类型",required = true)
            @PathVariable(value = "type") Integer type,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<Announcement> announcementPage = announcementRepository.findByType(type,pageable);
        List<AnnouncementJson> announcementJsonList = new ArrayList<>();
        List<Announcement> announcementList = announcementPage.getContent();
        for(Announcement announcement:announcementList){
            AnnouncementJson announcementJson = new AnnouncementJson();
            BeanCopierUtils.getBeanCopier(Announcement.class, AnnouncementJson.class);
            BeanCopierUtils.copyProperties(announcement,announcementJson);
            announcementJsonList.add(announcementJson);
        }
        ResultsWrapper<AnnouncementJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(announcementJsonList);
        resultsWrapper.setPages(announcementPage.getTotalPages());
        resultsWrapper.setTotal(announcementPage.getTotalElements());
        return resultsWrapper;
    }


    @ApiOperation(value = "获取公告详情")
    @GetMapping(value = "/getAnnouncementById")
    public AnnouncementJson getAnnouncementById(
            //@RequestHeader(ConstantsUtils.AUTH_TOKEN_HEADER_NAME) String authToken,
            @ApiParam(value = "公告id",required = true)
            @RequestParam(value = "id") Long id){
        Announcement announcement = announcementRepository.findOne(id);
        if(null == announcement){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        AnnouncementJson announcementJson = new AnnouncementJson();
        BeanCopierUtils.getBeanCopier(Announcement.class,AnnouncementJson.class);
        BeanCopierUtils.copyProperties(announcement,announcementJson);
        return announcementJson;
    }

    @ApiOperation(value = "删除公告")
    @DeleteMapping(value = "/deleteAnnouncementById")
    public void deleteAnnouncementById(
            @ApiParam(value = "公告id",required = true)
            @RequestParam(value = "id") Long id){
            Announcement announcement = announcementRepository.findOne(id);
            if(null == announcement){
                throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
            }
            announcement.setStatus(2);
        announcementRepository.save(announcement);
    }


}
