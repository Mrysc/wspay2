package com.rltx.wspay.pay.dao;


import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("paymentRecordDao")
public interface PaymentRecordDao {

    void insert(@Param("entity") PaymertRecordEntity paymertHistoryEntity);

    void update(@Param("entity") PaymertRecordEntity paymertHistoryEntity);

    void updateConfirm(@Param("entity") PaymertRecordEntity paymertHistoryEntity);

    PaymertRecordEntity selectByCode(@Param("code") String code);

    PaymertRecordEntity selectByOutTradeNo(@Param("outTradeNo") String outTradeNo);

}
