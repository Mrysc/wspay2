package com.rltx.wspay.bindbankcard.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/9 16:21.
 * Description:
 * 绑定他人卡时，他人入驻开户绑卡待处理队列 记录
 */
@Data
public class OtherRegisterBindPendRecQueueEntity extends OtherRegisterBindPendRecBaseEntity implements Serializable {


    private String queueStatus; //状态   如果 该银行卡的他人入驻绑卡成功了，那么该表中该卡的所有记录状态置为失败，不再处理
                                  //       如果 失败了，那么该表中记录移到他人入驻绑卡记录表，该记录在此表中删除，deleted =1
                                  //                    他人入驻绑卡记录表中失败的记录移到 他人绑卡记录失败表中。











}
