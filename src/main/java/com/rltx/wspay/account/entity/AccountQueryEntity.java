package com.rltx.wspay.account.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 开户查询
 */
@Data
public class AccountQueryEntity {

    private String isvOrgId;

    private String merchantId;

    //PREPAY  开预付子户， TRADE_DEPOSIT 开保证金子户 物流企业其他子户可以不用关注 ，注意值即可
    private String acctType;

    public AccountQueryEntity(){}

    public AccountQueryEntity(
            String      isvOrgId,
            String      merchantId,
            String      acctType

    ){
        this.isvOrgId=isvOrgId;
        this.merchantId=merchantId;
        this.acctType=acctType;
    }

}
