package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.common.service.IGenerationCodingService;
import com.rltx.wspay.bindbankcard.converter.DriverBankCardBindFormConverter;
import com.rltx.wspay.bindbankcard.entity.*;
import com.rltx.wspay.bindbankcard.form.DriverBankCardBindForm;
import com.rltx.wspay.bindbankcard.producer.IBindBankCardProducer;
import com.rltx.wspay.bindbankcard.result.BankCardBindResult;
import com.rltx.wspay.bindbankcard.service.*;
import com.rltx.wspay.utils.constant.BindBankCardConstant;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by  zl on 2020/6/21 10:15.
 * Description:
 */
@Service
public class DriverBankCardBindServiceImpl implements IDriverBankCardBindService {
    private static final Logger logger = LoggerFactory.getLogger(DriverBankCardBindServiceImpl.class);

    @Autowired
    private IDriverBankCardService driverBankCardService;
    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;

    @Autowired
    private ISelfRegisterBindPendRecService selfRegisterBindPendRecService;
    @Autowired
    private IBindBankCardProducer bindBankCardProducer;
    @Autowired
    private IDriverBankCardRelationService driverBankCardRelationService;
    @Autowired
    private IOtherMemberPayeeInfoService otherMemberPayeeInfoService;
    @Autowired
    private IOtherRegisterBindPendRecService otherRegisterBindPendRecService;
    @Autowired
    private IOtherRegisterBindPendRecQueueService otherRegisterBindPendRecQueueService;
    @Autowired
    private IOtherMerchRegisterService otherMerchRegisterService;

    /**
     * 0.首先进行银行卡卡bin校验(卡bin校验放在controller里面处理)
     * 1.根据身份证号正反面判断收款人信息是否存在
     * 1.判断银行卡是否存在：不存在--》保存银行卡信息
     * 2.判断用户、银行卡关系是否存在：（1）存在，提示该卡已绑定 ，返回结果，结束。
     * 3.判断是本人卡还是他人卡：针对情况，分别处理。
     *
     *
     * @param driverBankCardBindForm
     */
    @Override
    public BankCardBindResult bindBankCard(DriverBankCardBindForm driverBankCardBindForm) {
        BankCardBindResult result = new BankCardBindResult();

        //1.判断银行卡是否存在。if not exist,then save bankcard info.
        Map<String,Object> bankCardExistMap = validateBankCardExist(driverBankCardBindForm);
        Boolean bankCardExistFlag = (Boolean) bankCardExistMap.get("flag");
        String bankCardCode = "";
        if(bankCardExistFlag){
            bankCardCode = (String) bankCardExistMap.get("code");
        }else{
            DriverBankCardEntity entity =  saveBankCardInfo(driverBankCardBindForm);
            bankCardCode = entity.getCode();
        }

        //2. 判断用户、银行卡关系是否存在，if exist,then prompt have existed,return,end;
        Boolean driverBankCardExistFlag = validateDriverBankCardRelationExist(driverBankCardBindForm);
        if(driverBankCardExistFlag){
            result.setIsSuccess(false);
            result.setErrMsg("您已经绑定了该卡，请勿重复绑定！");
            return result;
        }
        //3.判断是本人卡还是他人卡，针对情况，分别处理
        Boolean cardIsSelfOrOtherFlag = validateCardIsSelfOrOther(driverBankCardBindForm);
        if(cardIsSelfOrOtherFlag){//本人卡
            BankCardBindResult selfBindCardResult =   bindSelfBankCard(driverBankCardBindForm,bankCardCode);
            return selfBindCardResult;
        }
        //接下来处理 绑定他人卡
        BankCardBindResult otherBindCardResult = bindOtherBankCard(driverBankCardBindForm,bankCardCode);
        return otherBindCardResult;
    }


    /**
     * 判断 银行卡是否已经存在，如果存在返回true，否则返回false
     * @param driverBankCardBindForm
     * @return
     */
    private Map<String,Object> validateBankCardExist(DriverBankCardBindForm driverBankCardBindForm){
        String bankCardNo = driverBankCardBindForm.getBankCardNo();
        DriverBankCardEntity entity =  driverBankCardService.selectByBankCardNo(bankCardNo);
        Map<String,Object> resMap = new HashMap<>();
        if(null != entity ){
            resMap.put("code",entity.getCode());
            resMap.put("flag",true);
            return resMap;
        }
        resMap.put("flag",false);
        return resMap;
    }

