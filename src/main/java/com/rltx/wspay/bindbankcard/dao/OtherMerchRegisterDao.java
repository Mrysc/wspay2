package com.rltx.wspay.bindbankcard.dao;

import com.rltx.wspay.bindbankcard.entity.OtherMerchRegisterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by  zl on 2020/6/10 14:14.
 * Description:
 * 收款人（绑定非本人银行卡）入驻平台
 */
@Mapper
public interface OtherMerchRegisterDao {

    void insert(OtherMerchRegisterEntity entity);

    List<OtherMerchRegisterEntity> selectList(@Param("params") Map<String, String> param);

    OtherMerchRegisterEntity select(@Param("params") Map<String, String> param);

}
