package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class RefundShareQueryEntity {

    private String isvOrgId;

    private String merchantId;

    private String relateOrderNo;

    private String outTradeNo;

    private String ShareRefundOrderNo;


    public RefundShareQueryEntity(String isvOrgId, String merchantId, String relateOrderNo, String outTradeNo, String shareRefundOrderNo) {
        this.isvOrgId = isvOrgId;
        this.merchantId = merchantId;
        this.relateOrderNo = relateOrderNo;
        this.outTradeNo = outTradeNo;
        ShareRefundOrderNo = shareRefundOrderNo;
    }

}
