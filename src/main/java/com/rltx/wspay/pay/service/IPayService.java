package com.rltx.wspay.pay.service;



import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.pay.entity.PaymertRecordEntity;

import java.util.TreeMap;

public interface IPayService {

    TreeMap<String, Object> balancePay(MerchRegisterEntity payer, MerchRegisterEntity payee, String totalAmount, String paybillNo) throws Exception;
    TreeMap<String,String> openPay(String code) throws Exception;
    PaymertRecordEntity payConfirm(String code) throws Exception;
    TreeMap<String,String> payQuery(String code) throws Exception;


}
