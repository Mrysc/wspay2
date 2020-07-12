package com.rltx.wspay.bindbankcard.producer;


import com.rltx.wspay.bindbankcard.entity.OtherRegisterBindPendRecEntity;
import com.rltx.wspay.bindbankcard.entity.SelfRegisterBindPendRecEntity;

/**
 * Created by  zl on 2020/4/1 17:58.
 * Description:
 */
public interface IBindBankCardProducer {


    void sendBindSelfBankCardMsg(SelfRegisterBindPendRecEntity selfRegisterBindPendRecEntity);

    void sendBindOtherBankCardMsg(OtherRegisterBindPendRecEntity otherRegisterBindPendRecEntity);


}
