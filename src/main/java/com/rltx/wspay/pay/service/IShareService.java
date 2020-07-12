package com.rltx.wspay.pay.service;


import com.rltx.wspay.pay.entity.PaymertRecordEntity;

import java.util.TreeMap;

public interface IShareService {
    TreeMap<String, Object> orderShare(PaymertRecordEntity paymertRecord) throws Exception;
    TreeMap<String, Object> orderShareDriver(String paybillNo,String merchId) throws Exception;
    String refundShare() throws Exception;
    String refundShareQuery() throws Exception;
    TreeMap<String, Object> shareQuery(String outTradeNo) throws Exception;
}
