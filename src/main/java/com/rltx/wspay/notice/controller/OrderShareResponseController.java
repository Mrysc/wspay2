package com.rltx.wspay.notice.controller;

import com.rltx.common.base.BaseContext;
import com.rltx.wspay.notice.service.IMerchResponseService;
import com.rltx.wspay.notice.service.IOrderShareResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分账结果通知
 */
@RestController
@RequestMapping("order/share")
public class OrderShareResponseController extends BaseContext {

    @Autowired
    private IOrderShareResponseService iOrderShareResponseService;

    @ResponseBody
    @PostMapping(value="/notify",produces = {"application/xml;charset=UTF-8"})
    public  String notify(@RequestBody String data) throws Exception {
        System.out.println("+++++++++++++++++++++++++++分账结果通知+++++++++++++++++++++++++");
        String response = iOrderShareResponseService.notify(data);
        return  response;
    }

}
