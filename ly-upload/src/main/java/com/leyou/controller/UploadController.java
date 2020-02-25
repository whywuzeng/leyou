package com.leyou.controller;

import com.leyou.service.UploadService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2020/1/25.
 * <p>
 * by author wz
 * <p>
 * com.leyou.controller
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    @PostMapping("image")
     public ResponseEntity<String> UploadImage(@RequestParam(name = "file")MultipartFile multipartFile){
      String url =  this.uploadService.upload(multipartFile);
        if (StringUtils.isBlank(url)) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(url);
     }

     @PostMapping("im")
    public String upload1(){

        return "woshiwu";
     }
}
