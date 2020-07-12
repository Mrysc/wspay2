package com.rltx.wspay.bindbankcard.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:05.
 * Description:
 * 司机银行卡关系实体类
 */
@Data
public class DriverBankCardRelationEntity extends BaseEntity implements Serializable {

    private String driverUserCode; //司机userCode

    private String driverName; //司机名字

    private String bankAccountName; //银行卡户名

    private String bankCardNo; //银行卡号

    private String bankAccountIdNo; //银行卡实际持有人 身份证号

    private String bankAccountPhone; //银行卡实际持有人 手机号

    private String selfOrOther; //本人卡还是他人卡  本人卡s  他人卡 o  (字母哦)

    private String merchantId; //银行卡账户对应的商户号

    private String bankCardCode; //银行卡code ，对应driver_bank_card中code

    private String bindStatus; //绑定状态，只记录绑定失败的 success ,fail,binding

    private String bindFailMsg; //绑定失败备注


}
