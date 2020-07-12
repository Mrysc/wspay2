package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.account.entity.MemberPersonInfoEntity;
import com.rltx.wspay.bindbankcard.entity.OtherMemberPayeeInfoEntity;

/**
 * Created by  zl on 2020/6/28 16:45.
 * Description:
 */
public interface IOtherMemberPayeeInfoService {

    OtherMemberPayeeInfoEntity selectByIdNumber(String idNumber);

    void saveOtherMemberPayeeInfo(OtherMemberPayeeInfoEntity entity);

    int insertSelect(OtherMemberPayeeInfoEntity entity);

    MemberPersonInfoEntity selectDriverInfoByIdNo(String idNo);

}
