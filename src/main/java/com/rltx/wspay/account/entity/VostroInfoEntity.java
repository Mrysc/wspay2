package com.rltx.wspay.account.entity;

import lombok.Data;

/**
 * 充值保证金查询VostroInfoList 数据结构
 */
@Data
public class VostroInfoEntity {

    private String orderNo;
    private String payerBankOrgId;
    private String payerCardNo;
    private String payerCardName;
    private String payeeCardNo;
    private String totalAmount;
    private String ccy;
    private String maxRefundAmount;
    private String chargeTime;

}
