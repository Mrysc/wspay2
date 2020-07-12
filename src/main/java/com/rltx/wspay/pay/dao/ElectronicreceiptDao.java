package com.rltx.wspay.pay.dao;


import com.rltx.wspay.pay.entity.ElectronicReceiptEntity;
import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("electronicreceiptDao")
public interface ElectronicreceiptDao {

    void insert(@Param("entity") ElectronicReceiptEntity electronicReceiptEntity);

    void update(@Param("entity") ElectronicReceiptEntity electronicReceiptEntity);

    ElectronicReceiptEntity selectByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    ElectronicReceiptEntity selectByPaymentCode(@Param("paymentCode") String paymentCode);

}
