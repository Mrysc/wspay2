package com.rltx.wspay.bindbankcard.result;

import com.rltx.wspay.utils.entity.BaseResult;
import lombok.Data;

/**
 * Created by  zl on 2020/6/21 15:06.
 * Description:
 */
@Data
public class BankCardBindResult extends BaseResult {

    private Boolean bizFlag = false; //业务标志，针对 绑定本人卡进行替换的情况. default false

    private String bindStatus;

}
