package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.bindbankcard.entity.SelfRegisterBindPendRecEntity;

/**
 * Created by  zl on 2020/6/26 15:05.
 * Description:
 */
public interface ISelfRegisterBindPendRecService {

    //更新商户merchantId,register_status
    void updateMerchantInfo(SelfRegisterBindPendRecEntity recEntity);

    void updateMerchantInfoForKafka(SelfRegisterBindPendRecEntity recEntity);

    void saveSelfBindBankCardPendRec(SelfRegisterBindPendRecEntity entity);
}
