package com.rltx.wspay.bindbankcard.dao;

import com.rltx.wspay.bindbankcard.entity.DriverBankCardRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by  zl on 2020/6/10 14:13.
 * Description:
 * 司机银行卡绑定关系表
 */
@Mapper
public interface DriverBankCardRelationDao {

    void insert(DriverBankCardRelationEntity entity);

    void delByRelationCode(@Param("code") String relationCode);

    //1. 根据司机userCode查询司机所有绑定卡 2.根据bankCardNo查询该卡是否已被绑过
    List<DriverBankCardRelationEntity> selectByParams(@Param("params") Map<String, String> map);

    List<DriverBankCardRelationEntity> selectRelationsByFourEles(@Param("params") Map<String, String> map);

    void updateMerchId(DriverBankCardRelationEntity relationEntity);

    void updateOtherBindStatusByFourEles(@Param("params") Map<String, String> params);
}
