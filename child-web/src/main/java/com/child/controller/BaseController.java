package com.child.controller;

import com.child.UserSession;
import com.child.UserType;
import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.utils.ConstantsUtils;
import com.child.dao.AdminRepository;
import com.child.dao.CustomerRepository;
import com.child.dao.UserSessionRepository;
import com.child.entity.image.NorImage;
import com.child.enums.ResultEnum;
import com.child.json.image.NorImageJson;
import com.child.service.NorImageService;
import com.child.utils.ResultVOUtil;
import com.child.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Controller
@RequestMapping(value = "/base")
@Api(value = "基本的管理类")
public class BaseController {

    @Autowired
    private NorImageService norImageService;

    @ApiOperation(value = "上传图片", notes = "需要file，和用户id，图片分类，返回的是地址")
    @RequestMapping(value = "/uploadImg",method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<NorImage> uploadImg(@RequestParam("file") MultipartFile file,@RequestParam Integer type, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IllegalStateException, IOException {

        NorImage image = norImageService.uploadImg(file,request,response,session, type);
        if(image==null&&image.getId()==null){
            return ResultVOUtil.error(ResultEnum.PICTURE.getCode(), ResultEnum.PICTURE.getMessage());
        }
        return ResultVOUtil.success(image);
    }

}
