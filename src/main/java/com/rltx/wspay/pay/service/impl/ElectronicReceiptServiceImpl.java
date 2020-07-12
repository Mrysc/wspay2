package com.rltx.wspay.pay.service.impl;

import com.rltx.wspay.commom.*;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.pay.dao.ElectronicreceiptDao;
import com.rltx.wspay.pay.dao.PaymentRecordDao;
import com.rltx.wspay.pay.entity.ElectronicReceiptEntity;
import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import com.rltx.wspay.pay.service.IElectronicReceiptService;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.TreeMap;

@Service
public class ElectronicReceiptServiceImpl implements IElectronicReceiptService {

    @Autowired
    private PaymentRecordDao paymentRecordDao;
    @Autowired
    private ElectronicreceiptDao electronicreceiptDao;


    @Override
    public TreeMap<String, Object> apply(String paymertCode) throws Exception {

        String function = Constant.function.electronicreceiptApply;
        PaymertRecordEntity paymertRecordEntity = paymentRecordDao.selectByCode(paymertCode);
        ElectronicReceiptEntity apply = new ElectronicReceiptEntity();

        apply.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        apply.setMerchantId(paymertRecordEntity.getPayeeMerchId());
        apply.setOrderNo(paymertRecordEntity.getOrderNo());
        apply.setOutTradeNo(TradeNoUtils.getTradeNo32());
        apply.preInsert();
        TreeMap<String, String> form = MapTrunPojo.object2Map(apply);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpReq(ParamUtil.getParamInfoByKey("reqUrl"),param);
        TreeMap<String, String> mapBody= XmlToMap.DocumentMapBody(response);
        apply.setReceiptNo(mapBody.get("ReceiptNo"));
        apply.setPaymentCode(paymertRecordEntity.getCode());
        electronicreceiptDao.insert(apply);
        TreeMap<String, Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        return mapRespInfo;
    }


    @Override
    public TreeMap<String, Object> query(String paymertCode) throws Exception {

        String function = Constant.function.electronicreceiptQuery;
        ElectronicReceiptEntity apply = electronicreceiptDao.selectByPaymentCode(paymertCode);
//        ElectronicReceiptEntity electronicReceiptEntity = new ElectronicReceiptEntity();
        apply.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        TreeMap<String, String> form = MapTrunPojo.object2Map(apply);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);

        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        TreeMap<String, String> mapBody= XmlToMap.DocumentMapBody(response);
        TreeMap<String, Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        mapRespInfo.putAll(mapBody);
        return mapRespInfo;
    }

}
