package com.rltx.wspay.pay.dao;


import com.rltx.wspay.pay.entity.WaybillPaybillBaseInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("waybillPaybillBaseInfoDao")
public interface WaybillPaybillBaseInfoDao {

  WaybillPaybillBaseInfoEntity getByPaybillNo(@Param("paybillNo") String paramString);

}
