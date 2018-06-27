package com.child.controller;

import com.child.SecureTokenhelper;
import com.child.UserSession;
import com.child.UserType;
import com.child.common.exception.BusinessException;
import com.child.common.exception.ErrorCode;
import com.child.common.exception.ResultsWrapper;
import com.child.common.security.PooledStrongPasswordEncryptor;
import com.child.common.utils.BeanCopierUtils;
import com.child.common.utils.ConstantsUtils;
import com.child.common.utils.PicProcessType;
import com.child.dao.AdminRepository;
import com.child.dao.UserSessionRepository;
import com.child.entity.admin.Admin;
import com.child.json.admin.AdminJson;
import com.child.json.admin.AdminLoginResponse;
import com.child.json.admin.AdminLoginUser;
import com.child.service.NorImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping(value = "/admin")
@Api(value = "管理员管理")
public class AdminController  {

    @Value("${defaultPassword}")
    private String defaultPassword;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private UserSessionRepository userSessionRepository;

    @Autowired
    private NorImageService norImageService;


    @ApiOperation(value = "保存管理员")
    @PostMapping(value = "/save")
    public AdminJson saveAdmin(
            @RequestBody AdminJson adminJson){
       Admin adminToken = new Admin();
        Admin admin = null;
        if(null == adminJson.getId()) {
            admin = new Admin();
            admin.setAddTime(new Date());
            admin.setStatus(1);
        }else{
            admin = adminRepository.findOne(adminJson.getId());
            admin.setUpdateTime(new Date());
        }
        BeanCopierUtils.getBeanCopier(AdminJson.class, Admin.class);
        BeanCopierUtils.copyProperties(adminJson,admin);
        if(null != adminJson.getImageJsonList() && !adminJson.getImageJsonList().equals("")) {
            if (adminJson.getImageJsonList().size() > 0) {
                if (null != adminJson.getImageJsonList().get(0).getData() && !adminJson.getImageJsonList().get(0).getData().equals("")) {
                    String icon = norImageService.save(adminJson.getImageJsonList().get(0).getData(), adminJson.getImageJsonList().get(0).getOriginalName(), PicProcessType.ICON, null, null, null, false);
                    admin.setIcon(icon);
                }
            }
        }
        if(null != adminJson.getPassword() && !adminJson.getPassword().equals("")) {
            admin.setPassword(PooledStrongPasswordEncryptor.getInstance().encryptPassword(adminJson.getPassword()));
        }
        adminRepository.save(admin);
        BeanCopierUtils.getBeanCopier(Admin.class, AdminJson.class);
        BeanCopierUtils.copyProperties(admin,adminJson);
        return adminJson;
    }

    /**
     * 获取管理员列表
     * @return
     */
    @ApiOperation(value = "获取管理员列表")
    @GetMapping(value = "/getAllAdmin")
    public ResultsWrapper<AdminLoginResponse> getAllAdmin(
            @RequestParam(value = "account",required = false) String account,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "size",defaultValue = "10") Integer size){
        Sort sort = new Sort(Sort.Direction.DESC,"addTime");
        Pageable pageable = new PageRequest(page-1,size,sort);
        Page<Admin> adminPage = null;
        if(null == account || account.equals("")){
            adminPage = adminRepository.findByStatus(pageable);
        }else{
            adminPage = adminRepository.findByAccount(account,pageable);
        }
        List<AdminLoginResponse> adminJsonList = new ArrayList<>();
        List<Admin> adminList = adminPage.getContent();
        for(Admin admin:adminList){
            AdminLoginResponse adminJson = new AdminLoginResponse();
            BeanCopierUtils.getBeanCopier(Admin.class, AdminJson.class);
            BeanCopierUtils.copyProperties(admin,adminJson);
            adminJsonList.add(adminJson);
        }
        ResultsWrapper<AdminLoginResponse> resultsWrapper = new ResultsWrapper();
        resultsWrapper.setResults(adminJsonList);
        resultsWrapper.setPages(adminPage.getTotalPages());
        resultsWrapper.setTotal(adminPage.getTotalElements());
        return resultsWrapper;
    }


    @ApiOperation(value = "获取管理员详情")
    @GetMapping(value = "/getAdminById")
    public AdminLoginResponse getAdminById(
            @ApiParam(value = "管理员id",required = true)
            @RequestParam(value = "id") Long id){
        Admin admin = adminRepository.findOne(id);
        if(null == admin){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        AdminLoginResponse adminJson = new AdminLoginResponse();
        BeanCopierUtils.getBeanCopier(Admin.class,AdminJson.class);
        BeanCopierUtils.copyProperties(admin,adminJson);
        return adminJson;
    }

    @ApiOperation(value = "删除管理员")
    @DeleteMapping(value = "/deleteAdminById")
    public void deleteAdminById(
            @ApiParam(value = "管理员id",required = true)
            @RequestParam(value = "id") Long id){
        Admin admin = adminRepository.findOne(id);
        if(null == admin){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        admin.setStatus(2);
        adminRepository.save(admin);
    }

    @GetMapping(value = "/resetPassword")
    @ApiOperation(value = "重置密码")
    public AdminJson resetPassword(HttpServletRequest request,
                              @RequestParam(value = "id") Long id
                              ) {

        Admin adminToken = new Admin();
        Admin admin = adminRepository.findOne(id);
        if(null == admin){
            throw new BusinessException(ErrorCode.DATA_NOT_EXIST);
        }
        /*if(!adminToken.getId().equals(admin.getId())){
            throw new BusinessException(ErrorCode.NOT_OURSELF_ACCOUNT);
        }*/
        admin.setPassword(PooledStrongPasswordEncryptor.getInstance().encryptPassword(defaultPassword));
        adminRepository.save(admin);
        AdminJson adminJson = new AdminJson();
        BeanCopierUtils.getBeanCopier(Admin.class,AdminJson.class);
        BeanCopierUtils.copyProperties(admin,adminJson);
        return adminJson;
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "管理员登录")
    public AdminLoginResponse login(HttpServletRequest request,
                                    @RequestBody final AdminLoginUser user) {

        AdminLoginResponse result = login(user,request);
        return result;
    }

    private AdminLoginResponse login(AdminLoginUser loginUser,HttpServletRequest request) {
        Admin user = adminRepository.findByAccount(loginUser.getAccount());
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_EXIST_ACCOUNT);
        }

        if (!PooledStrongPasswordEncryptor.getInstance().checkPassword(loginUser.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        String token = SecureTokenhelper.createToken(UserType.ADMIN);
        UserSession session = new UserSession();
        session.setAuthToken(token);
        session.setStartTime(new Date());
        session.setExpired(false);
        session.setUserId(user.getId());
        userSessionRepository.save(session);
        AdminLoginResponse result = new AdminLoginResponse();
        result.setAddTime(user.getAddTime());
        result.setId(user.getId());
        result.setIcon(user.getIcon());
        result.setAccount(user.getAccount());
        result.setAdminType(user.getAdminType());
        result.setAuthToken(token);
        result.setStatus(user.getStatus());
        result.setName(user.getName());
        return result;
    }

}
