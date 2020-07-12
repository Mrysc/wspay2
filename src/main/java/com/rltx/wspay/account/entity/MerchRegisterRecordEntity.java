package com.rltx.wspay.account.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

/**
 * 商户入驻记录表
 */
@Data
public class MerchRegisterRecordEntity extends BaseEntity {

    private String outTradeNo;
    private String merchId;
    private String outMerchId;
    private String merchUserCode;
    private String merchName;
    private String type;
    private String isPersonal;
    private String requestData;
    private String responseData;
    private String orderNo;
    private String resultStatus;
    private String phone;
    private String name;
    private String merchType;

}
