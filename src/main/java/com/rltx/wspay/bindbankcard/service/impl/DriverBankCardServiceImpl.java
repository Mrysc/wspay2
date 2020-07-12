package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.bindbankcard.dao.DriverBankCardDao;
import com.rltx.wspay.bindbankcard.entity.DriverBankCardEntity;
import com.rltx.wspay.bindbankcard.service.IDriverBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by  zl on 2020/6/29 9:05.
 * Description:
 */
@Service
public class DriverBankCardServiceImpl implements IDriverBankCardService {
    @Autowired
    private DriverBankCardDao driverBankCardDao;
    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;


    @Override
    public void save(DriverBankCardEntity driverBankCardEntity) {
        String code =  generationCodingService.generateCurrencyCode();
        driverBankCardEntity.setCode(code);

        driverBankCardEntity.preInsert();
        driverBankCardDao.insert(driverBankCardEntity);

    }

    @Override
    public DriverBankCardEntity selectByBankCardNo(String bankCardNo) {
        DriverBankCardEntity entity = new DriverBankCardEntity();
        entity.setBankCardNo(bankCardNo);
        entity = driverBankCardDao.select(entity);
        return entity;
    }
}
