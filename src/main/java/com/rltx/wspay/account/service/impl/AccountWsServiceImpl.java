package com.rltx.wspay.account.service.impl;

import com.google.gson.Gson;
import com.rltx.framework.log.support.BusinessException;
import com.rltx.wspay.account.dao.MerchBankAccountDao;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.*;
import com.rltx.wspay.account.service.IAccountWsService;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.constant.AcctType;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class AccountWsServiceImpl implements IAccountWsService {

    @Autowired
    private MerchBankAccountDao merchBankAccountDao;
    @Autowired
    private MerchRegisterDao merchRegisterDao;

    @Override
    public TreeMap<String,Object> accountOpen(String code) throws Exception {
        String  function= Constant.function.accountOpen;
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByCode(code);
        if(ObjectUtils.isEmpty(merchRegisterEntity)){
            throw new BusinessException("用户未入住不可开户");
        }
        if(!StringUtils.isEmpty(merchRegisterEntity.getTradeDeposit())){
            throw new BusinessException("已有保证金子户");
        }
        AccountOpenEntity accountOpen = new AccountOpenEntity();
        accountOpen.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        accountOpen.setMerchantId(merchRegisterEntity.getMerchId());
        accountOpen.setAcctType(AcctType.TRADE_DEPOSIT);
        accountOpen.setOutTradeNo(TradeNoUtils.getTradeNo32());
        TreeMap<String, String> form = MapTrunPojo.object2Map(accountOpen);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        MerchBankAccountEntity merchBankAccountEntity = new MerchBankAccountEntity();
        merchBankAccountEntity.setMerchId(accountOpen.getMerchantId());
        merchBankAccountEntity.setAcctType(accountOpen.getAcctType());
        merchBankAccountEntity.setOutTradeId(accountOpen.getOutTradeNo());
        merchBankAccountEntity.setMerchUserCode(merchRegisterEntity.getMerchUserCode());
        merchBankAccountEntity.setMerchName(merchRegisterEntity.getMerchName());
        merchBankAccountEntity.setType(merchRegisterEntity.getType());
        merchBankAccountEntity.setRequestData(param);
        merchBankAccountEntity.preInsert();
        merchBankAccountDao.insert(merchBankAccountEntity);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        TreeMap<String,String> map= XmlToMap.DocumentMap(response);
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        merchBankAccountEntity.setResponseData(response);
        if (mapBody.containsKey("BranchNo")){
            merchBankAccountEntity.setBranchNo(mapBody.get("BranchNo"));
        }
        if (mapBody.containsKey("BranchName")){
            merchBankAccountEntity.setBranchName(mapBody.get("BranchName"));
        }
        if (mapBody.containsKey("BankCardNo")){
            merchBankAccountEntity.setBankCardNo(mapBody.get("BankCardNo"));
        }
        if (mapBody.containsKey("BankCertName")){
            merchBankAccountEntity.setBankCertName(mapBody.get("BankCertName"));
        }
        if (mapBody.containsKey("RespInfo")){
            TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
            merchBankAccountEntity.setResultStatus(mapRespInfo.get("ResultStatus").toString());
        }
        merchBankAccountEntity.preUpdate();
        merchBankAccountDao.update(merchBankAccountEntity);

        merchRegisterEntity.setTradeDeposit(merchBankAccountEntity.getBankCardNo());
        merchRegisterDao.updateTradeDeposit(merchRegisterEntity);
        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        mapRespInfo.putAll(mapBody);
        mapRespInfo.remove("RespInfo");
        return mapRespInfo;
    }

    @Override
    public Map<String,String> accountQuery(String code) throws Exception {
        String  function= Constant.function.accountQuery;
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByCode(code);
        if(ObjectUtils.isEmpty(merchRegisterEntity)){
            throw new BusinessException("用户未入住");
        }
        Gson gson = new Gson();
        AccountQueryEntity accountQuery = new AccountQueryEntity();
        accountQuery.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        accountQuery.setMerchantId(merchRegisterEntity.getMerchId());

        accountQuery.setAcctType(AcctType.BASIC);
        TreeMap<String, String> form = MapTrunPojo.object2Map(accountQuery);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        TreeMap<String,Object> mapBody= XmlToMap.DocumentMapType(response,"body");
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(mapBody.get("AcctMap").toString());
        Map<String,String> map = new HashMap<>();
        map = gson.fromJson(new String(bytes), map.getClass());

        accountQuery.setAcctType(AcctType.TRADE_DEPOSIT);
        TreeMap<String, String> form1 = MapTrunPojo.object2Map(accountQuery);
        String param1 = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form1);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify1 =  XmlSignUtil.verifyFromYourSelf(param1);
        String response1 = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param1);
        TreeMap<String,Object> mapBody1= XmlToMap.DocumentMapType(response1,"body");
        byte[] bytes1 = decoder.decodeBuffer(mapBody1.get("AcctMap").toString());
        Map<String,String> map1 = new HashMap<>();
        map1 = gson.fromJson(new String(bytes1), map1.getClass());
        if(MapUtils.isNotEmpty(map1)){
            map.put("TRADE_DEPOSIT",map1.get("TRADE_DEPOSIT"));
        }
        return map;
    }


}
