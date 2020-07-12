package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.bindbankcard.entity.OtherRegisterBindPendRecEntity;

/**
 * Created by  zl on 2020/6/29 15:33.
 * Description:
 */
public interface IOtherRegisterBindPendRecService {

    void saveOtherRegisterBindPendRec(OtherRegisterBindPendRecEntity entity);

    int insertSelect(OtherRegisterBindPendRecEntity entity);

    OtherRegisterBindPendRecEntity selectByBankCardNo(String bankCardNo);
}
