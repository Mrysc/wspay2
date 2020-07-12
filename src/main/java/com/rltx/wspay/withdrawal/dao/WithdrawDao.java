package com.rltx.wspay.withdrawal.dao;


import com.rltx.wspay.bindbankcard.entity.OtherMemberPayeeInfoEntity;
import com.rltx.wspay.withdrawal.entity.WithdrawApplyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository("withdrawDao")
public interface WithdrawDao {
    void insert(@Param("withdrawApplyEntity")WithdrawApplyEntity withdrawApplyEntity) throws Exception;

    WithdrawApplyEntity select(@Param("outTradeNo") String outTradeNo);

    void updateFlag(@Param("entity")WithdrawApplyEntity withdrawApplyEntity) throws Exception;


    void updateNotify(@Param("entity")WithdrawApplyEntity withdrawApplyEntity) throws Exception;

}
