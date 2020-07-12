package com.rltx.wspay.pay.dao;


import com.rltx.wspay.pay.entity.OrderShareEntity;
import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("orderShareRecordDao")
public interface OrderShareRecordDao {

    void insert(@Param("entity") OrderShareEntity orderShareEntity);

    void update(@Param("entity") OrderShareEntity orderShareEntity);

    void updateNotify(@Param("entity") OrderShareEntity orderShareEntity);

//    void updateConfirm(@Param("entity") OrderShareEntity orderShareEntity);

    OrderShareEntity selectByOutTradeNo(@Param("outTradeNo") String outTradeNo);

    OrderShareEntity selectByRelateOrderNo(@Param("relateOrderNo") String waybillNo);

}
