package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.bindbankcard.dao.SelfRegisterBindPendRecDao;
import com.rltx.wspay.bindbankcard.entity.SelfRegisterBindPendRecEntity;
import com.rltx.wspay.bindbankcard.service.ISelfRegisterBindPendRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by  zl on 2020/6/26 15:05.
 * Description:
 */
@Service
public class SelfRegisterBindPendRecServiceImpl implements ISelfRegisterBindPendRecService {
    @Autowired
    private SelfRegisterBindPendRecDao selfRegisterBindPendRecDao;
    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;

    @Override
    public void updateMerchantInfo(SelfRegisterBindPendRecEntity recEntity) {
        recEntity.preUpdate();
    }

    @Override
    public void updateMerchantInfoForKafka(SelfRegisterBindPendRecEntity recEntity) {
        recEntity.preUpdateKafka();
        selfRegisterBindPendRecDao.updateMerchantInfo(recEntity);

    }

    @Override
    public void saveSelfBindBankCardPendRec(SelfRegisterBindPendRecEntity entity) {
        String code =  generationCodingService.generateCurrencyCode();
        entity.setCode(code);

        entity.preInsert();
        selfRegisterBindPendRecDao.insert(entity);
    }
}
