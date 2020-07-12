package com.rltx.wspay.bindbankcard.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:18.
 * Description:
 * 收款人（绑定非本人银行卡）入驻 平台
 */
@Data
public class OtherMerchRegisterEntity extends BaseEntity implements Serializable {

    private String payeeUserCode; //他人表中收款人userCode;

    private String outerPayeeUserCode; //申请入驻的外部商户号

    private String merchantId; //网商银行分配的商户号

    private String bankAccName; //账户姓名

    private String payeeIdNo;

    private String bankCardNo; //绑定的银行卡号

    private String phone;

    private String  validateStatus; //有效状态 ： 有效S  未知处理中U  失败F





















}
