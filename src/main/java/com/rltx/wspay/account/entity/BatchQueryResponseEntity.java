package com.rltx.wspay.account.entity;

import lombok.Data;

import java.util.List;

/**
 * 保证金查询
 */
@Data
public class BatchQueryResponseEntity {
    //合作方机构号（网商银行分配）
    private String isvOrgId;
    //查询商户号
    private String merchantId;
    //充值保证金子户余额
    private String actualAmount;
    //来账信息列表
    private List<VostroInfoEntity> vostroInfoList;
    //有没有下一页true | false
    private String hasNextPage;
    //当前页面
    private String pageIndex;
    //当前页面数
    private String pageSize;

    private String respInfo;
}
