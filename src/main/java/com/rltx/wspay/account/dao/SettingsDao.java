package com.rltx.wspay.account.dao;

import com.rltx.wspay.account.entity.MerchRegisterRecordEntity;
import com.rltx.wspay.account.entity.SettingsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("settingsDao")
public interface SettingsDao {
    List<SettingsEntity> select();

}
