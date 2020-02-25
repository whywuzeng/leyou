package com.leyou.service.Impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.service.UploadService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;


/**
 * Created by Administrator on 2020/1/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.service.Impl
 */
@Service
public class UploadServiceImpl implements UploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

    // 支持的文件类型
    private static final List<String> suffixes = Arrays.asList("image/png","image/jpeg");

    @Autowired
    FastFileStorageClient storageClient;

    @Override
    public String upload(MultipartFile file) {
        // 1、图片信息校验
        // 1)校验文件类型

        try {
            String type = file.getContentType();
            if (!suffixes.contains(type))
            {
                LOGGER.info("上传失败，文件类型不匹配：{}", type);
                return null;
            }

            // 2)校验图片内容

            BufferedImage image = ImageIO.read(file.getInputStream());

            if (image==null)
            {
                LOGGER.info("上传失败，文件内容不符合要求");
                return null;
            }

            //获取后缀名
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ",");

            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);

            return "http://image.leyou.com/"+storePath.getFullPath();

//            // 2、保存图片
//            // 2.1、生成保存目录
//            File dir = new File("D:\\itzeng\\upload");
//            if (!dir.exists()){
//                dir.mkdirs();
//            }
//
//            // 2.2、保存图片
//            file.transferTo(new File(dir,file.getOriginalFilename()));
//
//            //拼接url
//            String url = "http://image.leyou.com/upload/"+file.getOriginalFilename();
//
//            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
