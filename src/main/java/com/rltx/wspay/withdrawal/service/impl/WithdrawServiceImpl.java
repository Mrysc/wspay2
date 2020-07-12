package com.rltx.wspay.withdrawal.service.impl;

import com.rltx.common.vo.CommParamsVo;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.utils.constant.ParamUtil;
import com.rltx.wspay.withdrawal.dao.WithdrawDao;
import com.rltx.wspay.withdrawal.entity.ApplyConfirmEntity;
import com.rltx.wspay.withdrawal.entity.WithdrawApplyEntity;
import com.rltx.wspay.withdrawal.entity.WithdrawQueryEntity;
import com.rltx.wspay.withdrawal.service.IWithdrawService;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rltx.wspay.commom.*;

import java.util.TreeMap;
import java.util.UUID;


@Service
public class WithdrawServiceImpl implements IWithdrawService {

    @Autowired
    private MerchRegisterDao merchRegisterDao;
    @Autowired
    private WithdrawDao withdrawDao;


    @Override
    public TreeMap<String,Object> applyConfirm(String outTradeNo) throws Exception {

        String function = Constant.function.applyConfirm;
        ApplyConfirmEntity applyConfirm = new ApplyConfirmEntity();
        WithdrawApplyEntity withdrawApplyEntity =withdrawDao.select(outTradeNo);
        applyConfirm.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        applyConfirm.setMerchantId(withdrawApplyEntity.getMerchantId());
        applyConfirm.setOutTradeNo(withdrawApplyEntity.getOutTradeNo());
        applyConfirm.setOrderNo(withdrawApplyEntity.getOrderNo());
        applyConfirm.setPlatformFee(withdrawApplyEntity.getPlatformFee());
        applyConfirm.setFeeCurrency(withdrawApplyEntity.getFeeCurrency());
        applyConfirm.setTotalAmount(withdrawApplyEntity.getTotalAmount());
        applyConfirm.setCurrency(withdrawApplyEntity.getCurrency());
        applyConfirm.setSmsCode("");
        applyConfirm.setMemo(withdrawApplyEntity.getMemo());
        withdrawApplyEntity.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        TreeMap<String, String> form = MapTrunPojo.object2Map(withdrawApplyEntity);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion, form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify = XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        withdrawApplyEntity.setFlag("2");
        withdrawDao.updateFlag(withdrawApplyEntity);
        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        mapRespInfo.putAll(mapBody);
        return mapRespInfo;
    }

    @Override
    public TreeMap<String,Object> withdrawApply(String userCode,String money) throws Exception {
//      OutTradeNo  外部交易流水号。合作方系统生成的外部交易流水号，同一交易流水号被视为同一笔交易。
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByMerchUserCode(userCode);
        WithdrawApplyEntity withdrawApplyEntity = new WithdrawApplyEntity();
        withdrawApplyEntity.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        withdrawApplyEntity.setMerchantId(merchRegisterEntity.getMerchId());
        withdrawApplyEntity.setTotalAmount(money);
        withdrawApplyEntity.setPlatformFee("0");
        withdrawApplyEntity.setMemo("");
        withdrawApplyEntity.setOutTradeNo(UUID.randomUUID().toString().replaceAll("-",""));

        String response = null;
        try {
            TreeMap<String, String> form = MapTrunPojo.object2Map(withdrawApplyEntity);
            String param = DomCreateRequest.createRequestXml(Constant.function.withdrawApply, XmlVersion.defaultVersion, form);
            boolean responseVerify = XmlSignUtil.verifyFromYourSelf(param);
            if (responseVerify) {
                response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
            }

            TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
            withdrawApplyEntity.setFlag("1");
            withdrawApplyEntity.setOrderNo(mapBody.get("OrderNo"));
            withdrawApplyEntity.preInsert();
            withdrawDao.insert(withdrawApplyEntity);
        } catch (Exception e) {
            e.printStackTrace();
            response = null;
        } finally {
            TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
            TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
            mapRespInfo.putAll(mapBody);
            return mapRespInfo;
        }
    }

    @Override
    public TreeMap<String,Object> withdrawQuery(String outTradeNo) throws Exception {

        String function = Constant.function.withdrawQuery;

        WithdrawQueryEntity withdrawQuery = new WithdrawQueryEntity(
                HttpMain.IsvOrgId, HttpMain.merchantId, "", ""
        );


        TreeMap<String, String> form = MapTrunPojo.object2Map(withdrawQuery);


        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion, form);

        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify = XmlSignUtil.verifyFromYourSelf(param);

        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);

        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        mapRespInfo.putAll(mapBody);
        return mapRespInfo;
    }
}
