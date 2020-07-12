package com.rltx.wspay.pay.controller;

import com.rltx.wspay.pay.service.IKybService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kyb")
public class KybController {

    @Autowired
    private IKybService kybService;

    //打款申请接口
    @PostMapping("kybApply")
    public String kybApply() throws Exception {
        return kybService.kybApply();
    }

    //打款验证接口
    @PostMapping("kybMatch")
    public String kybMatch() throws Exception {
        return kybService.kybMatch();
    }

}
