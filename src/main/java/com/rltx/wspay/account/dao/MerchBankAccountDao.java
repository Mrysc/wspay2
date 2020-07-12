package com.rltx.wspay.account.dao;

import com.rltx.wspay.account.entity.MerchBankAccountEntity;
import com.rltx.wspay.account.entity.MerchRegisterRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("merchBankAccountDao")
public interface MerchBankAccountDao {

    void insert(@Param("entity") MerchBankAccountEntity merchBankAccountEntity);

    void update(@Param("entity") MerchBankAccountEntity merchBankAccountEntity);

    MerchBankAccountEntity selectByTrade(@Param("outTradeId") String outTradeId);

}
