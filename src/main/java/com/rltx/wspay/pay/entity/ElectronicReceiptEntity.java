package com.rltx.wspay.pay.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class ElectronicReceiptEntity extends BaseEntity {
    private String paymentCode;
    private String isvOrgId;
    private String merchantId;
    private String orderNo;
    private String outTradeNo;
    private String receiptNo;
    private String status;
    private String pdfDownloadUrl;
}