    /**
     * 判断用户是否已经绑过该银行卡
     * 如果已经绑过该卡，返回true，否则返回false
     * @param driverBankCardBindForm
     * @return
     */
    private Boolean validateDriverBankCardRelationExist(DriverBankCardBindForm driverBankCardBindForm){
        Map<String,String> params = new HashMap<>();
        params.put("driverUserCode",driverBankCardBindForm.getDriverCode());
        params.put("bankCardNo",driverBankCardBindForm.getBankCardNo());
        List<DriverBankCardRelationEntity> list =  driverBankCardRelationService.selectByParams(params);
        if(CollectionUtils.isNotEmpty(list)){
            return true;
        }
        return false;
    }

    /**
     * 保存银行卡信息
     * @param driverBankCardBindForm
     */

    private DriverBankCardEntity saveBankCardInfo(DriverBankCardBindForm driverBankCardBindForm){
        DriverBankCardEntity driverBankCardEntity = DriverBankCardBindFormConverter.convertToDriverBankCardEntity(driverBankCardBindForm);
        driverBankCardService.save(driverBankCardEntity);
        return driverBankCardEntity;
    }

    /***
     * 验证是本人银行卡还是 他人银行卡
     * 根据账户名和身份证号验证是否是 绑本人卡
     * 如果是本人卡，返回true；
     * 他人卡，返回false
     *
     * @param driverBankCardBindForm
     * @return
     */
    public Boolean validateCardIsSelfOrOther(DriverBankCardBindForm driverBankCardBindForm){
        String driverName = driverBankCardBindForm.getDriverName();
        String driverIdNumber = driverBankCardBindForm.getDriverIdNumber();
        String bankAccName = driverBankCardBindForm.getBankAccountName();
        String bankAccIdNumber = driverBankCardBindForm.getBankAccIdNumber();
        if(driverName.equals(bankAccName) && driverIdNumber.equals(bankAccIdNumber)){
            return true;
        }
        return false;
    }

    /**
     * 绑定本人银行卡处理
     * 1.判断本人是否已经绑过银行卡，
     * if binded,then prompt “您已经绑定了一张本人的银行卡，只允许绑定一张本人银行卡。是否替换。”
     * 如果替换，前端调用替换绑卡接口，否则，不做处理。
     * 2.else ,保存用户、卡关系，设置本人卡标志位
     * @param driverBankCardBindForm
     * @param bankCardCode  银行卡实体code
     */
    private BankCardBindResult bindSelfBankCard(DriverBankCardBindForm driverBankCardBindForm, String bankCardCode){
        BankCardBindResult bindSelfResult = new BankCardBindResult();
        String driverCode = driverBankCardBindForm.getDriverCode();
        Boolean bindSelfCardFlag = validateSelfBankCardList(driverCode);
        if(bindSelfCardFlag){
            bindSelfResult.setIsSuccess(false);
            bindSelfResult.setErrMsg("您已经绑定了一张本人银行卡，只允许绑定一张本人卡，是否替换？");
            bindSelfResult.setBizFlag(true);
            return bindSelfResult;
        }
        //尚未绑过本人银行卡，保存用户银行卡关系
        DriverBankCardRelationEntity driverBankCardRelationEntity = DriverBankCardBindFormConverter.converterToDriverBankCardRelationEntity(
                driverBankCardBindForm,BindBankCardConstant.Bank_Card_Owner.self,bankCardCode,BindBankCardConstant.Bank_Card_Bind_Status.binding);
        driverBankCardRelationService.saveDriverBankCardRelation(driverBankCardRelationEntity);

        //存储本人绑卡记录表，记录开户状态和绑卡状态
        SelfRegisterBindPendRecEntity selfRegisterBindPendRecEntity = DriverBankCardBindFormConverter.converterToSelfRegisterBindPendRecEntity(
                driverBankCardBindForm,driverBankCardRelationEntity.getCode());
        selfRegisterBindPendRecService.saveSelfBindBankCardPendRec(selfRegisterBindPendRecEntity);

        //发送kafka消息处理
        bindBankCardProducer.sendBindSelfBankCardMsg(selfRegisterBindPendRecEntity);
        bindSelfResult.setIsSuccess(true);
        bindSelfResult.setErrMsg("处理成功，请查看绑卡状态！");
        bindSelfResult.setBindStatus(BindBankCardConstant.Bank_Card_Bind_Status.binding);
        return bindSelfResult;
    }

