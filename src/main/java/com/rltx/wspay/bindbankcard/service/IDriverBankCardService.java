package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.bindbankcard.entity.DriverBankCardEntity;

/**
 * Created by  zl on 2020/6/29 9:04.
 * Description:
 */
public interface IDriverBankCardService {

    void save(DriverBankCardEntity driverBankCardEntity);

    DriverBankCardEntity selectByBankCardNo(String bankCardNo);
}
