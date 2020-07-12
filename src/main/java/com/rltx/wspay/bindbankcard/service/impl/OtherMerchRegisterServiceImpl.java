package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.wspay.bindbankcard.dao.OtherMerchRegisterDao;
import com.rltx.wspay.bindbankcard.entity.OtherMerchRegisterEntity;
import com.rltx.wspay.bindbankcard.service.IOtherMerchRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  zl on 2020/7/1 19:56.
 * Description:
 */
@Service
public class OtherMerchRegisterServiceImpl implements IOtherMerchRegisterService {
    @Autowired
    private OtherMerchRegisterDao otherMerchRegisterDao;

    @Override
    public List<OtherMerchRegisterEntity> selectListByIdNo(String idNo) {
        Map<String,String> params = new HashMap<>();
        params.put("payeeIdNo",idNo);
        List<OtherMerchRegisterEntity> list = otherMerchRegisterDao.selectList(params);
        return list;
    }
}
