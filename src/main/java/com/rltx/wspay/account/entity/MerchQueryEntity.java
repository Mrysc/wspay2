package com.rltx.wspay.account.entity;

import lombok.Data;

/**
 * 入驻结果查询
 */
@Data
public class MerchQueryEntity {

    private String isvOrgId;

    private String orderNo;

    public MerchQueryEntity(){}

    public MerchQueryEntity(
            String      isvOrgId,
            String      orderNo

    ){
        this.isvOrgId=isvOrgId;
        this.orderNo=orderNo;
    }

}