    /**
     * 判断本人是否已经绑过银行卡
     * 1.if  binded,then return true;
     * else ,return false;
     * @param driverCode
     * @return
     */
    private Boolean validateSelfBankCardList(String driverCode){
        Map<String,String> params = new HashMap<>();
        params.put("driverUserCode",driverCode);
        params.put("selfOtherFlag",BindBankCardConstant.Bank_Card_Owner.self);
        List<DriverBankCardRelationEntity> driverBankCardRelationEntityList = driverBankCardRelationService.selectByParams(params);
        if(CollectionUtils.isNotEmpty(driverBankCardRelationEntityList)){
            return true;
        }
        return false;
    }


    private BankCardBindResult buildFailBankCardBindResult(String failMsg){
        BankCardBindResult bindOtherResult = new BankCardBindResult();
        bindOtherResult.setIsSuccess(false);
        bindOtherResult.setErrMsg(failMsg);
        bindOtherResult.setBindStatus(BindBankCardConstant.Bank_Card_Bind_Status.fail);
        return bindOtherResult;
    }
    private BankCardBindResult buildBindingBankCardBindResult(){
        BankCardBindResult bindOtherResult = new BankCardBindResult();
        bindOtherResult.setIsSuccess(true);
        bindOtherResult.setErrMsg("处理成功，请查看绑卡状态！");
        bindOtherResult.setBindStatus(BindBankCardConstant.Bank_Card_Bind_Status.binding);
        return bindOtherResult;
    }




