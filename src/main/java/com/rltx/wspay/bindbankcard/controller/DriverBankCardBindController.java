package com.rltx.wspay.bindbankcard.controller;


import com.rltx.common.base.BaseContext;
import com.rltx.framework.resource.service.ICopyResourceService;
import com.rltx.wspay.account.entity.MemberPersonInfoEntity;
import com.rltx.wspay.bindbankcard.converter.DriverBankCardBindFormConverter;
import com.rltx.wspay.bindbankcard.entity.OtherMemberPayeeInfoEntity;
import com.rltx.wspay.bindbankcard.form.DriverBankCardBindForm;
import com.rltx.wspay.bindbankcard.result.BankCardBindResult;
import com.rltx.wspay.bindbankcard.result.BankCardBindValidateResult;
import com.rltx.wspay.bindbankcard.service.IDriverBankCardBindService;
import com.rltx.wspay.bindbankcard.service.IOtherMemberPayeeInfoService;
import com.rltx.wspay.utils.BankCardUtils;
import com.rltx.wspay.utils.entity.BankCardDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by  zl on 2020/6/26 15:41.
 * Description:
 */
@RestController
@RequestMapping("driverBindCard")
public class DriverBankCardBindController extends BaseContext {
    @Autowired
    private IDriverBankCardBindService driverBankCardBindService;
    @Autowired
    private IOtherMemberPayeeInfoService otherMemberPayeeInfoService;
    @Autowired
    private ICopyResourceService copyResourceService;



    public DriverBankCardBindController() {
        super("WsCloudPay");
    }


    /**
     * 验证绑定的银行卡是否需要上传身份证照片
     * 根据四要素
     * 如果需要上传身份证照片，isSuccess返回true
     * @param driverBankCardBindForm
     * @return
     */
    @PostMapping("validateBankCardInfo")
    public BankCardBindValidateResult validatePayeeInfo(DriverBankCardBindForm driverBankCardBindForm){
        BankCardBindValidateResult result = new BankCardBindValidateResult();

        //1.首先判断是绑本人卡还是他人卡，如果是本人卡，无需验证
        Boolean selfOrOtherCardFlag = driverBankCardBindService.validateCardIsSelfOrOther(driverBankCardBindForm);
        if(selfOrOtherCardFlag){
            result.setIsSuccess(false);
            result.setErrMsg("绑定本人卡，无需上传身份证");
            return result;
        }
        //判断该卡所有人在收款人信息表中是否存在，如果存在，无需上传身份证照片
        String bankAccIdNo = driverBankCardBindForm.getBankAccIdNumber();
        OtherMemberPayeeInfoEntity memberPayeeInfoEntity = otherMemberPayeeInfoService.selectByIdNumber(bankAccIdNo);
        if(null != memberPayeeInfoEntity){
            result.setIsSuccess(false);
            result.setErrMsg("该银行卡所有人信息存在，无需上传身份证");
            return result;
        }
        //针对不存在的情况，在司机表里根据身份证查找该银行卡所有人信息，如果存在，将该信息存储至 收款人信息表中，无需上传身份证照片；，否则，需要上传身份证照片
        MemberPersonInfoEntity entity = otherMemberPayeeInfoService.selectDriverInfoByIdNo(bankAccIdNo);
        if(null != entity){
            String resourceCodes = entity.getIdentityResourceCode();
            if(StringUtils.isNotBlank(resourceCodes)){
                String[] resourceCodeArr = resourceCodes.split(":");
                for (int i=0;i<resourceCodeArr.length;i++){
                    String resourceCode = resourceCodeArr[i];
                    String srcModule = "member";
                    copyResourceService.copy(srcModule,resourceCode,"ws_cloud_pay");
                }
            }
            //存储收款人信息
            OtherMemberPayeeInfoEntity payeeInfoEntity = DriverBankCardBindFormConverter.converterToOtherMemberPayeeInfoEntity(driverBankCardBindForm);
            payeeInfoEntity.setIdentityResourceCode(resourceCodes);
            otherMemberPayeeInfoService.insertSelect(payeeInfoEntity);

            result.setIsSuccess(false);
            result.setErrMsg("该银行卡所有人信息存在，无需上传身份证");
            return result;
        }

        result.setIsSuccess(true);
        result.setErrMsg("需要上传身份证照片");
        return result;

    }




    @PostMapping("bind")
    public BankCardBindResult bindBankCard(DriverBankCardBindForm driverBankCardBindForm){
        BankCardBindResult result = new BankCardBindResult();
        String frontCode = driverBankCardBindForm.getFrontResourceCode();
        String backCode = driverBankCardBindForm.getBackResourceCode();
        if(StringUtils.isNotBlank(driverBankCardBindForm.getFrontResourceCode()) || StringUtils.isNotBlank(driverBankCardBindForm.getBackResourceCode())){
            //存储收款人信息
            OtherMemberPayeeInfoEntity memberPayeeInfoEntity = DriverBankCardBindFormConverter.converterToOtherMemberPayeeInfoEntity(driverBankCardBindForm);
            frontCode = StringUtils.isNotBlank(frontCode) ? frontCode : "";
            backCode = StringUtils.isNotBlank(backCode) ? backCode : "";
            String resourceCode = frontCode + ":" + backCode;
            memberPayeeInfoEntity.setIdentityResourceCode(resourceCode);
            //存在值为2，不存在值为1
            int otherMemberPayeeInfoExist = otherMemberPayeeInfoService.insertSelect(memberPayeeInfoEntity);
        }
        //该逻辑主要用于避免前端resourceCode传值问题。存在bug,只有绑他人卡时，该逻辑才满足。
        /*String bankAccIdNumber = driverBankCardBindForm.getBankAccIdNumber();
        OtherMemberPayeeInfoEntity otherMemberPayeeInfoEntity = otherMemberPayeeInfoService.selectByIdNumber(bankAccIdNumber);
        if(null == otherMemberPayeeInfoEntity){
            result.setIsSuccess(false);
            result.setBindStatus(BindBankCardConstant.Bank_Card_Bind_Status.fail);
            result.setErrMsg("该银行卡收款人信息不存在！请联系客户处理！");
            return result;
        }*/

        //验证银行卡 卡bin
        BankCardDetail bankCardDetail = BankCardUtils.getBankCardDetails(driverBankCardBindForm.getBankCardNo());
        if(!bankCardDetail.isValidated()){
            result.setIsSuccess(false);
            result.setErrMsg("银行卡卡bin校验不通过！请检查银行卡号");
            return result;
        }
        result =   driverBankCardBindService.bindBankCard(driverBankCardBindForm);
        return result;
    }



}
