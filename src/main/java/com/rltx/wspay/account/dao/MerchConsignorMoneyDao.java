package com.rltx.wspay.account.dao;

import com.rltx.wspay.account.entity.MerchConsignorMoneyEntity;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("merchConsignorMoneyDao")
public interface MerchConsignorMoneyDao {

    void insert(@Param("entity") MerchConsignorMoneyEntity consignorMoneyEntity);

    void update(@Param("entity") MerchConsignorMoneyEntity consignorMoneyEntity);

    MerchConsignorMoneyEntity selectByMerchId(@Param("merchId") String merchId);
}
