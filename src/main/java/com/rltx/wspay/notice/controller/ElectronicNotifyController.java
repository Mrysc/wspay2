package com.rltx.wspay.notice.controller;

import com.rltx.common.base.BaseContext;
import com.rltx.wspay.notice.service.IElectronicreceiptNotifyService;
import com.rltx.wspay.notice.service.IWithdrawNotifyService;
import com.rltx.wspay.pay.service.IElectronicReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 回单通知
 */
@RestController
@RequestMapping("electronic")
public class ElectronicNotifyController extends BaseContext {

    @Autowired
    private IElectronicreceiptNotifyService electronicreceiptNotifyService;

    @ResponseBody
    @PostMapping(value="/notify",produces = {"application/xml;charset=UTF-8"})
    public  String notify(@RequestBody String data) throws Exception {
        System.out.println("+++++++++++++++++++++++++++回单通知+++++++++++++++++++++++++");
        String response = electronicreceiptNotifyService.notify(data);
        return  response;
    }

}
