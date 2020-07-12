package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.bindbankcard.dao.DriverBankCardRelationDao;
import com.rltx.wspay.bindbankcard.entity.DriverBankCardRelationEntity;
import com.rltx.wspay.bindbankcard.service.IDriverBankCardRelationService;
import com.rltx.wspay.utils.constant.BindBankCardConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by  zl on 2020/6/26 11:21.
 * Description:
 */
@Service
public class DriverBankCardRelationServiceImpl implements IDriverBankCardRelationService {
    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;
    @Autowired
    private DriverBankCardRelationDao driverBankCardRelationDao;

    @Override
    public void updateMerchId(DriverBankCardRelationEntity relationEntity) {
        relationEntity.preUpdate();
        driverBankCardRelationDao.updateMerchId(relationEntity);
    }

    @Override
    public void updateMerchIdForKafka(String relationCode,String merchantId) {
        DriverBankCardRelationEntity relationEntity = new DriverBankCardRelationEntity();
        relationEntity.setCode(relationCode);
        relationEntity.setMerchantId(merchantId);
        relationEntity.preUpdateKafka();
        driverBankCardRelationDao.updateMerchId(relationEntity);
    }

    @Override
    public List<DriverBankCardRelationEntity> selectList(Map<String,String> paramMap) {
        List<DriverBankCardRelationEntity> list = driverBankCardRelationDao.selectByParams(paramMap);
        return list;
    }

    @Override
    public String selectMerchantIdByOtherBankCardNo(String bankCardNo) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("bankCardNo",bankCardNo);

        List<DriverBankCardRelationEntity> list = driverBankCardRelationDao.selectByParams(paramMap);
        //过滤非本人的
        List<DriverBankCardRelationEntity> filterList = list.stream().filter(entity -> BindBankCardConstant.Bank_Card_Owner.other.equals(entity.getSelfOrOther())
                && StringUtils.isNotBlank(entity.getMerchantId())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(filterList)){
            return filterList.get(0).getMerchantId();
        }
        return "";
    }

    @Override
    public DriverBankCardRelationEntity selectMerchantByOtherBankCardNo(String bankCardNo) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("bankCardNo",bankCardNo);

        List<DriverBankCardRelationEntity> list = driverBankCardRelationDao.selectByParams(paramMap);
        //过滤非本人的
        List<DriverBankCardRelationEntity> filterList = list.stream().filter(entity -> BindBankCardConstant.Bank_Card_Owner.other.equals(entity.getSelfOrOther())
                && StringUtils.isNotBlank(entity.getMerchantId())).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(filterList)){
            return filterList.get(0);
        }
        return null;
    }

    @Override
    public void saveDriverBankCardRelation(DriverBankCardRelationEntity entity) {
        entity.preInsert();
        String code =  generationCodingService.generateCurrencyCode();
        entity.setCode(code);

        driverBankCardRelationDao.insert(entity);
    }

    @Override
    public List<DriverBankCardRelationEntity> selectByParams(Map<String, String> params) {
        return driverBankCardRelationDao.selectByParams(params);
    }

    @Override
    public void updateOtherBindStatusByFourEles(String bankAccountName, String bankAccIdNumber, String bankCardNo, String phone,String bindStatus,String bindFailMsg) {
        Map<String,String> params = new HashMap<>();
        params.put("bankAccountName",bankAccountName);
        params.put("bankAccIdNumber",bankAccIdNumber);
        params.put("bankCardNo",bankCardNo);
        params.put("phone",phone);
        params.put("bindStatus",bindStatus);
        params.put("bindFailMsg",bindFailMsg);
        driverBankCardRelationDao.updateOtherBindStatusByFourEles(params);

    }

    @Override
    public List<DriverBankCardRelationEntity> selectOtherRelationByFourEles(Map<String, String> fourElements) {
        fourElements.put("selfOtherFlag",BindBankCardConstant.Bank_Card_Owner.other);
        List<DriverBankCardRelationEntity> list = driverBankCardRelationDao.selectByParams(fourElements);
        return list;
    }


}
