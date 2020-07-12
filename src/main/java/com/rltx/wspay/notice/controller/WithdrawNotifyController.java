package com.rltx.wspay.notice.controller;

import com.rltx.common.base.BaseContext;
import com.rltx.wspay.notice.service.IChargeNotifyRecordService;
import com.rltx.wspay.notice.service.IWithdrawNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 打款结果通知
 */
@RestController
@RequestMapping("withdrawNotify")
public class WithdrawNotifyController extends BaseContext {

    @Autowired
    private IWithdrawNotifyService withdrawNotifyService;

    @ResponseBody
    @PostMapping(value="/notify",produces = {"application/xml;charset=UTF-8"})
    public  String notify(@RequestBody String data) throws Exception {
        String response = withdrawNotifyService.notify(data);
        return  response;
    }

}