    /**
     * 绑定他人银行卡 逻辑处理
     * 1. 判断 该卡的身份证号已经开了几个户了，如果到了4个，提示绑卡失败，请联系客服人员。
     * 2. 判断 该卡在他人最终开户表是否存在，if exist，then validate four elements : if it's consistent,then save,prompt bind success,return; else,prompt bind fail because of nonconsistent four elements.
     * 3. 判断四要素在司机绑卡关系表中是否存在，如果存在，状态为失败，直接返回绑定失败，提示失败原因。状态为绑定中，直接做插入操作。返回结果，结束。
     * 4. 如果3不存在，判断他人表中是否存在该用户逻辑-----------------------------------------------------------------------------------------|
     *                                                                                                                                        |
     * 1. 判断关系表中该卡在他人绑定关系中的商户号是否存在                                                                                    |
     * 2. if exist,then save driver_bank_card_relation(driver_code,bank_info,bank_acc_merchant_id),prompt bind success; end;                  |
     *    else :                                                                                                                              |
     *    (1) validate other_member_payee_info exist bankAccountIdNo          《--------------------------------------------------------------|
     *        if not,then save other_member_payee_info; then validate other_register_bind_pend_rec exist this bank_card_no. ;
     *                                                  if exist, validate  four elements  exist in other_register_bind_pend_rec_queue,
     *                                                            if exist , then end;
     *                                                            else, save other_register_bind_pend_rec_queue.then end.
     *                                                  else,save other_register_bind_pend_rec,send kafka msg.
     *        else:
     *
     *
     * @param driverBankCardBindForm
     * @param bankCardCode
     * @return
     */
    private BankCardBindResult bindOtherBankCard(DriverBankCardBindForm driverBankCardBindForm, String bankCardCode){
        BankCardBindResult bindOtherResult = new BankCardBindResult();

        String driverCode = driverBankCardBindForm.getDriverCode();

        //1. validate bankcard exist in other_merch_register
        String bankCardNo = driverBankCardBindForm.getBankCardNo();
        //--------------------逻辑重构---------------------------------------------------------------------------------------------------------
        Map<String,Object> payeeMerchRegisterMap = validateExistMerchRegister(driverBankCardBindForm);
        Boolean existFlag = (Boolean) payeeMerchRegisterMap.get("existFlag");
        if(existFlag){
            Boolean consistentFlag = (Boolean) payeeMerchRegisterMap.get("consistentFlag");
            if(consistentFlag){
                String bankCardAccMerchId = (String) payeeMerchRegisterMap.get("merchantId");
                DriverBankCardRelationEntity driverBankCardRelationEntity = DriverBankCardBindFormConverter.converterToDriverBankCardRelationEntity(driverBankCardBindForm,
                        BindBankCardConstant.Bank_Card_Owner.other,bankCardCode,BindBankCardConstant.Bank_Card_Bind_Status.success);
                driverBankCardRelationEntity.setMerchantId(bankCardAccMerchId);
                driverBankCardRelationService.saveDriverBankCardRelation(driverBankCardRelationEntity);
                bindOtherResult.setIsSuccess(true);
                bindOtherResult.setErrMsg("绑定成功！");
                bindOtherResult.setBindStatus(BindBankCardConstant.Bank_Card_Bind_Status.success);
                return bindOtherResult;
            }else{
                String failMsg = (String) payeeMerchRegisterMap.get("remarks");
                bindOtherResult = buildFailBankCardBindResult(failMsg);
                return bindOtherResult;
            }
        }
        if(payeeMerchRegisterMap.containsKey("numFlag")){
            String failMsg = (String) payeeMerchRegisterMap.get("remarks");
            bindOtherResult = buildFailBankCardBindResult(failMsg);
            return bindOtherResult;
        }
        //2. validate bankcard four elements  exist in driver_bank_card_relation
        Map<String,String> fourElements = getFourELements(driverBankCardBindForm);
        List<DriverBankCardRelationEntity> otherRelationList = driverBankCardRelationService.selectOtherRelationByFourEles(fourElements);
        Map<String,Object> relationExistMap = validateRelationBindStatus(otherRelationList);
        Boolean relationExistFlag = (Boolean) relationExistMap.get("existFlag");
        if(relationExistFlag){
            String bindStatus = (String) relationExistMap.get("bindStatus");
            if(BindBankCardConstant.Bank_Card_Bind_Status.fail.equals(bindStatus)){
                //绑定失败
                String failMsg = (String) relationExistMap.get("failMsg");
                bindOtherResult = buildFailBankCardBindResult(failMsg);
                return bindOtherResult;
            }else{
                // 存储司机银行卡关系，绑定中
                DriverBankCardRelationEntity driverBankCardRelationEntity = DriverBankCardBindFormConverter.converterToDriverBankCardRelationEntity(
                        driverBankCardBindForm,BindBankCardConstant.Bank_Card_Owner.other,bankCardCode,BindBankCardConstant.Bank_Card_Bind_Status.binding
                );
                driverBankCardRelationService.saveDriverBankCardRelation(driverBankCardRelationEntity);
                bindOtherResult = buildBindingBankCardBindResult();
                return bindOtherResult;
            }
        }
        //存储司机绑卡关系
        DriverBankCardRelationEntity driverBankCardRelationEntity = DriverBankCardBindFormConverter.converterToDriverBankCardRelationEntity(
                driverBankCardBindForm,BindBankCardConstant.Bank_Card_Owner.other,bankCardCode,BindBankCardConstant.Bank_Card_Bind_Status.binding
        );
        driverBankCardRelationService.saveDriverBankCardRelation(driverBankCardRelationEntity);
        //判断待开户绑卡记录里是否存在
        //   (3) validate other_register_bind_pend_rec exist this payeeInfo's bankCard info;  by payee's  bankCardNo
        //               if exist,
        //                  then validate other_register_bind_pend_rec_queue exist payeeInfo's bankCard info. by payee's four elements.
        //                  if exist, end;
        //                  else save; end;
        //               else: save  other_register_bind_pend_rec,send kafka msg, end.

        String bankAccIdNumber = driverBankCardBindForm.getBankAccIdNumber();
        OtherRegisterBindPendRecEntity otherRegisterBindPendRecEntity = otherRegisterBindPendRecService.selectByBankCardNo(bankCardNo);
        if(null == otherRegisterBindPendRecEntity){
            OtherMemberPayeeInfoEntity payeeInfoEntity = otherMemberPayeeInfoService.selectByIdNumber(bankAccIdNumber);
            otherRegisterBindPendRecEntity = DriverBankCardBindFormConverter.convertToOtherRegisterBindPendRec(driverBankCardBindForm,payeeInfoEntity.getCode());
            int insertVal = otherRegisterBindPendRecService.insertSelect(otherRegisterBindPendRecEntity);
            if(1 == insertVal){
                //   (4) send register kafka
                //otherRegisterBindPendRecEntity = otherRegisterBindPendRecService.selectByBankCardNo(bankCardNo);
                bindBankCardProducer.sendBindOtherBankCardMsg(otherRegisterBindPendRecEntity);

                bindOtherResult = buildBindingBankCardBindResult();
                return bindOtherResult;
            }
        }
        //该银行卡号在待开户绑卡记录表中，
        // first validate four elements is consistent with other_register_bind_pend_rec: if it's consistency,then end;
        // else:
        // then validate other_register_bind_pend_rec_queue exist payeeInfo's bankCard info. by payee's four elements.
        //                  if exist, end;
        //                  else save; end;
        if(validateFourEleConsistency(driverBankCardBindForm,otherRegisterBindPendRecEntity)){
            bindOtherResult = buildBindingBankCardBindResult();
            return bindOtherResult;
        }
        OtherRegisterBindPendRecQueueEntity queueEntity = otherRegisterBindPendRecQueueService.selectByFourElements(
                driverBankCardBindForm.getBankAccountName(),driverBankCardBindForm.getBankAccIdNumber(),driverBankCardBindForm.getBankCardNo(),driverBankCardBindForm.getPhone());
        if(null == queueEntity){
            queueEntity = DriverBankCardBindFormConverter.convertToOtherRegisterBindPendRecQueue(driverBankCardBindForm);
            otherRegisterBindPendRecQueueService.save(queueEntity);
            bindOtherResult = buildBindingBankCardBindResult();
            return bindOtherResult;
        }
        //if four elements exist in queue,then validate queueStatus,if queue_status = 'F' then return bind fail. else: return binding
        String queueStatus = queueEntity.getQueueStatus();
        if(BindBankCardConstant.Payee_Rec_Queue_Status.fail.equals(queueStatus)){
            // update driver_bank_card_relation fail by  four elements.
            driverBankCardRelationService.updateOtherBindStatusByFourEles(driverBankCardBindForm.getBankAccountName(),
                    driverBankCardBindForm.getBankAccIdNumber(),driverBankCardBindForm.getBankCardNo(),driverBankCardBindForm.getPhone(),BindBankCardConstant.Bank_Card_Bind_Status.fail,"四要素不一致！");

            bindOtherResult = buildFailBankCardBindResult("绑定失败！四要素不一致！");
            return bindOtherResult;
        }
        bindOtherResult = buildBindingBankCardBindResult();
        return bindOtherResult;
        //---------------------- 重构结束-------------------------------------------------------------------------------------------
    }





