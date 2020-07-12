package com.rltx.wspay.bindbankcard.producer.impl;

import com.alibaba.fastjson.JSON;
import com.rltx.wspay.bindbankcard.entity.OtherRegisterBindPendRecEntity;
import com.rltx.wspay.bindbankcard.entity.SelfRegisterBindPendRecEntity;
import com.rltx.wspay.bindbankcard.producer.IBindBankCardProducer;
import com.wl.framework.event.producer.IEventProducer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by  zl on 2020/4/1 18:05.
 * Description:
 */
@Component("bindBankCardProducer")
public class BindBankCardProducerImpl implements IBindBankCardProducer {
    @Resource(name="fwEventProducer")
    private IEventProducer eventProducer;



    @Override
    public void sendBindSelfBankCardMsg(SelfRegisterBindPendRecEntity selfRegisterBindPendRecEntity) {
        String message = JSON.toJSONString(selfRegisterBindPendRecEntity);
        eventProducer.produce("WsCloudPay","bind_self_bank_card",message);
    }

    @Override
    public void sendBindOtherBankCardMsg(OtherRegisterBindPendRecEntity otherRegisterBindPendRecEntity) {
        String message = JSON.toJSONString(otherRegisterBindPendRecEntity);
        eventProducer.produce("WsCloudPay","bind_other_bank_card",message);
    }
}
