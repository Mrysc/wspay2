package com.rltx.wspay.withdrawal.entity;

import lombok.Data;

@Data
public class WithdrawQueryEntity {

    private String isvOrgId;

    private String merchantId;

    private String outTradeNo;

    private String orderNO;

    public WithdrawQueryEntity(String isvOrgId, String merchantId, String outTradeNo, String orderNO) {
        this.isvOrgId = isvOrgId;
        this.merchantId = merchantId;
        this.outTradeNo = outTradeNo;
        this.orderNO = orderNO;
    }
}
