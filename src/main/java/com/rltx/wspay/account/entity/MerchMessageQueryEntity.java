package com.rltx.wspay.account.entity;

import lombok.Data;

/**
 * 商户信息查询接口
 */
@Data
public class MerchMessageQueryEntity {

    private String isvOrgId;

    private String merchantId;

//    private String outMerchantId;
}
