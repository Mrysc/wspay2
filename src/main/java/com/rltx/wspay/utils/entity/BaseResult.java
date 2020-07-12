package com.rltx.wspay.utils.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/22 17:42.
 * Description:
 */
@Data
public class BaseResult implements Serializable {
    private Boolean isSuccess;

    private String errMsg;
}
