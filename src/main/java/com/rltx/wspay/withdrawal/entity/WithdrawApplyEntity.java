package com.rltx.wspay.withdrawal.entity;

import com.rltx.wspay.commom.HttpMain;
import com.rltx.wspay.utils.constant.ParamUtil;
import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawApplyEntity extends BaseEntity {
    @Builder.Default
    private String isvOrgId = ParamUtil.getParamInfoByKey("IsvOrgId");

    private String merchantId;

    private String outTradeNo;

    private String totalAmount;

    @Builder.Default
    private String currency = ParamUtil.getParamInfoByKey("Currency");

    private String platformFee;

    @Builder.Default
    private String feeCurrency = ParamUtil.getParamInfoByKey("Currency");

    private String memo;

    private String flag;

    private String orderNo;

    private String smsCode;

    private String bankCardNo;
    private String bankCertName;
    private String withdrawApplyDate;
    private String withdrawFinishDate;
    private String errorDesc;

}
