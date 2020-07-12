package com.rltx.wspay.pay.service.impl;

import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.pay.dao.OrderShareRecordDao;
import com.rltx.wspay.pay.dao.WaybillBaseInfoDao;
import com.rltx.wspay.pay.dao.WaybillPaybillBaseInfoDao;
import com.rltx.wspay.pay.entity.*;
import com.rltx.wspay.pay.service.IShareService;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rltx.wspay.commom.*;

import java.util.*;

@Service
public class ShareServiceImpl implements IShareService {

    @Autowired
    private WaybillBaseInfoDao waybillBaseInfoDao;
    @Autowired
    private MerchRegisterDao merchRegisterDao;
    @Autowired
    private OrderShareRecordDao orderShareRecordDao;
    @Autowired
    private WaybillPaybillBaseInfoDao waybillPaybillBaseInfoDao;

    @Override
    public TreeMap<String, Object> orderShare(PaymertRecordEntity paymertRecord) throws Exception {
        OrderShareResult orderShare = new OrderShareResult();
        orderShare.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        orderShare.setMerchantId(paymertRecord.getPayeeMerchId());
        orderShare.setRelateOrderNo(paymertRecord.getOrderNo());
        orderShare.setOutTradeNo(TradeNoUtils.getTradeNo32());
        orderShare.setTotalAmount(paymertRecord.getTotalAmount());
        orderShare.setCurrency("CNY");
        Map<String, String> map = orderShareResponse(orderShare);
        String response = map.get("response");
        OrderShareEntity orderShareEntity = insertOrderShare(orderShare,paymertRecord,map.get("request"));
        TreeMap<String, String> mapBody= XmlToMap.DocumentMapBody(response);
        TreeMap<String, Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        if(mapRespInfo.containsKey("ResultStatus")&&"S".equals(mapRespInfo.get("ResultStatus"))){
            orderShareEntity.setResponseData(response);
            orderShareEntity.setShareOrderNo(mapBody.get("ShareOrderNo"));
            orderShareRecordDao.update(orderShareEntity);
        }
        mapRespInfo.putAll(mapBody);
        return mapRespInfo;
    }


    public Map<String, String> orderShareResponse(OrderShareResult orderShare) throws Exception {
        String function = Constant.function.orderShare;
        TreeMap<String, String> form = MapTrunPojo.object2Map(orderShare);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        Map<String, String> map = new HashMap<>();
        map.put("request",param);
        map.put("response",response);
        return map;
    }


    private OrderShareEntity insertOrderShare(OrderShareResult orderShare,PaymertRecordEntity paymertRecord,String param){
        OrderShareEntity orderShareEntity = new OrderShareEntity();
        orderShareEntity.setMerchId(orderShare.getMerchantId());
        orderShareEntity.setRelateOrderNo(orderShare.getRelateOrderNo());
        orderShareEntity.setOutTradeNo(orderShare.getOutTradeNo());
        orderShareEntity.setMerchUserCode(paymertRecord.getPayeeUserCode());
        orderShareEntity.setMerchName(paymertRecord.getPayeeName());
        orderShareEntity.setType(paymertRecord.getPayeeType());
        orderShareEntity.setTotalAmount(orderShare.getTotalAmount());
        orderShareEntity.setPayeeFundDetails(orderShare.getPayeeFundDetails());
        orderShareEntity.setRequestData(param);
        try {
            orderShareEntity.preInsert();
        }catch (Exception e){
            orderShareEntity.setCreateBy("网商异步通知");
            orderShareEntity.setCreateTime(new Date());
            orderShareEntity.setUpdateBy("网商异步通知");
            orderShareEntity.setUpdateTime(new Date());
        }
        orderShareRecordDao.insert(orderShareEntity);
        return orderShareEntity;
    }

    private FundDetailsResult fundDetailsResult(WaybillBaseInfoEntity waybillBaseInfoEntity,String merchId){
        FundDetailsResult fundDetails = new FundDetailsResult();
        fundDetails.setAmount(String.valueOf(AmountUtils.changeY2F(waybillBaseInfoEntity.getSettleTradingConfigAmount())));
        fundDetails.setCurrency("CNY");
        fundDetails.setParticipantId(merchId);
        fundDetails.setParticipantType("MERCHANT");
//        fundDetails.setParticipantId(ParamUtil.getParamInfoByKey("IsvOrgId"));
//        fundDetails.setParticipantType("PLATFORM");
        fundDetails.setPurpose("FEE");
        return fundDetails;
    }

