package com.rltx.wspay.notice.entity;

import lombok.Data;

@Data
public class MerchResponseEntity {

    private String merchantId;
    private String outTradeNo;
    private String orderNo;
    private String registerStatus;
    private String failReason;
    private String outMerchantId;

}
