package com.rltx.wspay.account.controller;


import com.rltx.common.base.BaseContext;

import com.rltx.wspay.account.entity.MerchEntity;
import com.rltx.wspay.account.result.MerchRegisterResult;
import com.rltx.wspay.account.service.IMerchService;
import com.rltx.wspay.utils.DownloadPicture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@RestController
@RequestMapping("merch")
public class MerchController extends BaseContext {

    @Autowired
    private IMerchService merchService;

    /**
     * 商户入驻接口
     * 返回网商返回报文
     * @param register
     * @return
     * @throws Exception
     */
    @PostMapping("register")
    public String register(MerchEntity register) throws Exception {
        String map = merchService.register(register);
        return map;
    }

    //个人入驻
    @PostMapping(value = "register/person")
    public MerchRegisterResult personRegister(String code) throws Exception {
        MerchRegisterResult response = merchService.personRegister(code);
        return response;
    }

    //企业入驻
    @PostMapping(value = "register/enterprise")
    public MerchRegisterResult enterpriseRegister(String code) throws Exception {
        MerchRegisterResult response = merchService.enterpriseRegister(code);
        return response;
    }
    //入驻查询
    @PostMapping("register/query")
    public TreeMap<String,Object> registerQuery(String outTradeNo) throws Exception {
        TreeMap<String,Object> response = merchService.registerQuery(outTradeNo);
        response.remove("RespInfo");
        return response;
    }

    //商户信息查询接口
    @PostMapping("merch/query")
    public TreeMap<String,Object> merchQuery(String code) throws Exception {
        TreeMap<String,Object> response = merchService.merchQuery(code);
        response.remove("RespInfo");
        return response;
    }

}
