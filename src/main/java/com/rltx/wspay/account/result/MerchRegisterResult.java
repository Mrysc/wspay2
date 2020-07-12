package com.rltx.wspay.account.result;

import com.rltx.wspay.utils.entity.BaseResult;
import lombok.Data;

@Data
public class MerchRegisterResult  extends BaseResult {

    private String outTradeNo;
    private String outMerchantId;
    private String orderNo;


}
