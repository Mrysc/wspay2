package com.rltx.wspay.bindbankcard.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:21.
 * Description:
 * 绑定他人卡时，他人入驻开户绑卡待处理队列 记录
 */
@Data
public class OtherRegisterBindPendRecBaseEntity extends BaseEntity implements Serializable {

    private String driverCode; //司机userCode

    private String payeeUserCode; //收款人userCode

    private String payeeBankAccountName; //收款人银行卡户名

    private String payeeBankCardNo;  //收款人银行卡号

    private String payeeIdNo; //收款人身份证号

    private String payeePhone; //收款人手机号

    private String merchantId;// 网商银行分配给收款人的商户号



}
