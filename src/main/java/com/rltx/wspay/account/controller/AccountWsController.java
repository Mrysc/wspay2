package com.rltx.wspay.account.controller;


import com.rltx.common.base.BaseContext;
import com.rltx.wspay.account.service.IAccountWsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("account")
public class AccountWsController extends BaseContext {

    @Autowired
    private IAccountWsService accountWsService;

    // 商户通用开户接口 开启保证金子户 合并子户子户专用
    @PostMapping("open")
    public TreeMap<String,Object> accountOpen(String code) throws Exception {
        TreeMap<String,Object> response = accountWsService.accountOpen(code);
        return response;
    }

    //商户子户查询
    @PostMapping("query")
    public Map<String,String> accountQuery(String code) throws Exception {
        Map<String,String> response = accountWsService.accountQuery(code);
        return response;
    }

}
