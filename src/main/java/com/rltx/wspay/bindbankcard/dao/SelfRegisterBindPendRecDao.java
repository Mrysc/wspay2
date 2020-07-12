package com.rltx.wspay.bindbankcard.dao;

import com.rltx.wspay.bindbankcard.entity.SelfRegisterBindPendRecEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by  zl on 2020/6/10 14:15.
 * Description:
 * 本人开户绑卡记录
 */
@Mapper
public interface SelfRegisterBindPendRecDao {

    void insert(SelfRegisterBindPendRecEntity entity);

    List<SelfRegisterBindPendRecEntity> selectList(SelfRegisterBindPendRecEntity entity);


    void updateMerchantInfo(SelfRegisterBindPendRecEntity recEntity);
}
