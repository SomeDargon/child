package com.child.service.impl;

import com.child.common.utils.PicProcessType;
import com.child.common.utils.PictureHelper;
import com.child.common.utils.date.DatetimeUtil;
import com.child.dao.NorImageRepository;
import com.child.entity.image.NorImage;
import com.child.service.NorImageService;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by somedragon on 2018/1/8.
 */
@Service
@Data
public class NorImageServiceImpl implements NorImageService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NorImageServiceImpl.class);

    @Value("${server.ip}")
    private String serverIp;

    @Value("${web.upload-path}")
    private String filePath;

    @Value("${server.context-path}")
    private String contextPath;


    @Autowired
    private NorImageRepository norImageRepository;
    @Override
    public  String save(String data,String originalName,PicProcessType picProcessType,String fileType,Long objectId,Integer type,Boolean flag){
        byte[] fileData =  PictureHelper.convertBinaryToByteArray(data);
        String path = PictureHelper.savePic(fileData,picProcessType,fileType,filePath);
        String realPath = serverIp + path;
        if(flag) {
            NorImage norImage = new NorImage();
            norImage.setType(type);
            norImage.setNorImageUrl(realPath);
            norImage.setAddTime(new Date());
            norImage.setOriginalName(originalName);
            norImage.setObjectId(objectId);
            norImageRepository.save(norImage);
        }
        return realPath;
    }

    @Override
    public NorImage findOne(Long id) {
        return norImageRepository.findOne(id);
    }

    @Override
    public NorImage save(NorImage norImage) {
        return norImageRepository.save(norImage);
    }

    @Override
    public NorImage uploadImg(MultipartFile file, HttpServletRequest request, HttpServletResponse response, HttpSession session, Integer status) throws IllegalStateException, IOException {
        NorImage image = new NorImage();
        if (file!=null) {// 判断上传的文件是否为空
            String path="null";// 文件路径
            String type=null;// 文件类型
            String imgUrl="/img/"+ DatetimeUtil.formatDate(new Date())+"/";
            String pathName=filePath+imgUrl;
            if(!new File(pathName).exists()){
                new File(pathName).mkdirs();
            }
            String fileName=(new Date()).getTime()+"_"+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 0);// 文件原名称
            logger.info("上传的文件原名称:"+fileName);
            // 判断文件类型
            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            if (type!=null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase())||"PNG".equals(type.toUpperCase())||"JPG".equals(type.toUpperCase())||"JPEG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
                    String realPath=request.getSession().getServletContext().getRealPath("/");
                    // 自定义的文件名称
                    String trueFileName=String.valueOf(System.currentTimeMillis())+fileName;
                    // 设置存放图片文件的路径
                    path=pathName+trueFileName;
                    System.out.println("存放图片文件的路径:"+path);
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
                    image.setFileName(fileName);
                    image.setAddTime(new Date());
                    image.setType(status);
                    image.setNorImageUrl(serverIp+imgUrl+trueFileName);
                    image = norImageRepository.save(image);
                    System.out.println("文件成功上传到指定目录下:"+image.getId());
                    return image;
                }else {
                    logger.info("不是我们想要的文件类型,请按要求重新上传");
                }
            }else {
                logger.info("文件类型为空");
            }
        }else {
            logger.info("没有找到相对应的文件");
        }

        return null;
    }

    @Override
    public List<NorImage> findByObjectIdAndType(Long id, Integer type) {

        return norImageRepository.findByObjectIdAndType(id, type);
    }

}
