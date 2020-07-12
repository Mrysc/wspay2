package com.rltx.wspay.account.controller;

import com.rltx.wspay.account.entity.BatchQueryResponseEntity;
import com.rltx.wspay.account.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("query")
public class QueryController {

    @Autowired
    private IQueryService queryService;

    //余额查询
    @PostMapping("balance")
    public Map<String,Object> balanceQuery(String code) throws Exception {

        Map<String,Object> response = queryService.balanceQuery(code);

        return response;
    }

    //保证金查询
    @PostMapping("batch")
    public BatchQueryResponseEntity batchQuery(String code, String startTime, String endTime, String pageIndex, String pageSize) throws Exception {

        BatchQueryResponseEntity response = queryService.batchQuery(code,startTime,endTime,pageIndex,pageSize);

        return response;
    }

    //账户场景查询
    @PostMapping("sceneBalance")
    public String sceneBalanceQuery() throws Exception {

        String response = queryService.sceneBalanceQuery();

        return response;
    }

}
