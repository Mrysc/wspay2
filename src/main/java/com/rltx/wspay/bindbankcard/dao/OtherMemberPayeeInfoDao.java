package com.rltx.wspay.bindbankcard.dao;

import com.rltx.wspay.bindbankcard.entity.OtherMemberPayeeInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by  zl on 2020/6/10 14:13.
 * Description:
 * 维护的收款人信息
 */
@Mapper
public interface OtherMemberPayeeInfoDao {

    void insert(OtherMemberPayeeInfoEntity entity);

    int  insertSelect(OtherMemberPayeeInfoEntity entity);

    OtherMemberPayeeInfoEntity select(@Param("params") Map<String, String> params);


}
