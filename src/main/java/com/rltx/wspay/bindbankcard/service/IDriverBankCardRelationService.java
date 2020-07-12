package com.rltx.wspay.bindbankcard.service;

import com.rltx.wspay.bindbankcard.entity.DriverBankCardRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by  zl on 2020/6/26 11:20.
 * Description:
 */
public interface IDriverBankCardRelationService {

    void updateMerchId(DriverBankCardRelationEntity relationEntity);

    void updateMerchIdForKafka(String relationCode, String merchantId);

    List<DriverBankCardRelationEntity> selectList(Map<String, String> paramMap);

    String selectMerchantIdByOtherBankCardNo(String bankCardNo);
    DriverBankCardRelationEntity selectMerchantByOtherBankCardNo(String bankCardNo);

    void saveDriverBankCardRelation(DriverBankCardRelationEntity entity);

    List<DriverBankCardRelationEntity> selectByParams(Map<String, String> params);

    void updateOtherBindStatusByFourEles(String bankAccountName, String bankAccIdNumber, String bankCardNo, String phone, String bindStatus, String bindFailMsg);

    List<DriverBankCardRelationEntity> selectOtherRelationByFourEles(Map<String, String> fourElements);
}
