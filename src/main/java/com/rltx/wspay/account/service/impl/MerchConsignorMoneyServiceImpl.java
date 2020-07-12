package com.rltx.wspay.account.service.impl;

import com.rltx.wspay.account.dao.*;
import com.rltx.wspay.account.entity.*;
import com.rltx.wspay.account.service.IMerchConsignorMoneyService;
import com.rltx.wspay.account.service.IMerchService;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.constant.*;
import com.rltx.wspay.utils.constant.MerchRegisterConstant;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.TreeMap;
import java.util.UUID;

@Service
public class MerchConsignorMoneyServiceImpl implements IMerchConsignorMoneyService {


    @Autowired
    private MerchConsignorMoneyDao consignorMoneyDao;

    @Override
    public synchronized void update(String merchId,String totalAmount) throws Exception {
        MerchConsignorMoneyEntity consignorMoneyEntity = consignorMoneyDao.selectByMerchId(merchId);
        String money = consignorMoneyEntity.getMoney().toString();
        String totalAmountYuan = AmountUtils.changeF2Y(totalAmount);
        Double sumMoney = Double.valueOf(AmountUtils.sumMoney(money,totalAmountYuan));
        consignorMoneyEntity.setMoney(sumMoney);
        try {
            consignorMoneyEntity.preUpdate();
        }catch (Exception e){
            consignorMoneyEntity.setUpdateBy("网商异步通知");
            consignorMoneyEntity.setUpdateTime(new Date());
        }
        consignorMoneyDao.update(consignorMoneyEntity);
    }
}
