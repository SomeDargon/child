package com.child.service;

import com.child.common.utils.PicProcessType;
import com.child.entity.image.NorImage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by somedragon on 2018/1/8.
 */
public interface NorImageService{

    String save(String data,String originalName, PicProcessType picProcessType, String fileType,Long objectId,Integer type,Boolean flag);

    NorImage findOne(Long id);

    NorImage save(NorImage norImage);

    NorImage uploadImg(MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session, Integer type) throws IllegalStateException, IOException;

    List<NorImage> findByObjectIdAndType(Long id, Integer type);
}
