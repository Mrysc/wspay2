package com.rltx.wspay.account.dao;

import com.rltx.wspay.account.entity.MerchRegisterRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("merchRegisterRecordDao")
public interface MerchRegisterRecordDao {

    void insert(@Param("entity") MerchRegisterRecordEntity merchRegisterRecordEntity);

    void update(@Param("entity") MerchRegisterRecordEntity merchRegisterRecordEntity);

    void updateMerchId(@Param("entity") MerchRegisterRecordEntity merchRegisterRecordEntity);

    MerchRegisterRecordEntity selectByMerchUserCode(@Param("merchUserCode") String merchUserCode);

    MerchRegisterRecordEntity selectByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    List<MerchRegisterRecordEntity> selectList(@Param("params") Map<String,Object> params);

}
