package com.rltx.wspay.account.controller;



import com.rltx.wspay.account.dao.SettingsDao;
import com.rltx.wspay.account.entity.SettingsEntity;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.geo.Distance;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class WsSettings implements CommandLineRunner {

    @Autowired
    SettingsDao settingsDao;

    @Override
    public void run(String... strings){
        List<SettingsEntity> list = settingsDao.select();
        for (SettingsEntity setting:list) {
            ParamUtil.param.put(setting.getParamKey(),setting.getParamVal());
        }
    }
}
