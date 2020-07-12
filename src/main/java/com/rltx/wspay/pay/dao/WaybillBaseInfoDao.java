package com.rltx.wspay.pay.dao;

import com.rltx.wspay.pay.entity.WaybillBaseInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("waybillBaseInfoDao")
public  interface WaybillBaseInfoDao {

  WaybillBaseInfoEntity selectByNo(@Param("waybillNo") String paramString);
  }
