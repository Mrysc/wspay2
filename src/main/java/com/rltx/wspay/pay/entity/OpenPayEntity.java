package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class OpenPayEntity {

    private String isvOrgId;

    private String merchantId;

    private String outTradeNo;

    private String  requestTime;


    public OpenPayEntity(){}

    public OpenPayEntity(String isvOrgId, String merchantId, String outTradeNo, String requestTime) {
        this.isvOrgId = isvOrgId;
        this.merchantId = merchantId;
        this.outTradeNo = outTradeNo;
        this.requestTime = requestTime;
    }

}
