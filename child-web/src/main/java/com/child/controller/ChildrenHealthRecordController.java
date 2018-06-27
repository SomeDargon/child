package com.child.controller;

import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.DatetimeUtil;
import com.child.common.utils.PicProcessType;
import com.child.dao.*;
import com.child.entity.children.Children;
import com.child.entity.children.ChildrenRecord;
import com.child.entity.customer.Customer;
import com.child.entity.doctor.DoctorBespeakTime;
import com.child.entity.doctor.DoctorConsult;
import com.child.entity.doctor.DoctorConsultOrder;
import com.child.entity.doctor.QDoctorConsult;
import com.child.entity.image.NorImage;
import com.child.json.children.ChildrenRecordJson;
import com.child.json.doctor.DoctorConsultArchivesJson;
import com.child.json.doctor.DoctorConsultDetailJson;
import com.child.json.doctor.DoctorConsultJson;
import com.child.json.doctor.DoctorConsultResponseJson;
import com.child.json.image.NorImageJson;
import com.child.service.NorImageService;
import com.querydsl.core.types.Predicate;
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
@RequestMapping(value = "/childrenHealthRecord")
@Api(value = "宝宝健康记录")
public class ChildrenHealthRecordController  {

    @Autowired
    private DoctorConsultRepository doctorConsultRepository;
    @Autowired
    private ChildrenRepository childrenRepository;
    @Autowired
    private DoctorBespeakTimeRepository doctorBespeakTimeRepository;
    @Autowired
    private ChildrenHealthRecordRepository childrenHealthRecordRepository;
    @Autowired
    private NorImageService norImageService;
    @Autowired
    private NorImageRepository norImageRepository;
    @Autowired
    private DoctorConsultOrderRepository doctorConsultOrderRepository;


