package com.rltx.wspay.account.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

/**
 * 商户入驻记录表
 */
@Data
public class MerchConsignorMoneyEntity extends BaseEntity {

    private String merchId;
    private String merchUserCode;
    private String merchName;
    private Double money;

}
