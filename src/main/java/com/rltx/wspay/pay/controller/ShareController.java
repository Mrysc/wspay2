package com.rltx.wspay.pay.controller;

import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import com.rltx.wspay.pay.service.IShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
@RequestMapping("share")
public class ShareController{

    @Autowired
    private IShareService shareService;

    //公司分账申请接口
    @PostMapping("orderShare")
    public TreeMap<String, Object> orderShare(PaymertRecordEntity paymertRecord) throws Exception {
        return shareService.orderShare(paymertRecord);
    }


    //分账申请接口
    @PostMapping("orderShareDriver")
    public TreeMap<String, Object> orderShareDriver(String paybillNo,String merchId) throws Exception {
        return shareService.orderShareDriver(paybillNo,merchId);
    }

    //分账退回查询接口
    @PostMapping("refundShare")
    public String refundShare() throws Exception {
        return shareService.refundShare();
    }

    //分账退回查询接口
    @PostMapping("refundShareQuery")
    public String refundShareQuery() throws Exception {
        return shareService.refundShareQuery();
    }

    //单笔分账查询接口
    @PostMapping("shareQuery")
    public TreeMap<String, Object> shareQuery(String outTradeNo) throws Exception {
        return shareService.shareQuery(outTradeNo);
    }

}
