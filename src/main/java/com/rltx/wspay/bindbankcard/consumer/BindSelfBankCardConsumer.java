/**
 * wanhetong
 * 2019-11-07
 * Sun Q.J
 */

package com.rltx.wspay.bindbankcard.consumer;

import com.alibaba.fastjson.JSON;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.dao.MerchRegisterRecordDao;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.account.entity.MerchRegisterRecordEntity;
import com.rltx.wspay.bindbankcard.entity.SelfRegisterBindPendRecEntity;
import com.rltx.wspay.bindbankcard.service.IDriverBankCardRelationService;
import com.rltx.wspay.bindbankcard.service.ISelfRegisterBindPendRecService;
import com.rltx.wspay.utils.constant.BindBankCardConstant;
import com.rltx.wspay.utils.constant.MerchRegisterConstant;
import com.wl.framework.event.consumer.IEventConsumer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BindSelfBankCardConsumer implements IEventConsumer {

  @Autowired
  private MerchRegisterDao merchRegisterDao;
  @Autowired
  private MerchRegisterRecordDao merchRegisterRecordDao;
  @Autowired
  private IDriverBankCardRelationService driverBankCardRelationService;
  @Autowired
  private ISelfRegisterBindPendRecService selfRegisterBindPendRecService;

  /**
   * 司机绑定本人卡kafka消息
   * 1. 判断司机是否开户
   * if have register,then
   *    (1) update driver_bank_card_relation merchant_id（商户号）
   *    (2) update self_register_bind_pend_rec merchant_id,register_status
   *    (3) call bind card interface
   * else:
   *    (1) update self_register_bind_pend_rec register_status
   *    (2) if unregister : call register interface; else:do nothing.
   *
   * @param eventCode
   * @param message
   */
  public void consume(String eventCode, String message) {
    System.out.println("----------------------执行绑定网商银行本人银行卡consume------------");
    SelfRegisterBindPendRecEntity selfRegisterBindPendRecEntity = JSON.parseObject(message, SelfRegisterBindPendRecEntity.class);
    String relationCode = selfRegisterBindPendRecEntity.getDriverBankCardRelationCode();
    String driverCode = selfRegisterBindPendRecEntity.getDriverCode();
    //判断司机是否开户
    Map<String,Object> registerMap = validateDriverRegister(driverCode);
    Boolean registerFlag = (Boolean) registerMap.get("flag");
    if(registerFlag){
      String merchId = (String) registerMap.get("merchId");
      driverBankCardRelationService.updateMerchIdForKafka(relationCode,merchId);

      SelfRegisterBindPendRecEntity recEntity = new SelfRegisterBindPendRecEntity();
      recEntity.setCode(selfRegisterBindPendRecEntity.getCode());
      recEntity.setMerchantId(merchId);
      recEntity.setDriverRegisterStatus(BindBankCardConstant.Self_Register_Status.register);
      selfRegisterBindPendRecService.updateMerchantInfoForKafka(recEntity);
      //todo 调用网商绑卡接口。

    }else{
      String registerStatus = (String) registerMap.get("status");
      SelfRegisterBindPendRecEntity recEntity = new SelfRegisterBindPendRecEntity();
      recEntity.setCode(selfRegisterBindPendRecEntity.getCode());

      if(MerchRegisterConstant.registerStatus.dealing.equals(registerStatus)){
        recEntity.setDriverRegisterStatus(BindBankCardConstant.Self_Register_Status.pending);
        selfRegisterBindPendRecService.updateMerchantInfoForKafka(recEntity);
      }else if(MerchRegisterConstant.registerStatus.fail.equals(registerStatus)){
        recEntity.setDriverRegisterStatus(BindBankCardConstant.Self_Register_Status.unregister);
        selfRegisterBindPendRecService.updateMerchantInfoForKafka(recEntity);
        //todo 调用入驻开户接口

      }
    }


  }


  /**
   * 判断该用户是否开户
   * @param driverCode
   * @return
   */
  private Map<String,Object> validateDriverRegister(String driverCode){
    Map<String,Object> map = new HashMap<>();
    MerchRegisterEntity entity = merchRegisterDao.selectByMerchUserCode(driverCode);
    if(null != entity){//已开户
      map.put("flag",true);
      map.put("merchId",entity.getMerchId());
      return map;
    }
    //未查询到开户记录
    //List<String> resultStatusList = Arrays.asList(MerchRegisterConstant.registerStatus.dealing,MerchRegisterConstant.registerStatus.success);
    Map<String,Object> paramMap = new HashMap<>();
    //paramMap.put("resultStatusList",resultStatusList);
    paramMap.put("merchUserCode",driverCode);
    paramMap.put("resultStatus",MerchRegisterConstant.registerStatus.dealing);
    List<MerchRegisterRecordEntity> recordList = merchRegisterRecordDao.selectList(paramMap);
    if(CollectionUtils.isNotEmpty(recordList)){
        //开户状态待定
      map.put("flag",false);
      map.put("status",MerchRegisterConstant.registerStatus.dealing);
      return map;
    }
    map.put("flag",false);
    map.put("status",MerchRegisterConstant.registerStatus.fail);
    return map;
  }



  public String topic() {
    return "bind_self_bank_card";
  }

  public String getModuleCode() {
    return "WsCloudPay";
  }
}
