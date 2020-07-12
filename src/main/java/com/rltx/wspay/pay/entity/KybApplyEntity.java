package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class KybApplyEntity {

    private String outTradeNo;

    private String isvOrgId;

    private String merchantId;


    public KybApplyEntity(
            String     outTradeNo,
            String     isvOrgId,
            String     merchantId

    ){
        this.outTradeNo=outTradeNo;
        this.isvOrgId=isvOrgId;
        this.merchantId=merchantId;

    }

}
