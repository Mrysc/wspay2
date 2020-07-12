package com.rltx.wspay.pay.controller;

import com.rltx.wspay.pay.service.IRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("refund")
public class RefundController {

    @Autowired
    private IRefundService refundService;

    //支付退回申请
    @PostMapping("refundApply")
    public String refundApply() throws Exception {
        return refundService.refundApply();
    }

    //支付退回查询接口
    @PostMapping("refundQuery")
    public String refundQuery() throws Exception {
        return refundService.refundQuery();
    }

}
