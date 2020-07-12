package com.rltx.wspay.bindbankcard.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:33.
 * Description:
 *
 * 其他人入驻待处理记录表的关系表， 调用入驻申请之前做外部商户号、交易号申请的记录。
 */
@Data
public class OtherRegisterPendRecRelationEntity extends BaseEntity implements Serializable {

    private String pendingRecCode; //OtherRegisterBindPendingRecEntity的code

    private String payeeUserCode; //他人表中userCode;

    private String outerPayeeUserCode;//申请入驻的外部商户号

    private String tradeOrderNo; //交易号  用于入驻结果通知

    private String merchantId; //网商银行分配的商户号

    private String payeeRegisterStatus; //入驻状态 ，成功S 失败F 处理中U

}
