package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class BalancePayEntity {

    private String isvOrgId;

    private String payerMerchantId;

    private String payeeId;

    private String payeeType;

    private String outTradeNo;

    private String totalAmount;

    private String  currency;

    private String body;
    public BalancePayEntity(){}
    public BalancePayEntity(String isvOrgId, String payerMerchantId, String payeeId, String payeeType,String outTradeNo, String totalAmount, String currency, String body) {
        this.isvOrgId = isvOrgId;
        this.payerMerchantId = payerMerchantId;
        this.payeeId = payeeId;
        this.payeeType=payeeType;
        this.outTradeNo = outTradeNo;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.body = body;
    }

}
