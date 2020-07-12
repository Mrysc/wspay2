package com.rltx.wspay.bindbankcard.dao;

import com.rltx.wspay.bindbankcard.entity.OtherRegisterBindPendRecEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by  zl on 2020/6/10 14:14.
 * Description:
 * 他人信息开户绑卡记录
 *
 */
@Mapper
public interface OtherRegisterBindPendRecDao {

    void insert(OtherRegisterBindPendRecEntity entity);

    int insertSelect(OtherRegisterBindPendRecEntity entity);

    List<OtherRegisterBindPendRecEntity> selectList(@Param("params") Map<String, String> params);

    OtherRegisterBindPendRecEntity select(@Param("params") Map<String, String> paramMap);
}
