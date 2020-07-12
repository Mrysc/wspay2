package com.rltx.wspay.pay.controller;

import com.rltx.wspay.pay.service.IElectronicReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
@RequestMapping("electronic/receipt")
public class ElectronicReceiptController {

    @Autowired
    private IElectronicReceiptService electronicReceiptService;

    //回单申请接口
    @PostMapping("apply")
    public TreeMap<String, Object> apply(String paymertCode) throws Exception {
        return electronicReceiptService.apply(paymertCode);
    }


    //回单查询接口
    @PostMapping("payQuery")
    public TreeMap<String, Object> payQuery(String paymertCode) throws Exception {
        return electronicReceiptService.query(paymertCode);
    }

}
