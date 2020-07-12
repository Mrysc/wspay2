package com.rltx.wspay.account.entity;

import lombok.Data;

/*
*入驻
 */
@Data
public class MerchUpdateEntity {

    private String isvOrgId;
    private String outTradeNo;
    private String merchantId;
    private String bankCardParam;

}
