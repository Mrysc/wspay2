package com.rltx.wspay.other.service.impl;

import com.rltx.wspay.account.dao.MemberThumbnailResourceFileDao;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.other.dao.UploadPhotoDao;
import com.rltx.wspay.other.entity.PhotoEntity;
import com.rltx.wspay.other.entity.UploadPhotoEntity;
import com.rltx.wspay.other.service.IUploadService;
import com.rltx.wspay.utils.DownloadPicture;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.TreeMap;


@Service
public class UploadServiceImpl implements IUploadService {

    @Autowired
    private UploadPhotoDao uploadPhotoDao;
    @Autowired
    private MemberThumbnailResourceFileDao memberThumbnailResourceFileDao;

    @Override
    public void uplodePhoto(String str,String userCode,String photoType,String type) throws Exception {
        String photoPath = memberThumbnailResourceFileDao.select(str);
        String dirDiveded[] = photoPath.split("/");
        String newName = "";
        if(dirDiveded.length > 0){
            newName = dirDiveded[dirDiveded.length-1];
        }
        String path = "/storage/photo/wspay/"+newName;
        DownloadPicture.downloadPicture(photoPath,path);
        UploadPhotoEntity photo = new UploadPhotoEntity(photoType, TradeNoUtils.getTradeNo32(),path);
        call(photo,userCode,type);
    }

    @Override
    public TreeMap<String, Object> call(UploadPhotoEntity uploadPhotoEntity, String userCode , String type) throws Exception {
        HttpEntity reqEntity = buildReqEntity(uploadPhotoEntity);
        String response = HttpsUtil.httpPost(ParamUtil.getParamInfoByKey("upLoadReqUrl"), reqEntity);
        boolean result =  XmlSignUtil.verify(response);
        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        mapRespInfo.putAll(mapBody);
        PhotoEntity photo = new PhotoEntity();
        photo.setMerchUserCode(userCode);
        photo.setPhotoType(uploadPhotoEntity.getPhotoType());
        photo.setType(type);
        photo.setPhotoUrl(mapBody.get("PhotoUrl"));
        photo.setOutTradeNo(uploadPhotoEntity.getOutTradeNo());
        photo.preInsert();
        uploadPhotoDao.insert(photo);
        return  mapRespInfo;
    }

    private HttpEntity buildReqEntity(UploadPhotoEntity uploadPhotoEntity) throws Exception  {
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("IsvOrgId", new StringBody(ParamUtil.getParamInfoByKey("IsvOrgId")))
                .addPart("PhotoType", new StringBody(uploadPhotoEntity.getPhotoType()))
                .addPart("OutTradeNo", new StringBody(uploadPhotoEntity.getOutTradeNo()))
                .addBinaryBody("Picture", new File(uploadPhotoEntity.getPicture()), ContentType.DEFAULT_BINARY, uploadPhotoEntity.getPicture())
                .addPart("Function", new StringBody(uploadPhotoEntity.getFunction()))
                .addPart("Version", new StringBody(XmlVersion.defaultVersion)).addPart("AppId", new StringBody(ParamUtil.getParamInfoByKey("Appid")))
                .addPart("ReqTime", new StringBody(uploadPhotoEntity.getReqTime()))
                .addPart("Signature", new StringBody(uploadPhotoEntity.getSignature())).build();
        return reqEntity;
    }
}
