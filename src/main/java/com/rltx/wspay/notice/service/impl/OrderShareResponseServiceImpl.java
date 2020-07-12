package com.rltx.wspay.notice.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.dao.MerchRegisterRecordDao;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.account.entity.MerchRegisterRecordEntity;
import com.rltx.wspay.commom.DomCreateResponse;
import com.rltx.wspay.commom.XmlSignUtil;
import com.rltx.wspay.commom.XmlToMap;
import com.rltx.wspay.constant.MapEntity;
import com.rltx.wspay.notice.entity.MerchResponseEntity;
import com.rltx.wspay.notice.service.IMerchResponseService;
import com.rltx.wspay.notice.service.IOrderShareResponseService;
import com.rltx.wspay.pay.dao.OrderShareRecordDao;
import com.rltx.wspay.pay.entity.OrderShareEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.TreeMap;

@Service
public class OrderShareResponseServiceImpl implements IOrderShareResponseService {

    @Autowired
    private OrderShareRecordDao orderShareRecordDao;

    @Override
    public String notify(String data) throws Exception {
        //对来自网商得报文做签名验证
        boolean result =  XmlSignUtil.verify(data);
        TreeMap<String,String> map= XmlToMap.DocumentMap(data);
        //响应回执生成(报文组装步骤)
        String response = DomCreateResponse.requestcreateXml(map);
        //开始对响应回执进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(response);
        if(result&&responseVerify){
            TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(data);
            OrderShareEntity orderShareEntity = orderShareRecordDao.selectByOutTradeNo(mapBody.get("OutTradeNo"));
            orderShareEntity.setStatus(mapBody.get("Status"));
            orderShareEntity.setNotifyData(data);
//        orderShareEntity.setShareDate();
            orderShareRecordDao.updateNotify(orderShareEntity);
        }else {
            System.out.println("验签失败——result："+result+"   responseVerify:"+responseVerify);
        }
        return response;
    }
}
