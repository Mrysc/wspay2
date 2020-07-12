package com.rltx.wspay.withdrawal.entity;

import lombok.Data;

@Data
public class ApplyConfirmEntity {

    private String isvOrgId;

    private String merchantId;

    private String outTradeNo;

    private String orderNo;

    private String  platformFee;

    private String feeCurrency;

    private String totalAmount;

    private String  currency;

    private String smsCode;

    private String memo;

}
