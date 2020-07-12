package com.rltx.wspay.bindbankcard.form;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/21 10:05.
 * Description:
 */

@Data
public class DriverBankCardBindForm implements Serializable {

    private String bankAccountName; //银行卡账户名

    private String bankAccIdNumber; //银行账户的身份证号

    private String bankCardNo; //银行卡号

    private String phone;//手机号,银行卡预留手机号

    private String driverCode; //司机code

    private String driverName; //司机名字

    private String driverIdNumber; //司机身份证号




    private String frontResourceCode; //身份证正面
    private String backResourceCode; //身份证背面

}
