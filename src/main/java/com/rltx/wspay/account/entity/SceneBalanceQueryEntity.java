package com.rltx.wspay.account.entity;

public class SceneBalanceQueryEntity {

    private String isvOrgId;

    private String merchantId;

    public SceneBalanceQueryEntity (
            String      isvOrgId,
            String      merchantId

    ){
        this.isvOrgId=isvOrgId;
        this.merchantId=merchantId;
    }


}
