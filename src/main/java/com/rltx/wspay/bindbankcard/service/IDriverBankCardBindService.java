package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.bindbankcard.form.DriverBankCardBindForm;
import com.rltx.wspay.bindbankcard.result.BankCardBindResult;

/**
 * Created by  zl on 2020/6/21 10:04.
 * Description:
 * 绑卡的业务逻辑
 */
public interface IDriverBankCardBindService {

    BankCardBindResult bindBankCard(DriverBankCardBindForm driverBankCardBindForm);

    /***
     * 验证是本人银行卡还是 他人银行卡
     * 根据账户名和身份证号验证是否是 绑本人卡
     * 如果是本人卡，返回true；
     * 他人卡，返回false
     *
     * @param driverBankCardBindForm
     * @return
     */
    Boolean validateCardIsSelfOrOther(DriverBankCardBindForm driverBankCardBindForm);
}
