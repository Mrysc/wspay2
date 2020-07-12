package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class RefundApplyEntity {

    private String isvOrgId;

    private String participantType;

    private String participantId;

    private String relateOrderNo;

    private String outRefundNo;

    private String refundAmount;

    private String  currency;

    private String refundReason;

    public RefundApplyEntity(String isvOrgId, String participantType, String participantId, String relateOrderNo, String outRefundNo, String refundAmount, String currency, String refundReason) {
        this.isvOrgId = isvOrgId;
        this.participantType = participantType;
        this.participantId = participantId;
        this.relateOrderNo = relateOrderNo;
        this.outRefundNo = outRefundNo;
        this.refundAmount = refundAmount;
        this.currency = currency;
        this.refundReason = refundReason;
    }
}
