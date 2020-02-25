package com.leyou.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2020/1/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.service
 */

public interface UploadService {

    String upload(MultipartFile multipartFile);
}
