package com.rltx.wspay.other.controller;



import com.rltx.wspay.other.entity.UploadPhotoEntity;
import com.rltx.wspay.other.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
@RequestMapping("upload")
public class UploadPhoto {

    @Autowired
    private IUploadService uploadService;

    @PostMapping("photo")
    public TreeMap<String,Object> accountOpen(String photoType,String path,String userCode,String type) throws Exception {
        UploadPhotoEntity uploadPhoto = new UploadPhotoEntity(photoType, TradeNoUtils.getTradeNo32(),path);
        return uploadService.call(uploadPhoto,userCode,type);
    }

}
