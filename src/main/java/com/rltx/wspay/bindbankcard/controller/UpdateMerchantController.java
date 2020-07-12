package com.rltx.wspay.bindbankcard.controller;

import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.BankCardParam;
import com.rltx.wspay.account.entity.MerchEntity;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.account.entity.MerchUpdateEntity;
import com.rltx.wspay.bindbankcard.service.IUpdateMeerchService;
import com.rltx.wspay.constant.AccountType;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bankcard")
public class UpdateMerchantController {

    @Autowired
    private IUpdateMeerchService updateMeerchService;
    /**
     * 商户入驻修改接口用于绑卡
     * @param
     * @return
     * @throws Exception
     */
    @PostMapping("update/merch")
    public String updateMerchant(MerchEntity register) throws Exception {
        String response = updateMeerchService.updateMerchant(register);
        System.out.println(response);
        return response;
    }

}
