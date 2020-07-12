package com.rltx.wspay.notice.service.impl;

import com.rltx.wspay.commom.DomCreateResponse;
import com.rltx.wspay.commom.XmlSignUtil;
import com.rltx.wspay.commom.XmlToMap;
import com.rltx.wspay.constant.MapEntity;
import com.rltx.wspay.notice.entity.WithdrawNotifyEntity;
import com.rltx.wspay.notice.service.IElectronicreceiptNotifyService;
import com.rltx.wspay.notice.service.IWithdrawNotifyService;
import com.rltx.wspay.pay.dao.ElectronicreceiptDao;
import com.rltx.wspay.pay.entity.ElectronicReceiptEntity;
import com.rltx.wspay.utils.MapChangeKay;
import com.rltx.wspay.withdrawal.dao.WithdrawDao;
import com.rltx.wspay.withdrawal.entity.WithdrawApplyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class ElectronicreceiptNotifyServiceImpl implements IElectronicreceiptNotifyService {
    
    @Autowired
    private ElectronicreceiptDao electronicreceiptDao;

    @Override
    public String notify(String data) throws Exception {
        boolean result =  XmlSignUtil.verify(data);
        TreeMap<String,String> map = XmlToMap.DocumentMap(data);
        //响应回执生成(报文组装步骤)
        String response = DomCreateResponse.requestcreateXml(map);
        //开始对响应回执进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(response);
        if(result&&responseVerify){
            TreeMap<String,Object> mapBody= XmlToMap.DocumentMapType(data,"body");
            Map<String, Object> map1=  MapChangeKay.transformUpperCase(mapBody);
            ElectronicReceiptEntity electronicReceiptEntity= (ElectronicReceiptEntity) MapEntity.map2Object(map1, ElectronicReceiptEntity.class);
            ElectronicReceiptEntity electronicReceipt = electronicreceiptDao.selectByOutTradeNo(electronicReceiptEntity.getOutTradeNo());
            electronicReceipt.setStatus(electronicReceiptEntity.getStatus());
            electronicReceipt.setPdfDownloadUrl(electronicReceiptEntity.getPdfDownloadUrl());
            electronicReceipt.preUpdate();
            electronicreceiptDao.update(electronicReceipt);
        }else {
            System.out.println("验签失败——result："+result+"   responseVerify:"+responseVerify);
        }
        return response;
    }


}