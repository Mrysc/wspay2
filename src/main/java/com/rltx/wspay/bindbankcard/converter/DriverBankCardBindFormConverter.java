package com.rltx.wspay.bindbankcard.converter;

import com.rltx.wspay.bindbankcard.entity.*;
import com.rltx.wspay.bindbankcard.form.DriverBankCardBindForm;

/**
 * Created by  zl on 2020/6/25 10:21.
 * Description:
 */
public class DriverBankCardBindFormConverter {


    public static DriverBankCardRelationEntity converterToDriverBankCardRelationEntity(DriverBankCardBindForm driverBankCardBindForm,
                                                                                       String selfOrOtherFlag, String bankCardCode, String bindStatus){
        DriverBankCardRelationEntity entity = new DriverBankCardRelationEntity();
        entity.setDriverUserCode(driverBankCardBindForm.getDriverCode());
        entity.setDriverName(driverBankCardBindForm.getDriverName());
        entity.setBankAccountName(driverBankCardBindForm.getBankAccountName());
        entity.setBankCardNo(driverBankCardBindForm.getBankCardNo());
        entity.setBankAccountIdNo(driverBankCardBindForm.getBankAccIdNumber());
        entity.setBankAccountPhone(driverBankCardBindForm.getPhone());
        entity.setSelfOrOther(selfOrOtherFlag);
        entity.setBankCardCode(bankCardCode);
        entity.setBindStatus(bindStatus);
        return entity;

    }

    public static SelfRegisterBindPendRecEntity converterToSelfRegisterBindPendRecEntity(DriverBankCardBindForm driverBankCardBindForm, String relationCode){
        SelfRegisterBindPendRecEntity entity = new SelfRegisterBindPendRecEntity();
        entity.setDriverBankCardRelationCode(relationCode);
        entity.setDriverCode(driverBankCardBindForm.getDriverCode());
        entity.setDriverBankAccountName(driverBankCardBindForm.getBankAccountName());
        entity.setDriverBankCardNo(driverBankCardBindForm.getBankCardNo());
        entity.setDriverIdNo(driverBankCardBindForm.getDriverIdNumber());
        entity.setPhone(driverBankCardBindForm.getPhone());

        return entity;
    }

    public static OtherMemberPayeeInfoEntity converterToOtherMemberPayeeInfoEntity(DriverBankCardBindForm driverBankCardBindForm) {
        OtherMemberPayeeInfoEntity entity = new OtherMemberPayeeInfoEntity();
        entity.setIdNumber(driverBankCardBindForm.getBankAccIdNumber());
        entity.setFullName(driverBankCardBindForm.getBankAccountName());
        return entity;
    }

    public static DriverBankCardEntity convertToDriverBankCardEntity(DriverBankCardBindForm driverBankCardBindForm) {
        DriverBankCardEntity driverBankCardEntity = new DriverBankCardEntity();
        driverBankCardEntity.setBankAccountName(driverBankCardBindForm.getBankAccountName());
        driverBankCardEntity.setBankCardNo(driverBankCardBindForm.getBankCardNo());
        driverBankCardEntity.setIdNumber(driverBankCardBindForm.getBankAccIdNumber());
        driverBankCardEntity.setPhone(driverBankCardBindForm.getPhone());
        return driverBankCardEntity;
    }

    public static OtherRegisterBindPendRecEntity convertToOtherRegisterBindPendRec(DriverBankCardBindForm driverBankCardBindForm, String payeeUserCode) {
        OtherRegisterBindPendRecEntity entity = new OtherRegisterBindPendRecEntity();

        entity.setDriverCode(driverBankCardBindForm.getDriverCode());
        entity.setPayeeUserCode(payeeUserCode);
        entity.setPayeeBankAccountName(driverBankCardBindForm.getBankAccountName());
        entity.setPayeeBankCardNo(driverBankCardBindForm.getBankCardNo());
        entity.setPayeeIdNo(driverBankCardBindForm.getBankAccIdNumber());
        entity.setPayeePhone(driverBankCardBindForm.getPhone());

        return entity;
    }

    public static OtherRegisterBindPendRecQueueEntity convertToOtherRegisterBindPendRecQueue(DriverBankCardBindForm driverBankCardBindForm ) {
        OtherRegisterBindPendRecQueueEntity entity = new OtherRegisterBindPendRecQueueEntity();

        entity.setDriverCode(driverBankCardBindForm.getDriverCode());
        entity.setPayeeBankAccountName(driverBankCardBindForm.getBankAccountName());
        entity.setPayeeBankCardNo(driverBankCardBindForm.getBankCardNo());
        entity.setPayeeIdNo(driverBankCardBindForm.getBankAccIdNumber());
        entity.setPayeePhone(driverBankCardBindForm.getPhone());

        return entity;
    }
}
