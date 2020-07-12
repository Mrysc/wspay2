package com.rltx.wspay.bindbankcard.dao;

import com.rltx.wspay.bindbankcard.entity.DriverBankCardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by  zl on 2020/6/10 14:10.
 * Description:
 * 银行卡
 */
@Mapper
public interface DriverBankCardDao {

    void insert(DriverBankCardEntity entity);

    int countByBankCardNo(@Param("bankCardNo") String bankCardNo);

    DriverBankCardEntity select(DriverBankCardEntity entity);



}
