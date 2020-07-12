package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class PayconfirmEntity {

    private String isvOrgId;

    private String payerMerchantId;

    private String payeeId;

    private String payeeType;

    private String outTradeNo;

    private String orderNo;

    private String totalAmount;

    private String  currency;

    private String smsCode;

    public PayconfirmEntity(){}

    public PayconfirmEntity(String isvOrgId, String payerMerchantId, String payeeId,String payeeType, String outTradeNo, String orderNo, String totalAmount, String currency, String smsCode) {
        this.isvOrgId = isvOrgId;
        this.payerMerchantId = payerMerchantId;
        this.payeeId = payeeId;
        this.payeeType=payeeType;
        this.outTradeNo = outTradeNo;
        this.orderNo = orderNo;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.smsCode = smsCode;
    }
}
