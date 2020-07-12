package com.rltx.wspay.notice.entity;

import com.rltx.wspay.commom.HttpMain;
import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;

@Data
public class WithdrawNotifyEntity extends BaseEntity {
    private String isvOrgId;
    private String merchantId;
    private String outTradeNo;
    private String orderNo;
    private String totalAmount;
    private String currency;
    private String platformFee;
    private String feeCurrency;
    private String status;
    private String bankCardNo;
    private String bankCertName;
    private String withdrawApplyDate;
    private String withdrawFinishDate;
    private String errorDesc;
}
