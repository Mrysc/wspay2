package com.rltx.wspay.account.entity;

import lombok.Data;

/**
 * 余额查询
 */
@Data
public class BalanceQueryEntity {

    private String isvOrgId;

    private String merchantId;


    public BalanceQueryEntity (){}
    public BalanceQueryEntity (
            String      isvOrgId,
            String      merchantId

    ){
        this.isvOrgId=isvOrgId;
        this.merchantId=merchantId;
    }

}
