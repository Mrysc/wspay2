package com.rltx.wspay.bindbankcard.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:21.
 * Description:
 * 绑定他人卡时，他人入驻开户绑卡待处理记录
 */
@Data
public class OtherRegisterBindPendRecEntity extends OtherRegisterBindPendRecBaseEntity implements Serializable {

    private String payeeRegisterStatus; //收款人入驻状态  成功S 失败F 处理中U

    private String payeeBindCardStatus; //收款人商户绑卡状态 已绑Y 未绑定N

    private Integer payeeRegisterNum; //申请入驻调用接口次数

    private Integer payeeBindNum; //绑卡调用次数。理论上绑失败了就不再处理了


}
