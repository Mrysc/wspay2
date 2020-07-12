package com.rltx.wspay.pay.entity;

import lombok.Data;

@Data
public class RefundQueryEntity {

    private String isvOrgId;

    private String relateOrderNo;

    private String participantType;

    private String participantId;

    private String outRefundNo;

    private String refundOrderNo;

    public RefundQueryEntity(String isvOrgId, String relateOrderNo, String participantType, String participantId, String outRefundNo, String refundOrderNo) {
        this.isvOrgId = isvOrgId;
        this.relateOrderNo = relateOrderNo;
        this.participantType = participantType;
        this.participantId = participantId;
        this.outRefundNo = outRefundNo;
        this.refundOrderNo = refundOrderNo;
    }
}
