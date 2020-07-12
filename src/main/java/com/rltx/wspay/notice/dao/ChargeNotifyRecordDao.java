package com.rltx.wspay.notice.dao;

import com.rltx.wspay.notice.entity.ChargeNotifyRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("chargeNotifyRecordDao")
public interface ChargeNotifyRecordDao {

    void insert(@Param("entity") ChargeNotifyRecordEntity chargeNotifyRecordEntity);

    List<ChargeNotifyRecordEntity> select(@Param("orderNo") String orderNo);

}