    private Map<String,Object> validateRelationBindStatus(List<DriverBankCardRelationEntity> relationList) {
        Map<String,Object> map = new HashMap<>();
        if(CollectionUtils.isNotEmpty(relationList)){
            //判断状态
            List<DriverBankCardRelationEntity> filterList = relationList.stream().filter(entity -> entity.getBindStatus().equals(BindBankCardConstant.Bank_Card_Bind_Status.fail)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(filterList)){
                map.put("existFlag",true);//该四要素是否存在
                map.put("bindStatus",BindBankCardConstant.Bank_Card_Bind_Status.fail); //
                DriverBankCardRelationEntity entity = filterList.get(0);
                map.put("failMsg",entity.getBindFailMsg());
                return map;
            }else{
                map.put("existFlag",true);//该四要素是否存在
                map.put("bindStatus",BindBankCardConstant.Bank_Card_Bind_Status.binding); //
                return map;
            }
        }
        map.put("existFlag",false);
        return map;

    }

    private Map<String,String> getFourELements(DriverBankCardBindForm driverBankCardBindForm){
        Map<String,String> map = new HashMap<>();
        map.put("bankAccName",driverBankCardBindForm.getBankAccountName());
        map.put("bankAccIdNo",driverBankCardBindForm.getBankAccIdNumber());
        map.put("bankCardNo",driverBankCardBindForm.getBankCardNo());
        map.put("bankAccPhone",driverBankCardBindForm.getPhone());
        return map;

    }

