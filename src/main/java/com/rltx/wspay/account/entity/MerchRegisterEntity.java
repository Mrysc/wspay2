package com.rltx.wspay.account.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

/**
 * 商户入驻记录表
 */
@Data
public class MerchRegisterEntity extends BaseEntity {

    private String merchId;
    private String outMerchId;
    private String merchUserCode;
    private String merchName;
    private String type;
    private String merchType;
    private String phone;
    private String name;
    private String cardNo;
    private String cardName;
    private String openingBank;
    private String contactLine;
    private String accountType;
    private String basic;
    private String prepay;
    private String tradeDeposit;
    private String openPay;
    private String registerStatus;
}
