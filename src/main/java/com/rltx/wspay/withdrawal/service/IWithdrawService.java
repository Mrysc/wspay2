package com.rltx.wspay.withdrawal.service;


import com.rltx.common.vo.CommParamsVo;
import com.rltx.wspay.withdrawal.entity.WithdrawApplyEntity;

import java.util.TreeMap;

public interface IWithdrawService {
    TreeMap<String,Object> applyConfirm(String outTradeNo) throws Exception;
    TreeMap<String,Object> withdrawApply(String userCode, String money) throws Exception;
    TreeMap<String,Object> withdrawQuery(String outTradeNo) throws Exception;
}
