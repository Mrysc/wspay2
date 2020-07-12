package com.rltx.wspay.pay.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class OrderShareEntity extends BaseEntity {

    private String merchId;
    private String relateOrderNo;
    private String outTradeNo;
    private String merchUserCode;
    private String merchName;
    private String type;
    private String totalAmount;
    private String payeeFundDetails;
    private String amount;
    private String participantId;
    private String participantType;
    private String purpose;
    private String shareOrderNo;
    private String requestData;
    private String responseData;
    private String resultStatus;

    private String status;
    private String notifyData;
    private Date shareDate;
}
