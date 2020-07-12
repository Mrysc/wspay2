package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class PayQueryEntity {

    private String isvOrgId;

    private String payerMerchantId;

    private String payeeId;

    private String payeeType;

    private String outTradeNo;

    private String orderNo;

}
