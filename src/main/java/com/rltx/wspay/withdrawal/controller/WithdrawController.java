package com.rltx.wspay.withdrawal.controller;

import com.rltx.common.base.BaseContext;
import com.rltx.wspay.withdrawal.entity.ApplyConfirmEntity;
import com.rltx.wspay.withdrawal.entity.WithdrawApplyEntity;
import com.rltx.wspay.withdrawal.service.IWithdrawService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
@RequestMapping("withdraw")
public class WithdrawController{


    @Autowired
    private IWithdrawService withdrawService;


    /**
     * 商户单笔提现申请接口
     * @param userCode  money  商户单笔提现申请   实体类
     * @return
     * @throws Exception
     */
    @PostMapping("withdrawApply")
    public TreeMap<String,Object> withdrawApply(String userCode, String money) throws Exception {
        return withdrawService.withdrawApply(userCode,money);
    }

    //商户单笔提现确认接口
    @PostMapping("applyConfirm")
    public TreeMap<String,Object> applyConfirm(String outTradeNo) throws Exception {
        return withdrawService.applyConfirm(outTradeNo);
    }

    //单笔提现查询接口
    @PostMapping("withdrawQuery")
    public TreeMap<String,Object> withdrawQuery(String outTradeNo) throws Exception {
        return withdrawService.withdrawQuery(outTradeNo);
    }

}
