package com.rltx.wspay.pay.controller;


import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import com.rltx.wspay.pay.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
@RequestMapping("pay")
public class PayController{

    @Autowired
    private IPayService payService;

    //余额支付创建接口
//    @PostMapping("balancePay")
//    public TreeMap<String, Object> balancePay(String payerCode, String payeeCode, String totalAmount,String paybillNo) throws Exception {
//        return payService.balancePay(payerCode,payeeCode,totalAmount,paybillNo);
//    }

    //开通商户余额支付权限接口
    @PostMapping("openPay")
    public TreeMap<String,String> openPay(String code) throws Exception {
        return payService.openPay(code);
    }

    //余额支付确认接口
    @PostMapping("payConfirm")
    public PaymertRecordEntity payConfirm(String code) throws Exception {
        return payService.payConfirm(code);
    }

    //余额支付查询接口
    @PostMapping("payQuery")
    public TreeMap<String, String> payQuery(String code) throws Exception {
        return payService.payQuery(code);
    }





}