    //分账申请接口
    @Override
    public TreeMap<String, Object> orderShareDriver(String paybillNo,String merchId) throws Exception {
        String function = Constant.function.orderShare;
        WaybillPaybillBaseInfoEntity paybillBaseInfoEntity =  waybillPaybillBaseInfoDao.getByPaybillNo(paybillNo);
        MerchRegisterEntity merchRegister = merchRegisterDao.selectByMerchId(merchId);
        OrderShareResult orderShare = new OrderShareResult();
        orderShare.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        orderShare.setRelateOrderNo("202007670703066160");
        orderShare.setOutTradeNo(TradeNoUtils.getTradeNo32());
        orderShare.setTotalAmount(String.valueOf(AmountUtils.changeY2F(paybillBaseInfoEntity.getPaymentAmount())));
        orderShare.setTotalAmount("899788");
        orderShare.setCurrency("CNY");
        orderShare.setMerchantId(merchId);
        TreeMap<String, String> form = MapTrunPojo.object2Map(orderShare);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        OrderShareEntity orderShareEntity = insertOrderShareDriver(orderShare,merchRegister,param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        TreeMap<String, String> mapBody= XmlToMap.DocumentMapBody(response);
        TreeMap<String, Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        if(mapRespInfo.containsKey("ResultStatus")&&"S".equals(mapRespInfo.get("ResultStatus"))){
            orderShareEntity.setShareOrderNo(mapBody.get("ShareOrderNo"));
            orderShareRecordDao.update(orderShareEntity);
        }
        mapRespInfo.putAll(mapBody);
        return mapRespInfo;
    }

    private OrderShareEntity insertOrderShareDriver(OrderShareResult orderShare,MerchRegisterEntity merchRegister,String param){
        OrderShareEntity orderShareEntity = new OrderShareEntity();
        orderShareEntity.setMerchId(orderShare.getMerchantId());
        orderShareEntity.setRelateOrderNo(orderShare.getRelateOrderNo());
        orderShareEntity.setOutTradeNo(orderShare.getOutTradeNo());
        orderShareEntity.setMerchUserCode(merchRegister.getMerchUserCode());
        orderShareEntity.setMerchName(merchRegister.getMerchName());
        orderShareEntity.setType(merchRegister.getType());
        orderShareEntity.setTotalAmount(orderShare.getTotalAmount());
        orderShareEntity.setPayeeFundDetails(orderShare.getPayeeFundDetails());
        orderShareEntity.setRequestData(param);
        orderShareEntity.preInsert();
        orderShareRecordDao.insert(orderShareEntity);
        return orderShareEntity;
    }


    //分账退回查询接口
    @Override
    public String refundShare() throws Exception {
        String function = Constant.function.refundShare;
        RefundShareEntity refundShare = new RefundShareEntity(
                HttpMain.IsvOrgId,HttpMain.merchantId,"","","",HttpMain.Currency,"过期了"
        );
        TreeMap<String, String> form = MapTrunPojo.object2Map(refundShare);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //打印请求报文
        System.out.println(param);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        //自签自验结果
        System.out.println("验签结果：" +responseVerify);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        System.out.println(response);
        return response;
    }

    //分账退回查询接口
    @Override
    public String refundShareQuery() throws Exception {
        String function = Constant.function.refundShareQuery;
        RefundShareQueryEntity refundShareQuery = new RefundShareQueryEntity(
                HttpMain.IsvOrgId,HttpMain.merchantId,"","",""
        );
        TreeMap<String, String> form = MapTrunPojo.object2Map(refundShareQuery);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //打印请求报文
        System.out.println(param);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        //自签自验结果
        System.out.println("验签结果：" +responseVerify);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        System.out.println(response);
        return response;
    }

    //单笔分账查询接口
    @Override
    public TreeMap<String, Object> shareQuery(String outTradeNo) throws Exception {

        String function = Constant.function.shareQuery;
        OrderShareEntity orderShareEntity = orderShareRecordDao.selectByOutTradeNo(outTradeNo);
        ShareQueryEntity shareQuery = new ShareQueryEntity();
        shareQuery.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        shareQuery.setMerchantId(orderShareEntity.getMerchId());
        shareQuery.setRelateOrderNo(orderShareEntity.getRelateOrderNo());
        shareQuery.setOutTradeNo(orderShareEntity.getOutTradeNo());
        shareQuery.setShareOrderNo(orderShareEntity.getShareOrderNo());
        TreeMap<String, String> form = MapTrunPojo.object2Map(shareQuery);
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
