package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class RefundShareEntity {

    private String isvOrgId;

    private String merchantId;

    private String relateOrderNo;

    private String outTradeNo;

    private String totalAmount;

    private String currency;

    private String Reason;

    public RefundShareEntity(String isvOrgId, String merchantId, String relateOrderNo, String outTradeNo, String totalAmount, String currency, String reason) {
        this.isvOrgId = isvOrgId;
        this.merchantId = merchantId;
        this.relateOrderNo = relateOrderNo;
        this.outTradeNo = outTradeNo;
        this.totalAmount = totalAmount;
        this.currency = currency;
        Reason = reason;
    }
}
