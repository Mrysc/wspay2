package com.rltx.wspay.other.service;


import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.other.entity.UploadPhotoEntity;

import java.util.TreeMap;

public interface IUploadService {
    void uplodePhoto(String str,String userCode,String photoType,String type) throws Exception;
    TreeMap<String,Object> call(UploadPhotoEntity uploadPhotoEntity, String userCode ,String type) throws Exception;
}
