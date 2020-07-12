package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class OrderShareResult {

    private String isvOrgId;

    private String merchantId;

    private String relateOrderNo;

    private String outTradeNo;

    private String totalAmount;

    private String  currency;

    private String payeeFundDetails;
}
