package com.rltx.wspay.pay.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

@Data
public class PaymertRecordEntity extends BaseEntity {

    private String code;
    private String payerMerchId;
    private String payerOutMerchId;
    private String payerUserCode;
    private String payerName;
    private String payerType;
    private String payerMerchType;
    private String payeeMerchId;
    private String payeeOutMerchId;
    private String payeeUserCode;
    private String payeeName;
    private String payeeType;
    private String payeeMerchType;
    private String payStatus;
    private String requestData;
    private String responseData;
    private String confirmRequestData;
    private String confirmResponseData;
    private String outTradeNo;
    private String totalAmount;
    private String orderNo;
    private String paybillNo;

}
