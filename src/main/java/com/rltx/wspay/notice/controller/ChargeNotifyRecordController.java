package com.rltx.wspay.notice.controller;

import com.rltx.common.base.BaseContext;
import com.rltx.wspay.notice.service.IChargeNotifyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 打款结果通知
 */
@RestController
@RequestMapping("vostro")
public class ChargeNotifyRecordController extends BaseContext {

    @Autowired
    private IChargeNotifyRecordService chargeNotifyRecordService;

    @ResponseBody
    @PostMapping(value="/notify",produces = {"application/xml;charset=UTF-8"})
    public  String notify(@RequestBody String data) throws Exception {
        System.out.println("+++++++++++++++++++++++++++打款结果通知+++++++++++++++++++++++++");
        String response = chargeNotifyRecordService.notify(data);
        return  response;
    }

}
