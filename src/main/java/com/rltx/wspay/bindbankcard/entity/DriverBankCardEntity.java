package com.rltx.wspay.bindbankcard.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:03.
 * Description:
 * 司机银行卡实体类
 */
@Data
public class DriverBankCardEntity extends BaseEntity implements Serializable {

    private String bankAccountName;

    private String bankCardNo;

    private String idNumber; //身份证号

    private String phone; //手机号

    private String validateStatus; //该字段待定 TODO

}
