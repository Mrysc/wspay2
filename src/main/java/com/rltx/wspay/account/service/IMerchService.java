package com.rltx.wspay.account.service;


import com.rltx.wspay.account.entity.MerchEntity;
import com.rltx.wspay.account.result.MerchRegisterResult;

import java.util.TreeMap;

public interface IMerchService {
    MerchRegisterResult enterpriseRegister(String code) throws Exception;
    MerchRegisterResult personRegister(String code) throws Exception;
    TreeMap<String,Object> registerQuery(String outTradeNo) throws Exception;
    TreeMap<String,Object> merchQuery(String code) throws Exception;

    String register(MerchEntity register) throws Exception;

}
