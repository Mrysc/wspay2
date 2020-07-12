package com.rltx.wspay.notice.controller;

import com.rltx.common.base.BaseContext;
import com.rltx.wspay.notice.service.IMerchResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.TreeMap;

/**
 * 商户入驻结果通知
 */
@RestController
@RequestMapping("merch")
public class MerchResponseController extends BaseContext {

    @Autowired
    private IMerchResponseService iMerchResponseService;

    @ResponseBody
    @PostMapping(value="/notify",produces = {"application/xml;charset=UTF-8"})
    public  String registerResult(@RequestBody String data) throws Exception {
        System.out.println("+++++++++++++++++++++++++++商户入驻结果通知+++++++++++++++++++++++++");
        String response = iMerchResponseService.registerResult(data);
        return  response;
    }

}
