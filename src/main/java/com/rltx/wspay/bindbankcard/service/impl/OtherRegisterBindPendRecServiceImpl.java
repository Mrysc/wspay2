package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.bindbankcard.dao.OtherRegisterBindPendRecDao;
import com.rltx.wspay.bindbankcard.entity.OtherRegisterBindPendRecEntity;
import com.rltx.wspay.bindbankcard.service.IOtherRegisterBindPendRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  zl on 2020/6/29 15:34.
 * Description:
 */
@Service
public class OtherRegisterBindPendRecServiceImpl implements IOtherRegisterBindPendRecService {

    @Autowired
    private OtherRegisterBindPendRecDao otherRegisterBindPendRecDao;

    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;


    @Override
    public void saveOtherRegisterBindPendRec(OtherRegisterBindPendRecEntity entity) {
        entity.preInsert();
        String code =  generationCodingService.generateCurrencyCode();
        entity.setCode(code);
        otherRegisterBindPendRecDao.insert(entity);
    }

    @Override
    public int insertSelect(OtherRegisterBindPendRecEntity entity) {
        entity.preInsert();
        String code =  generationCodingService.generateCurrencyCode();
        entity.setCode(code);
        int val = otherRegisterBindPendRecDao.insertSelect(entity);
        return val;
    }

    @Override
    public OtherRegisterBindPendRecEntity selectByBankCardNo(String bankCardNo) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("payeeBankCardNo",bankCardNo);
        OtherRegisterBindPendRecEntity entity = otherRegisterBindPendRecDao.select(paramMap);
        return entity;
    }
}
