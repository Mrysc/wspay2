package com.rltx.wspay.account.service;


import com.rltx.wspay.account.entity.BatchQueryResponseEntity;

import java.util.List;
import java.util.Map;

public interface IQueryService {
    Map<String,Object> balanceQuery(String code) throws Exception;
    BatchQueryResponseEntity batchQuery(String code, String startTime, String endTime, String pageIndex, String pageSize) throws Exception;
    String sceneBalanceQuery() throws Exception;
}
