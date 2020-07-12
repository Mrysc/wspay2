package com.rltx.wspay.bindbankcard.entity;

import com.rltx.wspay.utils.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by  zl on 2020/6/10 14:08.
 * Description:
 */
@Data
public class OtherMemberPayeeInfoEntity extends BaseEntity implements Serializable {

    private String fullName; //收款人姓名

    private String idNumber; //身份证号

    private String identityResourceCode; //身份证附件

}
