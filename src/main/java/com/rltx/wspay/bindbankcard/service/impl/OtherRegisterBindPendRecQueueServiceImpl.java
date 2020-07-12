package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.bindbankcard.dao.OtherRegisterBindPendRecQueueDao;
import com.rltx.wspay.bindbankcard.entity.OtherRegisterBindPendRecQueueEntity;
import com.rltx.wspay.bindbankcard.service.IOtherRegisterBindPendRecQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  zl on 2020/7/1 16:15.
 * Description:
 */

@Service
public class OtherRegisterBindPendRecQueueServiceImpl implements IOtherRegisterBindPendRecQueueService {

    @Autowired
    private OtherRegisterBindPendRecQueueDao otherRegisterBindPendRecQueueDao;

    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;

    @Override
    public void save(OtherRegisterBindPendRecQueueEntity entity) {
        entity.preInsert();
        String code =  generationCodingService.generateCurrencyCode();
        entity.setCode(code);
        otherRegisterBindPendRecQueueDao.insert(entity);
    }

    @Override
    public OtherRegisterBindPendRecQueueEntity selectByBankCardNo(String bankCardNo) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("payeeBankCardNo",bankCardNo);
        OtherRegisterBindPendRecQueueEntity entity = otherRegisterBindPendRecQueueDao.select(paramMap);
        return entity;
    }

    @Override
    public OtherRegisterBindPendRecQueueEntity selectByFourElements(String accName, String idNo, String bankCardNo, String phone) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("payeeAccName",accName);
        paramMap.put("payeeIdNo",idNo);
        paramMap.put("payeeBankCardNo",bankCardNo);
        paramMap.put("payeePhone",phone);
        OtherRegisterBindPendRecQueueEntity entity = otherRegisterBindPendRecQueueDao.selectByFourElements(paramMap);
        return entity;
    }
}
