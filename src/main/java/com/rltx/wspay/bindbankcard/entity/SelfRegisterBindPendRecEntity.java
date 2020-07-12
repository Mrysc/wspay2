package com.rltx.wspay.bindbankcard.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:21.
 * Description:
 * 绑定本人卡时，本人入驻开户绑卡待处理记录
 */
@Data
public class SelfRegisterBindPendRecEntity extends BaseEntity implements Serializable {

    private String driverCode; //司机userCode

    private String driverBankCardRelationCode; //driver_bank_card_relation表code

    private String driverBankAccountName; //收款人银行卡户名

    private String driverBankCardNo;  //收款人银行卡号

    private String driverIdNo; //收款人身份证号

    private String phone;//手机号

    private String merchantId;//商户号
    private String driverRegisterStatus; //收款人入驻状态 入驻Y 未入住N，处理中U

    private String driverBindCardStatus; //收款人商户绑卡状态 已绑Y 未绑定N

    private Integer driverRegisterNum; //申请入驻调用接口次数

    private Integer driverBindNum; //绑卡调用次数。理论上绑失败了就不再处理了
    










}
