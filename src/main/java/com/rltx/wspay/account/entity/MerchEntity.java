package com.rltx.wspay.account.entity;

import lombok.Data;
/*
*入驻
 */
@Data
public class MerchEntity {

    private String isvOrgId;
    private String outTradeNo;
    private String outMerchantId;
    private String merchantId;
    private String merchantType;
    private String merchantName;
    private String dealType;
    private String merchantDetail;
    private String bankCardParam;
    private String authCode;
    private String mcc;
    private String cloudFundsInfo;
    private String bussAuthParam;

}
