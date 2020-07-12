package com.rltx.wspay.notice.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

@Data
public class ChargeNotifyRecordEntity extends BaseEntity {

    private String merchantId;
    private String merchUserCode;
    private String merchName;
    private String orderNo;
    private String transNo;
    private String channelId;
    private String payerBankOrgId;
    private String payerCardNo;
    private String payerCardName;
    private String payeeCardNo;
    private String payeeCardName;
    private String totalAmount;
    private String currency;
    private String transferDate;
    private String memo;
    private String extInfo;
    private String status;
    private String ostroType;
    private String vostroScene;

}
