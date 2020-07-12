package com.rltx.wspay.account.entity;

import lombok.Data;

/**
 * 保证金查询
 */
@Data
public class BatchQueryEntity {

    private String isvOrgId;
    private String merchantId;
    //来账单号
    private String orderNo;

    //开始及结束时间 格式为YYYYMMDD 不带-
    private String startTime;
    private String endTime;
    private String pageIndex;
    private String pageSize;
    public BatchQueryEntity(){}

    /**
     * 注意：
     * 来账单号 和 开始结束 时间是 互斥关系 两种业务场景
     * 两者只选其一
     * */
    public BatchQueryEntity(
            String isvOrgId,
            String merchantId,
            String orderNo,
            String startTime,
            String endTime
    ){
        this.isvOrgId=isvOrgId;
        this.merchantId=merchantId;
        this.orderNo=orderNo;
        this.startTime=startTime;
        this.endTime=endTime;

    }

}
