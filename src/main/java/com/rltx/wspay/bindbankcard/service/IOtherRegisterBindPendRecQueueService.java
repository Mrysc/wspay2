package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.bindbankcard.entity.OtherRegisterBindPendRecQueueEntity;

/**
 * Created by  zl on 2020/6/29 15:33.
 * Description:
 */
public interface IOtherRegisterBindPendRecQueueService {

    void save(OtherRegisterBindPendRecQueueEntity entity);

    OtherRegisterBindPendRecQueueEntity selectByBankCardNo(String bankCardNo);

    OtherRegisterBindPendRecQueueEntity selectByFourElements(String accName, String idNo, String bankCardNo, String phone);
}
