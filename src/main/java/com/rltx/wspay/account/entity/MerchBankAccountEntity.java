package com.rltx.wspay.account.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

/**
 * 商户入驻记录表
 */
@Data
public class MerchBankAccountEntity extends BaseEntity {
    //商户号
    private String merchId;
    //外部流水号（平台生成）
    private String outTradeId;
    //商户平台编码
    private String merchUserCode;
    //商户名称
    private String merchName;
    //平台商户类型（01:司机 02:货主 03:平台）
    private String type;
    //开户场景(目前支持 BASIC， PREPAY， SETTLING，TRADE_DEPOSIT 四种场景的子户)
    private String acctType;
    //传参
    private String requestData;
    //返回传参
    private String responseData;
    //处理状态(S：成功，F：失败，U：未知)
    private String resultStatus;
    //开户行行号
    private String branchNo;
    //开户行名称
    private String branchName;
    //网商卡号
    private String bankCardNo;
    //证件号
    private String bankCertName;


}
