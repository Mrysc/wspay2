package com.rltx.wspay.account.entity;

import lombok.Data;

/**
 * 开户表
 */
@Data
public class AccountOpenEntity {

    private String isvOrgId;

    private String merchantId;

    //PREPAY  开预付子户， TRADE_DEPOSIT 开保证金子户 物流企业其他子户可以不用关注 ，注意值即可
    private String acctType;

    private String outTradeNo;
    public AccountOpenEntity(){}

    public AccountOpenEntity(
            String      isvOrgId,
            String      merchantId,
            String      acctType,
            String      outTradeNo
    ){
        this.isvOrgId=isvOrgId;
        this.merchantId=merchantId;
        this.acctType=acctType;
        this.outTradeNo=outTradeNo;

    }

}