    private Map<String,Object> validateExistMerchRegister(DriverBankCardBindForm driverBankCardBindForm){
        Map<String,Object> resultMap = new HashMap<>();

        String payeeIdNo = driverBankCardBindForm.getBankAccIdNumber();
        String bankCardNo = driverBankCardBindForm.getBankCardNo();
        List<OtherMerchRegisterEntity> list = otherMerchRegisterService.selectListByIdNo(payeeIdNo);
        if(CollectionUtils.isNotEmpty(list)){
            List<OtherMerchRegisterEntity> filterList =   list.stream().filter(entity -> entity.getBankCardNo().equals(bankCardNo)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(filterList)){
                OtherMerchRegisterEntity merchRegisterEntity = filterList.get(0);
                //验证四要素是否一致
                Map<String,String> paramMap = new HashMap<>();
                paramMap.put("bankAccName",merchRegisterEntity.getBankAccName());
                paramMap.put("bankAccIdNo",merchRegisterEntity.getPayeeIdNo());
                paramMap.put("bankAccPhone",merchRegisterEntity.getPhone());
                if(validateFourEleConsistency(driverBankCardBindForm,paramMap)){
                    resultMap.put("existFlag",true);//是否存在标志
                    resultMap.put("consistentFlag",true);//一致性标志
                    resultMap.put("merchantId",merchRegisterEntity.getMerchantId());
                    return resultMap;
                }
                resultMap.put("existFlag",true);//是否存在标志
                resultMap.put("consistentFlag",false);//一致性标志
                resultMap.put("remarks","绑卡失败！四要素不一致！");
                return resultMap;
            }
            if(list.size() == 4 ){
                resultMap.put("existFlag",false);
                resultMap.put("numFlag",true);//4个上限的标志
                resultMap.put("remarks","绑卡失败！请联系客服人员！");
            }
            resultMap.put("existFlag",false);//是否存在标志
            return resultMap;
        }
        resultMap.put("existFlag",false);//是否存在标志
        return resultMap;
    }

    /**
     * 验证绑卡的四要素一致性
     */
    private Map<String,Object> validateFourEleConsistency(DriverBankCardBindForm driverBankCardBindForm,
                                                          DriverBankCardRelationEntity driverBankCardRelationEntity){
        Map<String,Object> resultMap = new HashMap<>();

        String driver_bankAccName = driverBankCardBindForm.getBankAccountName();
        String driver_bankAccIdNo = driverBankCardBindForm.getBankAccIdNumber();
        String driver_bankAccPhone = driverBankCardBindForm.getPhone();

        String merchant_bankAccName = driverBankCardRelationEntity.getBankAccountName();
        String merchant_bankAccIdNo = driverBankCardRelationEntity.getBankAccountIdNo();
        String merchant_bankAccPhone = driverBankCardRelationEntity.getBankAccountPhone();

        if(merchant_bankAccName.equals(driver_bankAccName) && merchant_bankAccIdNo.equals(driver_bankAccIdNo)
                && merchant_bankAccPhone.equals(driver_bankAccPhone)){
            resultMap.put("flag",true);
            return resultMap;
        }else{
            resultMap.put("flag",false);
            resultMap.put("remarks","四要素填写错误，请核对！");
            return resultMap;
        }
    }

    /**
     * 验证待绑的他人卡 四要素是否与 他人开户绑卡记录表里的数据四要素是否一致
     * 一致 返回true
     * 不一致，返回false
     */
    private boolean validateFourEleConsistency(DriverBankCardBindForm driverBankCardBindForm,
                                               OtherRegisterBindPendRecBaseEntity recEntity){
        String driver_bankAccName = driverBankCardBindForm.getBankAccountName();
        String driver_bankAccIdNo = driverBankCardBindForm.getBankAccIdNumber();
        String driver_bankAccPhone = driverBankCardBindForm.getPhone();

        String rec_bankAccName = recEntity.getPayeeBankAccountName();
        String rec_bankAccIdNo = recEntity.getPayeeIdNo();
        String rec_bankAccPhone = recEntity.getPayeePhone();

        if(rec_bankAccName.equals(driver_bankAccName) && rec_bankAccIdNo.equals(driver_bankAccIdNo)
                && rec_bankAccPhone.equals(driver_bankAccPhone)){
            return true;
        }
        return false;
    }



    private boolean validateFourEleConsistency(DriverBankCardBindForm driverBankCardBindForm,
                                               Map<String,String> params){
        String driver_bankAccName = driverBankCardBindForm.getBankAccountName();
        String driver_bankAccIdNo = driverBankCardBindForm.getBankAccIdNumber();
        String driver_bankAccPhone = driverBankCardBindForm.getPhone();

        String rec_bankAccName = params.get("bankAccName");
        String rec_bankAccIdNo = params.get("bankAccIdNo");
        String rec_bankAccPhone = params.get("bankAccPhone");

        if(rec_bankAccName.equals(driver_bankAccName) && rec_bankAccIdNo.equals(driver_bankAccIdNo)
                && rec_bankAccPhone.equals(driver_bankAccPhone)){
            return true;
        }
        return false;
    }


}
