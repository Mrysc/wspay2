package com.rltx.wspay.bindbankcard.dao;

import com.rltx.wspay.bindbankcard.entity.OtherRegisterPendRecRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by  zl on 2020/6/10 14:15.
 * Description:
 * 他人开户记录关联
 * 其他人入驻待处理记录表的关系表， 调用入驻申请之前做外部商户号、交易号申请的记录。
 */
@Mapper
public interface OtherRegisterPendRecRelationDao {

    void insert(OtherRegisterPendRecRelationEntity entity);

    //根据交易号 更新 入驻申请的商户号信息
    void update(OtherRegisterPendRecRelationEntity entity);

    OtherRegisterPendRecRelationEntity select(@Param("params") Map<String, String> params);


}