    @ApiOperation(value = "获取宝宝健康记录列表")
    @GetMapping(value = "/getAllChildrenHealthRecord")
    public ResultsWrapper<DoctorConsultDetailJson> getAllChildrenHealthRecord(
            @ApiParam(value = "咨询类型 1.图文咨询 2.电话咨询 3.网络诊室 4.预约门诊")
            @RequestParam(value = "type",required = false) Integer type,
            @ApiParam(value = "宝宝id")
            @RequestParam(value = "childrenId",required = false) Long childrenId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){

        Sort sort = new Sort(Sort.Direction.DESC,"visitTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        QDoctorConsult doctorConsultName = QDoctorConsult.doctorConsult;
        Predicate predicate = null;
        /*if(null == type || null == childrenId){
            predicate = doctorConsultName.customerId.in(customer.getId());
        }*/
        if(null != childrenId && !childrenId.equals("")) {
            if(null != type && !type.equals("")) {
                predicate = doctorConsultName.childrenId.eq(childrenId).and(doctorConsultName.type.like("%" + type + "%")).and(doctorConsultName.sysType.eq(1));
            }else {
                predicate = doctorConsultName.childrenId.eq(childrenId);
            }
        }

        Page<DoctorConsult> doctorConsultPage = doctorConsultRepository.findAll(predicate,pageable);
        List<DoctorConsultDetailJson> doctorConsultJsonList = new ArrayList<>();
        List<DoctorConsult> doctorConsultList = doctorConsultPage.getContent();
        for(DoctorConsult doctorConsult:doctorConsultList){
            DoctorConsultDetailJson doctorConsultJson = new DoctorConsultDetailJson();
            BeanCopierUtils.getBeanCopier(DoctorConsult.class, DoctorConsultJson.class);
            BeanCopierUtils.copyProperties(doctorConsult,doctorConsultJson);
            Children children = childrenRepository.findOne(doctorConsult.getChildrenId());
            doctorConsultJson.setChildrenName(children.getName());
            doctorConsultJsonList.add(doctorConsultJson);
        }
        ResultsWrapper<DoctorConsultDetailJson> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(doctorConsultJsonList);
        resultsWrapper.setPages(doctorConsultPage.getTotalPages());
        resultsWrapper.setTotal(doctorConsultPage.getTotalElements());
        return resultsWrapper;
    }


    @ApiOperation(value = "获取宝宝健康记录详情")
    @GetMapping(value = "/getChildrenHealthRecordById")
    public DoctorConsultResponseJson getChildrenHealthRecordById(
            @ApiParam(value = "健康记录id")
            @RequestParam(value = "id") Long id){
            DoctorConsult doctorConsult = doctorConsultRepository.findOne(id);
            DoctorConsultResponseJson doctorConsultResponseJson1 = new DoctorConsultResponseJson();
            BeanCopierUtils.getBeanCopier(DoctorConsult.class, DoctorConsultResponseJson.class);
            BeanCopierUtils.copyProperties(doctorConsult,doctorConsultResponseJson1);
            List<NorImage> norImageList = norImageRepository.findByObjectIdAndType(doctorConsult.getId(),7);
            List<NorImageJson> norImageJsonList = new ArrayList<>();
            if(norImageList.size() >0) {
                for (NorImage NorImage:norImageList) {
                    NorImageJson NorImageJson = new NorImageJson();
                    BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                    BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                    norImageJsonList.add(NorImageJson);
                }
                doctorConsultResponseJson1.setMedicationRecordJsonList(norImageJsonList);
            }
            List<NorImage> norImageList1 = norImageRepository.findByObjectIdAndType(doctorConsult.getId(),8);
            List<NorImageJson> norImageJsonList1 = new ArrayList<>();
            if(norImageList1.size() >0) {
                for (NorImage NorImage:norImageList1) {
                    NorImageJson NorImageJson = new NorImageJson();
                    BeanCopierUtils.getBeanCopier(NorImage.class, NorImageJson.class);
                    BeanCopierUtils.copyProperties(NorImage, NorImageJson);
                    norImageJsonList1.add(NorImageJson);
                }
                doctorConsultResponseJson1.setCheckRecordJsonList(norImageJsonList1);
            }
            return doctorConsultResponseJson1;
    }


    @ApiOperation(value = "保存宝宝资料")
    @PostMapping(value = "/save")
    public ChildrenRecordJson saveChildrenHealthRecord(
            @RequestBody ChildrenRecordJson childrenHealthRecordJson){
        ChildrenRecord childrenHealthRecord =  childrenHealthRecordRepository.findByChildrenId(childrenHealthRecordJson.getChildrenId());
        if(null == childrenHealthRecord) {
            childrenHealthRecord = new ChildrenRecord();
        }
        BeanCopierUtils.getBeanCopier(ChildrenRecordJson.class, ChildrenRecord.class);
        BeanCopierUtils.copyProperties(childrenHealthRecordJson,childrenHealthRecord);
        childrenHealthRecordRepository.save(childrenHealthRecord);
        return childrenHealthRecordJson;
    }

    @ApiOperation(value = "获取宝宝资料")
    @GetMapping(value = "/getChildrenHealthRecordByChildrenId")
    public ChildrenRecordJson getChildrenHealthRecord(
            @RequestParam Long childrenId){
        ChildrenRecord childrenHealthRecord = childrenHealthRecordRepository.findByChildrenId(childrenId);
        if(null != childrenHealthRecord) {
            ChildrenRecordJson childrenHealthRecordJson = new ChildrenRecordJson();
            BeanCopierUtils.getBeanCopier(ChildrenRecord.class, ChildrenRecordJson.class);
            BeanCopierUtils.copyProperties(childrenHealthRecord, childrenHealthRecordJson);
            return childrenHealthRecordJson;
        }else {
            return new ChildrenRecordJson();
        }
    }

    @ApiOperation(value = "保存宝宝健康记录")
    @PostMapping(value = "/saveChildrenArchives")
    public DoctorConsultArchivesJson saveChildrenArchives(
            @RequestBody DoctorConsultArchivesJson doctorConsultArchivesJson)throws Exception{
        Customer customer = new Customer();
        DoctorConsult doctorConsult = null;
        if(null == doctorConsultArchivesJson.getId()) {
            doctorConsult = new DoctorConsult();
            doctorConsult.setAddTime(new Date());
            doctorConsult.setCustomerId(customer.getId());
            doctorConsult.setSysType(2);
        }else{
            doctorConsult = doctorConsultRepository.findOne(doctorConsultArchivesJson.getId());
        }
        BeanCopierUtils.getBeanCopier(DoctorConsultArchivesJson.class, DoctorConsult.class);
        BeanCopierUtils.copyProperties(doctorConsultArchivesJson,doctorConsult);
        doctorConsultRepository.save(doctorConsult);
        if(doctorConsultArchivesJson.getMedicationRecordJsonList().size() >0){
            for(com.child.json.image.NorImageJson NorImageJson:doctorConsultArchivesJson.getMedicationRecordJsonList()){
                if (null != NorImageJson.getData() && !NorImageJson.getData().equals("")) {
                    norImageService.save(NorImageJson.getData(), NorImageJson.getOriginalName(), PicProcessType.HEALTHRECORD, NorImageJson.getFileType(), doctorConsult.getId(), 7, true);
                }
            }
        }
        if(doctorConsultArchivesJson.getCheckRecordJsonList().size() >0){
            for(com.child.json.image.NorImageJson NorImageJson:doctorConsultArchivesJson.getCheckRecordJsonList()){
                if (null != NorImageJson.getData() && !NorImageJson.getData().equals("")) {
                    norImageService.save(NorImageJson.getData(), NorImageJson.getOriginalName(), PicProcessType.HEALTHRECORD, NorImageJson.getFileType(), doctorConsult.getId(), 8, true);
                }
            }
        }
        BeanCopierUtils.getBeanCopier(DoctorConsult.class, DoctorConsultArchivesJson.class);
        BeanCopierUtils.copyProperties(doctorConsult,doctorConsultArchivesJson);
        return doctorConsultArchivesJson;
    }


}
