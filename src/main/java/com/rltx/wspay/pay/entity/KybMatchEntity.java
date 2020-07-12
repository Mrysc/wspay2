package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class KybMatchEntity {

    private String  outTradeNo;

    private String  isvOrgId;

    private String  merchantId;

    // 打款申请响应返回 verifyId
    private String  verifyId;

    // 打款申请响应返回 orderNo
    private String  orderNo;

    //联调技术反馈 金额 直接传入至此字段
    private String  amount;

    //这里直接写死 156   156是人名币得别称
    private String  currency;


    public KybMatchEntity(
            String  outTradeNo,
            String  isvOrgId,
            String  merchantId,
            String  verifyId,
            String  orderNo,
            String  amount
    ){
        this.outTradeNo=outTradeNo;
        this.isvOrgId=isvOrgId;
        this.merchantId=merchantId;
        this.verifyId=verifyId;
        this.orderNo=orderNo;
        this.amount=amount;
        this.currency="156";
    }

}
