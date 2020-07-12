package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.bindbankcard.entity.OtherMerchRegisterEntity;

import java.util.List;

/**
 * Created by  zl on 2020/7/1 19:55.
 * Description:
 */
public interface IOtherMerchRegisterService {

    List<OtherMerchRegisterEntity> selectListByIdNo(String idNo);


}
