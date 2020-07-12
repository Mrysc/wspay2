package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.account.dao.MemberPersonInfoDao;
import com.rltx.wspay.account.entity.MemberPersonInfoEntity;
import com.rltx.wspay.bindbankcard.dao.OtherMemberPayeeInfoDao;
import com.rltx.wspay.bindbankcard.entity.OtherMemberPayeeInfoEntity;
import com.rltx.wspay.bindbankcard.service.IOtherMemberPayeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  zl on 2020/6/28 16:45.
 * Description:
 */
@Service
public class OtherMemberPayeeInfoServiceImpl implements IOtherMemberPayeeInfoService {
    @Autowired
    private OtherMemberPayeeInfoDao otherMemberPayeeInfoDao;
    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;
    @Autowired
    private MemberPersonInfoDao memberPersonInfoDao;

    @Override
    public OtherMemberPayeeInfoEntity selectByIdNumber(String idNumber) {
        Map<String,String> params = new HashMap<>();
        params.put("idNumber",idNumber);
        OtherMemberPayeeInfoEntity entity = otherMemberPayeeInfoDao.select(params);
        return entity;
    }

    @Override
    public void saveOtherMemberPayeeInfo(OtherMemberPayeeInfoEntity entity) {
        entity.preInsert();
        String code =  generationCodingService.generateCurrencyCode();
        entity.setCode(code);

        otherMemberPayeeInfoDao.insert(entity);
    }

    @Override
    public int insertSelect(OtherMemberPayeeInfoEntity entity) {

        String code =  generationCodingService.generateCurrencyCode();
        entity.setCode(code);
        entity.preInsert();
        int val = otherMemberPayeeInfoDao.insertSelect(entity);
        return val;
    }

    @Override
    public MemberPersonInfoEntity selectDriverInfoByIdNo(String idNo) {
        MemberPersonInfoEntity entity = memberPersonInfoDao.selectResourceImgByIdNumber(idNo);
        return entity;
    }
}