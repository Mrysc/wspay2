package com.rltx.wspay.utils.entity;

import lombok.Data;

/**
 * Created by  lenovo on 2019/10/10 18:52.
 * Description: No Description
 */
@Data
public class BankCardDetail {

    private String stat;//状态

    private boolean validated;//验证是否通过

    private String key;//银行卡号

    private String cardType;//借记卡类型

    private String bank;//所属银行简写 ICBC   CMB

    private String bankName; //银行名称

    private String messages;//返回消息


}
