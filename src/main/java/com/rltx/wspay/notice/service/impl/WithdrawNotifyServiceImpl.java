package com.rltx.wspay.notice.service.impl;

import com.rltx.wspay.account.dao.MerchConsignorMoneyDao;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.account.service.IMerchConsignorMoneyService;
import com.rltx.wspay.commom.DomCreateResponse;
import com.rltx.wspay.commom.XmlSignUtil;
import com.rltx.wspay.commom.XmlToMap;
import com.rltx.wspay.constant.MapEntity;
import com.rltx.wspay.notice.dao.ChargeNotifyRecordDao;
import com.rltx.wspay.notice.entity.ChargeNotifyRecordEntity;
import com.rltx.wspay.notice.entity.WithdrawNotifyEntity;
import com.rltx.wspay.notice.service.IChargeNotifyRecordService;
import com.rltx.wspay.notice.service.IWithdrawNotifyService;
import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import com.rltx.wspay.pay.service.IPayService;
import com.rltx.wspay.utils.MapChangeKay;
import com.rltx.wspay.withdrawal.dao.WithdrawDao;
import com.rltx.wspay.withdrawal.entity.WithdrawApplyEntity;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WithdrawNotifyServiceImpl implements IWithdrawNotifyService {
    
    @Autowired
    private WithdrawDao withdrawDao;

    @Override
    public String notify(String data) throws Exception {
        boolean result =  XmlSignUtil.verify(data);
        TreeMap<String,String> map = XmlToMap.DocumentMap(data);
        TreeMap<String,Object> mapBody= XmlToMap.DocumentMapType(data,"body");
        Map<String, Object> map1=  MapChangeKay.transformUpperCase(mapBody);
        WithdrawNotifyEntity withdrawNotifyEntity= (WithdrawNotifyEntity) MapEntity.map2Object(map1, WithdrawNotifyEntity.class);
        WithdrawApplyEntity withdrawApplyEntity = withdrawDao.select(withdrawNotifyEntity.getOutTradeNo());
        withdrawApplyEntity.setBankCardNo(withdrawNotifyEntity.getBankCardNo());
        withdrawApplyEntity.setBankCertName(withdrawNotifyEntity.getBankCertName());
        withdrawApplyEntity.setWithdrawApplyDate(withdrawNotifyEntity.getWithdrawApplyDate());
        withdrawApplyEntity.setWithdrawFinishDate(withdrawNotifyEntity.getWithdrawFinishDate());
        withdrawApplyEntity.setErrorDesc(withdrawNotifyEntity.getErrorDesc());
        if(mapBody.containsKey("Status")&&"SUCCESS".equals(mapBody.get("Status"))){
            withdrawApplyEntity.setFlag("3");
        }else {
            withdrawApplyEntity.setFlag("0");
        }
        withdrawDao.updateNotify(withdrawApplyEntity);
        //响应回执生成(报文组装步骤)
        String response = DomCreateResponse.requestcreateXml(map);
        //开始对响应回执进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(response);
        return response;
    }


}