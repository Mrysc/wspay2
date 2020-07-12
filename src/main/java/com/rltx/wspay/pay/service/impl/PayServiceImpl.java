package com.rltx.wspay.pay.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.framework.log.support.BusinessException;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.account.service.IMerchConsignorMoneyService;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.pay.dao.PaymentRecordDao;
import com.rltx.wspay.pay.dao.WaybillPaybillBaseInfoDao;
import com.rltx.wspay.pay.entity.*;
import com.rltx.wspay.pay.service.IPayService;
import com.rltx.wspay.utils.constant.MerchRegisterConstant;
import com.rltx.wspay.utils.constant.ParamUtil;
import com.rltx.wspay.utils.constant.PayConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rltx.wspay.commom.*;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PayServiceImpl implements IPayService {
    @Autowired
    private MerchRegisterDao merchRegisterDao;
    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;
    @Autowired
    private PaymentRecordDao paymentRecordDao;
    @Autowired
    private IMerchConsignorMoneyService consignorMoneyService;
    @Autowired
    private WaybillPaybillBaseInfoDao waybillPaybillBaseInfoDao;

    public  TreeMap<String, Object> payment(String paybillNo) throws Exception{
        MerchRegisterEntity payer = new MerchRegisterEntity();
        MerchRegisterEntity payee = new MerchRegisterEntity();
        WaybillPaybillBaseInfoEntity waybillPaybillBaseInfoEntity = waybillPaybillBaseInfoDao.getByPaybillNo(paybillNo);
        if(ObjectUtils.isEmpty(waybillPaybillBaseInfoEntity)){
            throw new BusinessException("支付单未找到");
        }else {
            if(StringUtils.isEmpty(waybillPaybillBaseInfoEntity.getPlatformDriverCode())||waybillPaybillBaseInfoEntity.getPlatformDriverCode().equals(waybillPaybillBaseInfoEntity.getPlatformPayerCode())){
                payee = merchRegisterDao.selectByMerchUserCodeType(waybillPaybillBaseInfoEntity.getPlatformPayeeCode(),PayConstant.accountType.commonly);
                if("2".equals(waybillPaybillBaseInfoEntity.getPayeeType())){
                    payer = merchRegisterDao.selectByMerchUserCodeType(waybillPaybillBaseInfoEntity.getPlatformPayerCode(),PayConstant.accountType.commonly);
                }else {
                    payer = merchRegisterDao.selectByMerchUserCodeType(waybillPaybillBaseInfoEntity.getPlatformPayerCode(),PayConstant.accountType.income);
                }
            }else {
                payee = merchRegisterDao.selectByMerchUserCodeType(waybillPaybillBaseInfoEntity.getPlatformPayeeCode(),PayConstant.accountType.commonly);
                payer = merchRegisterDao.selectByMerchUserCodeType(waybillPaybillBaseInfoEntity.getPlatformDriverCode(),PayConstant.accountType.commonly);
                if(ObjectUtils.isEmpty(payer)){
                    throw new BusinessException("商户未入驻");
                }
                if(ObjectUtils.isEmpty(payee)){
                    throw new BusinessException("商户未入驻");
                }
                String totalAmount = AmountUtils.changeY2F(waybillPaybillBaseInfoEntity.getPaymentAmount().toString());
                balancePay(payer,payee,totalAmount,waybillPaybillBaseInfoEntity.getPaybillNo());
                payee = payer;
                payer = merchRegisterDao.selectByMerchUserCodeType(waybillPaybillBaseInfoEntity.getPlatformPayerCode(),PayConstant.accountType.commonly);
            }
        }
        if(ObjectUtils.isEmpty(payer)){
            throw new BusinessException("商户未入驻");
        }
        if(ObjectUtils.isEmpty(payee)){
            throw new BusinessException("商户未入驻");
        }
        String totalAmount = AmountUtils.changeY2F(waybillPaybillBaseInfoEntity.getPaymentAmount().toString());
        return balancePay(payer,payee,totalAmount,waybillPaybillBaseInfoEntity.getPaybillNo());
    }



    @Override
    public TreeMap<String, Object> balancePay(MerchRegisterEntity payer, MerchRegisterEntity payee, String totalAmount,String paybillNo) throws Exception {
        BalancePayEntity balancePay = new BalancePayEntity();
        balancePay.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        balancePay.setPayerMerchantId(payer.getMerchId());
        balancePay.setPayeeId(payee.getMerchId());
        balancePay.setPayeeType("MERCHANT");
        balancePay.setOutTradeNo(TradeNoUtils.getTradeNo32());
        balancePay.setTotalAmount(totalAmount);
        balancePay.setCurrency(ParamUtil.getParamInfoByKey("Currency"));
        balancePay.setBody("运费");
        Map<String, String> map = balancePayResponse(balancePay);
        String response = map.get("response");

        PaymertRecordEntity paymertRecordEntity = new PaymertRecordEntity();
        paymertRecordEntity.setCode(generationCodingService.generateCurrencyCode());
        paymertRecordEntity.setPayeeMerchId(payee.getMerchId());
        paymertRecordEntity.setPayeeMerchType(payee.getMerchType());
        paymertRecordEntity.setPayeeName(payee.getMerchName());
        paymertRecordEntity.setPayeeUserCode(payee.getMerchUserCode());
        paymertRecordEntity.setPayeeType(payee.getType());
        paymertRecordEntity.setPayeeOutMerchId(payee.getOutMerchId());
        paymertRecordEntity.setPayerMerchId(payer.getMerchId());
        paymertRecordEntity.setPayerMerchType(payer.getMerchType());
        paymertRecordEntity.setPayerName(payer.getMerchName());
        paymertRecordEntity.setPayerUserCode(payer.getMerchUserCode());
        paymertRecordEntity.setPayerType(payer.getType());
        paymertRecordEntity.setPayerOutMerchId(payer.getOutMerchId());
        paymertRecordEntity.setPayStatus("01");
        paymertRecordEntity.setTotalAmount(totalAmount);
        paymertRecordEntity.setOutTradeNo(balancePay.getOutTradeNo());
        paymertRecordEntity.setRequestData(map.get("request"));
        paymertRecordEntity.setPaybillNo(paybillNo);
        String type = "";
        try {
            type = "0";
            paymertRecordEntity.preInsert();
        }catch (Exception e){
            type = "1";
            paymertRecordEntity.setCreateBy("网商异步通知");
            paymertRecordEntity.setCreateTime(new Date());
            paymertRecordEntity.setUpdateBy("网商异步通知");
            paymertRecordEntity.setUpdateTime(new Date());
        }
        paymentRecordDao.insert(paymertRecordEntity);
        paymertRecordEntity = paymentRecordDao.selectByOutTradeNo(paymertRecordEntity.getOutTradeNo());
        TreeMap<String, Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        paymertRecordEntity.setResponseData(response);
        if(mapRespInfo.containsKey("ResultStatus")&&MerchRegisterConstant.registerStatus.fail.equals(mapRespInfo.get("ResultStatus"))){
            paymertRecordEntity.setPayStatus(PayConstant.payStatus.fail);
        }else {
            paymertRecordEntity.setPayStatus(PayConstant.payStatus.establish);
            if("0".equals(type)){
                consignorMoneyService.update(paymertRecordEntity.getPayerMerchId(),"-"+paymertRecordEntity.getTotalAmount());
            }
        }
        TreeMap<String, String> mapBody= XmlToMap.DocumentMapBody(response);
        if(mapBody.containsKey("OrderNo")){
            paymertRecordEntity.setOrderNo(mapBody.get("OrderNo"));
        }
        paymentRecordDao.update(paymertRecordEntity);
        mapRespInfo.put("code",paymertRecordEntity.getCode());
        mapRespInfo.putAll(mapBody);
        return mapRespInfo;
    }

    public Map<String, String> balancePayResponse(BalancePayEntity balancePay) throws Exception {
        String function = Constant.function.balancePay;
        TreeMap<String, String> form = MapTrunPojo.object2Map(balancePay);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        Map<String, String> map = new HashMap<>();
        map.put("request",param);
        map.put("response",response);
        return map;
    }

    @Override
    public TreeMap<String,String> openPay(String userCode) throws Exception {
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByMerchUserCodeType(userCode,PayConstant.accountType.commonly);
        String function = Constant.function.openPay;
        OpenPayEntity openPay= new OpenPayEntity();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String requestTime = formatter.format(new Date());
        openPay.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        openPay.setMerchantId(merchRegisterEntity.getMerchId());
        openPay.setOutTradeNo(TradeNoUtils.getTradeNo32());
        openPay.setRequestTime(requestTime);
        TreeMap<String, String> form = MapTrunPojo.object2Map(openPay);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        System.out.println(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        System.out.println(response);
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        if (mapBody.containsKey("Success")) {
            boolean success = Boolean.parseBoolean(mapBody.get("Success"));
            if(success){
                System.out.println(success);
                merchRegisterEntity.setOpenPay("true");
                merchRegisterDao.updateOpenPay(merchRegisterEntity);
            }
        }
        return mapBody;
    }

    @Override
    public PaymertRecordEntity payConfirm(String code) throws Exception {
        String function = Constant.function.payconfirm;
        PaymertRecordEntity paymertRecordEntity = paymentRecordDao.selectByCode(code);
        PayconfirmEntity payconfirm=  new PayconfirmEntity();
        payconfirm.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        payconfirm.setPayerMerchantId(paymertRecordEntity.getPayerMerchId());
        payconfirm.setPayeeId(paymertRecordEntity.getPayeeMerchId());
        payconfirm.setPayeeType("MERCHANT");
        payconfirm.setOutTradeNo(paymertRecordEntity.getOutTradeNo());
        payconfirm.setOrderNo(paymertRecordEntity.getOrderNo());
        payconfirm.setTotalAmount(paymertRecordEntity.getTotalAmount());
        payconfirm.setCurrency(ParamUtil.getParamInfoByKey("Currency"));
        payconfirm.setSmsCode(ParamUtil.getParamInfoByKey("SmsCode"));
        Map<String, String> map = payConfirmResponse(payconfirm);
        String response = map.get("response");
        TreeMap<String, Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        paymertRecordEntity.setResponseData(response);
        String type = "";
        try {
            type = "0";
            paymertRecordEntity.preUpdate();
        }catch (Exception e){
            type = "1";
            paymertRecordEntity.setUpdateBy("网商异步通知");
            paymertRecordEntity.setUpdateTime(new Date());
        }
        if(mapRespInfo.containsKey("ResultStatus")&& MerchRegisterConstant.registerStatus.fail.equals(mapRespInfo.get("ResultStatus"))){
            paymertRecordEntity.setPayStatus(PayConstant.payStatus.fail);
            if("0".equals(type)){
                consignorMoneyService.update(paymertRecordEntity.getPayeeMerchId(),paymertRecordEntity.getTotalAmount());
            }
        }else {
            paymertRecordEntity.setPayStatus(PayConstant.payStatus.success);
        }
        paymertRecordEntity.setConfirmRequestData(map.get("request"));
        paymertRecordEntity.setConfirmResponseData(response);

        paymentRecordDao.updateConfirm(paymertRecordEntity);
        return paymertRecordEntity;
    }


    public Map<String, String> payConfirmResponse(PayconfirmEntity payconfirm) throws Exception {
        String function = Constant.function.payconfirm;
        TreeMap<String, String> form = MapTrunPojo.object2Map(payconfirm);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        Map<String, String> map = new HashMap<>();
        map.put("request",param);
        map.put("response",response);
        return map;
    }


    @Override
    public TreeMap<String, String> payQuery(String code) throws Exception {

        String function = Constant.function.payQuery;
        PaymertRecordEntity paymertRecordEntity = paymentRecordDao.selectByCode(code);
        PayQueryEntity payQuery= new PayQueryEntity();
        payQuery.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        payQuery.setPayerMerchantId(paymertRecordEntity.getPayerMerchId());
        payQuery.setPayeeId(paymertRecordEntity.getPayeeMerchId());
        payQuery.setPayeeType("MERCHANT");
        payQuery.setOutTradeNo(paymertRecordEntity.getOutTradeNo());
        payQuery.setOrderNo(paymertRecordEntity.getOrderNo());
        TreeMap<String, String> form = MapTrunPojo.object2Map(payQuery);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        TreeMap<String, String> mapBody= XmlToMap.DocumentMapBody(response);
        return mapBody;
    }


}
