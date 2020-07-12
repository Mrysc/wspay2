package com.rltx.wspay.account.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rltx.common.service.IGenerationCodingService;
import com.rltx.framework.log.support.BusinessException;
import com.rltx.wspay.account.dao.*;
import com.rltx.wspay.account.entity.*;
import com.rltx.wspay.account.result.MerchRegisterResult;
import com.rltx.wspay.account.service.IMerchService;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.constant.*;
import com.rltx.wspay.other.dao.UploadPhotoDao;
import com.rltx.wspay.other.entity.PhotoEntity;
import com.rltx.wspay.other.service.IUploadService;
import com.rltx.wspay.utils.constant.MerchRegisterConstant;
import com.rltx.wspay.utils.constant.ParamUtil;
import com.rltx.wspay.utils.constant.PayConstant;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MerchServiceImpl implements IMerchService {

    @Resource
    private MemberPersonInfoDao memberPersonInfoDao;
    @Resource
    private MemberOrgInfoDao memberOrgInfoDao;
    @Autowired
    private MerchRegisterRecordDao registerRecordDao;
    @Autowired
    private MerchRegisterDao merchRegisterDao;
    @Autowired
    private UploadPhotoDao uploadPhotoDao;
    @Autowired
    private MerchRegisterRecordDao merchRegisterRecordDao;
    @Resource(name="generationCodingService")
    private IGenerationCodingService generationCodingService;
    @Autowired
    private MerchConsignorMoneyDao consignorMoneyDao;
    @Autowired
    private IUploadService uploadService;


    //企业入主接口
    @Override
    public MerchRegisterResult enterpriseRegister(String code) throws Exception {
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByMerchUserCodeType(code, PayConstant.accountType.commonly);
        if(!ObjectUtils.isEmpty(merchRegisterEntity)){
            throw new BusinessException("企业已入驻");
        }
        MemberOrgInfoEntity memberOrgInfoEntity =memberOrgInfoDao.select(code);
        uplodePhoto(memberOrgInfoEntity);
        MerchantDetailWithoutBankCard merchantDetail = merchantDetail(memberOrgInfoEntity);
        MerchEntity register = setEnterpriseMerch(memberOrgInfoEntity,merchantDetail);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr =  mapper.writeValueAsString(register);
        MerchRegisterRecordEntity merchRegisterRecordEntity=insertEnterpriseRecord(register,memberOrgInfoEntity,jsonStr);
        String response = register(register);
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        updateEnterpriseRecord(merchRegisterRecordEntity,mapRespInfo,response,mapBody);
        MerchRegisterResult merchRegisterResult = merchRegisterResult(mapRespInfo,mapBody);
        if(merchRegisterResult.getIsSuccess()){
            insertMerch(merchRegisterRecordEntity);
        }
        return merchRegisterResult;
    }

    //公司上传图片
    public void uplodePhoto(MemberOrgInfoEntity memberOrgInfoEntity){
        String type = "";
        if("0".equals(memberOrgInfoEntity.getOrgType())){
            type = MerchRegisterConstant.type.platform;
        }else {
            type = MerchRegisterConstant.type.consignor;
        }
        String businessLicenseResourceCode = memberOrgInfoEntity.getBusinessLicenseResourceCode();
        String legalPersonResourceCode = memberOrgInfoEntity.getLegalPersonResourceCode();
        if(StringUtils.isNotEmpty(legalPersonResourceCode)){
            String[] str = legalPersonResourceCode.split(":");
                try{
                    if(str.length==2){
                        uploadService.uplodePhoto(str[0],memberOrgInfoEntity.getCode(),PhotoType.CERT_PHOTO_A,type);
                        uploadService.uplodePhoto(str[1],memberOrgInfoEntity.getCode(),PhotoType.CERT_PHOTO_B,type);
                    }else {
                        uploadService.uplodePhoto(str[0],memberOrgInfoEntity.getCode(),PhotoType.CERT_PHOTO_A,type);
                        uploadService.uplodePhoto(str[0],memberOrgInfoEntity.getCode(),PhotoType.CERT_PHOTO_B,type);
                    }
                }catch (Exception e){
                    throw new BusinessException("上传身份证附件错误");
                }
        }else {
            throw new BusinessException("法人没有身份证附件");
        }
        if(StringUtils.isNotEmpty(businessLicenseResourceCode)){
            try{
                    uploadService.uplodePhoto(businessLicenseResourceCode,memberOrgInfoEntity.getCode(),PhotoType.LICENSE_PHOTO,type);
            }catch (Exception e){
                throw new BusinessException("上传营业执照附件错误");
            }
        }else {
            throw new BusinessException("没有营业执照附件");
        }
    }

    //企业基本参数
    public MerchEntity setEnterpriseMerch(MemberOrgInfoEntity memberOrgInfoEntity,MerchantDetailWithoutBankCard merchantDetail) throws JSONException {
        MerchEntity register = new MerchEntity();
        register.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        register.setOutTradeNo(TradeNoUtils.getTradeNo32());
        register.setOutMerchantId(TradeNoUtils.getTradeNo32());
        register.setMerchantType(MerchantType.ENTERPRISING);
        register.setMerchantName(memberOrgInfoEntity.getOrgFullName());
        register.setDealType(DealType.ENTITY_AND_INTERNET);
        register.setMerchantDetail(merchantDetail.genJsonBase64());
        return register;
    }

    //保存企业入驻记录
    public MerchRegisterRecordEntity insertEnterpriseRecord(MerchEntity register,MemberOrgInfoEntity memberOrgInfoEntity,String param){
        MerchRegisterRecordEntity merchRegisterRecordEntity = new MerchRegisterRecordEntity();
        merchRegisterRecordEntity.setOutTradeNo(register.getOutTradeNo());
        merchRegisterRecordEntity.setOutMerchId(register.getOutMerchantId());
        merchRegisterRecordEntity.setMerchUserCode(memberOrgInfoEntity.getCode());
        merchRegisterRecordEntity.setMerchName(memberOrgInfoEntity.getOrgFullName());
        if("0".equals(memberOrgInfoEntity.getOrgType())){
            merchRegisterRecordEntity.setType(MerchRegisterConstant.type.platform);
        }else {
            merchRegisterRecordEntity.setType(MerchRegisterConstant.type.consignor);
        }
        merchRegisterRecordEntity.setPhone(memberOrgInfoEntity.getLinkmanPhone());
        merchRegisterRecordEntity.setMerchType(register.getMerchantType());
        merchRegisterRecordEntity.setName(memberOrgInfoEntity.getLinkmanFullName());
        merchRegisterRecordEntity.setIsPersonal("0");
        merchRegisterRecordEntity.setRequestData(param);
        merchRegisterRecordEntity.preInsert();
        registerRecordDao.insert(merchRegisterRecordEntity);
        return merchRegisterRecordEntity;
    }

    //修改企业入驻记录
    public MerchRegisterRecordEntity updateEnterpriseRecord(MerchRegisterRecordEntity merchRegisterRecordEntity,TreeMap<String,Object> mapRespInfo,String response,TreeMap<String,String> mapBody){
        merchRegisterRecordEntity.setResponseData(response);
        if (mapBody.containsKey("OrderNo")){
            merchRegisterRecordEntity.setOrderNo(mapBody.get("OrderNo"));
        }
        if (mapRespInfo.containsKey("ResultStatus")){
            merchRegisterRecordEntity.setResultStatus(mapRespInfo.get("ResultStatus").toString());
        }
        registerRecordDao.update(merchRegisterRecordEntity);
        return merchRegisterRecordEntity;
    }

    //企业商户详情
    public MerchantDetailWithoutBankCard merchantDetail(MemberOrgInfoEntity memberOrgInfoEntity) throws Exception {
        List<PhotoEntity> photoList = new ArrayList<>();
        if("0".equals(memberOrgInfoEntity.getOrgType())){
            photoList = uploadPhotoDao.select(memberOrgInfoEntity.getCode(),MerchRegisterConstant.type.platform);
        }else {
            photoList = uploadPhotoDao.select(memberOrgInfoEntity.getCode(),MerchRegisterConstant.type.consignor);
        }
        MerchantDetailWithoutBankCard merchantDetail = new MerchantDetailWithoutBankCard();
        merchantDetail.setContactMobile(memberOrgInfoEntity.getLinkmanPhone());
        merchantDetail.setContactName(memberOrgInfoEntity.getLinkmanFullName());
        merchantDetail.setLegalPerson(memberOrgInfoEntity.getOrgFullName());
        merchantDetail.setPrincipalMobile(memberOrgInfoEntity.getLinkmanPhone());
        merchantDetail.setPrincipalCertType(PrincipalCertTypeEnum.IdentityCard);
        merchantDetail.setPrincipalCertNo(memberOrgInfoEntity.getLegalPersonIdentityNumber());
        merchantDetail.setPrincipalPerson(memberOrgInfoEntity.getLegalPersonName());
        for (PhotoEntity photo:photoList) {
            if(PhotoType.CERT_PHOTO_A.equals(photo.getPhotoType())){
                merchantDetail.setCertPhotoA(photo.getPhotoUrl());
            }else if(PhotoType.CERT_PHOTO_B.equals(photo.getPhotoType())){
                merchantDetail.setCertPhotoB(photo.getPhotoUrl());
            }else if(PhotoType.LICENSE_PHOTO.equals(photo.getPhotoType())){
                merchantDetail.setLicensePhoto(photo.getPhotoUrl());
            }else if(PhotoType.INDUSTRY_LICENSE_PHOTO.equals(photo.getPhotoType())){
                merchantDetail.setIndustryLicensePhoto(photo.getPhotoUrl());
            }
        }
        merchantDetail.setBussAuthParam(createJSONObject(memberOrgInfoEntity));
        return merchantDetail;
    }

    //企业商户证件详情
    private JSONObject createJSONObject(MemberOrgInfoEntity memberOrgInfoEntity) throws Exception {
        JSONObject s = new JSONObject();
        s.put("BussAuthType", "12");
        s.put("BussAuthNo", memberOrgInfoEntity.getCreditCode());
        return s;
    }

    //人员入驻接口
    @Override
    public MerchRegisterResult personRegister(String code) throws Exception {
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByMerchUserCodeType(code,PayConstant.accountType.commonly);
        if(!ObjectUtils.isEmpty(merchRegisterEntity)){
            throw new BusinessException("司机已入驻");
        }
        MemberPersonInfoEntity memberPerson = memberPersonInfoDao.select(code);
        String identityResourceCode = memberPerson.getIdentityResourceCode();
        uplodeDriverPhoto(identityResourceCode,memberPerson.getCode());
        MerchantDetailWithoutBankCard merchantDetail = merchantDetail(memberPerson);
        MerchEntity register = setPersonMerch(memberPerson,merchantDetail);
        ObjectMapper mapper = new ObjectMapper();
        String jsonStr =  mapper.writeValueAsString(register);
        MerchRegisterRecordEntity merchRegisterRecordEntity = insertPersonRecord(register,memberPerson,jsonStr);
        String response = register(register);
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        merchRegisterRecordEntity.preUpdate();
        updatePersonRecord(merchRegisterRecordEntity,mapRespInfo,response,mapBody);
        MerchRegisterResult merchRegisterResult = merchRegisterResult(mapRespInfo,mapBody);
        if(merchRegisterResult.getIsSuccess()){
            insertMerch(merchRegisterRecordEntity);
        }
        return merchRegisterResult;
    }

    //司机上传身份证图片
    public void uplodeDriverPhoto(String identityResourceCode,String userCode){
        if(StringUtils.isNotEmpty(identityResourceCode)){
            String[] str = identityResourceCode.split(":");
            try{
                if(str.length==2){
                    uploadService.uplodePhoto(str[0],userCode,PhotoType.CERT_PHOTO_A,MerchRegisterConstant.type.driver);
                    uploadService.uplodePhoto(str[1],userCode,PhotoType.CERT_PHOTO_B,MerchRegisterConstant.type.driver);
                }else {
                    uploadService.uplodePhoto(str[0],userCode,PhotoType.CERT_PHOTO_A,MerchRegisterConstant.type.driver);
                    uploadService.uplodePhoto(str[0],userCode,PhotoType.CERT_PHOTO_B,MerchRegisterConstant.type.driver);
                }
            }catch (Exception e){
                throw new BusinessException("上传图片错误");
            }
        }else {
            throw new BusinessException("司机没有身份证附件");
        }
    }

    //返回
    public MerchRegisterResult merchRegisterResult(TreeMap<String,Object> mapRespInfo,TreeMap<String,String> mapBody){
        MerchRegisterResult merchRegisterResult = new MerchRegisterResult();
        if("F".equals(mapRespInfo.get("ResultStatus"))){
            merchRegisterResult.setIsSuccess(false);
        }else {
            merchRegisterResult.setIsSuccess(true);
        }
        merchRegisterResult.setErrMsg(mapRespInfo.get("ResultMsg").toString());
        merchRegisterResult.setOrderNo(mapBody.get("OrderNo"));
        merchRegisterResult.setOutMerchantId(mapBody.get("OutMerchantId"));
        merchRegisterResult.setOutTradeNo(mapBody.get("OutTradeNo"));
        return merchRegisterResult;
    }

    //人员基本参数
    public MerchEntity setPersonMerch(MemberPersonInfoEntity memberPerson,MerchantDetailWithoutBankCard merchantDetail) throws JSONException {
        MerchEntity register = new MerchEntity();
        register.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        register.setOutTradeNo(TradeNoUtils.getTradeNo32());
        register.setOutMerchantId(TradeNoUtils.getTradeNo32());
        register.setMerchantType(MerchantType.NATURAL);
        register.setMerchantName(memberPerson.getFullName());
        register.setDealType(DealType.ENTITY_AND_INTERNET);
        register.setMerchantDetail(merchantDetail.genJsonBase64());
        return register;
    }

    //保存人员入住记录
    public MerchRegisterRecordEntity insertPersonRecord(MerchEntity register,MemberPersonInfoEntity memberPerson,String param){
        MerchRegisterRecordEntity merchRegisterRecordEntity = new MerchRegisterRecordEntity();
        merchRegisterRecordEntity.setOutTradeNo(register.getOutTradeNo());
        merchRegisterRecordEntity.setOutMerchId(register.getOutMerchantId());
        merchRegisterRecordEntity.setMerchUserCode(memberPerson.getCode());
        merchRegisterRecordEntity.setMerchName(memberPerson.getFullName());
        merchRegisterRecordEntity.setMerchType(register.getMerchantType());
        merchRegisterRecordEntity.setName(memberPerson.getFullName());
        merchRegisterRecordEntity.setPhone(memberPerson.getPhone());
        merchRegisterRecordEntity.setType(MerchRegisterConstant.type.driver);
        merchRegisterRecordEntity.setIsPersonal("0");

        merchRegisterRecordEntity.preInsert();
        registerRecordDao.insert(merchRegisterRecordEntity);
        return merchRegisterRecordEntity;
    }

    //修改人员入驻记录
    public MerchRegisterRecordEntity updatePersonRecord(MerchRegisterRecordEntity merchRegisterRecordEntity,TreeMap<String,Object> mapRespInfo,String response,TreeMap<String,String> mapBody){
        merchRegisterRecordEntity.setResponseData(response);
        if (mapBody.containsKey("OrderNo")){
            merchRegisterRecordEntity.setOrderNo(mapBody.get("OrderNo"));
        }
        if (mapRespInfo.containsKey("ResultStatus")){
            merchRegisterRecordEntity.setResultStatus(mapRespInfo.get("ResultStatus").toString());
        }
        registerRecordDao.update(merchRegisterRecordEntity);
        return merchRegisterRecordEntity;
    }

    @Override
    public TreeMap<String,Object> registerQuery(String outTradeNo) throws Exception {
        String function = Constant.function.registerQuery;
        MerchRegisterRecordEntity merchRegisterRecordEntity = merchRegisterRecordDao.selectByOutTradeNo(outTradeNo);
        MerchQueryEntity query = new MerchQueryEntity();
        query.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        query.setOrderNo(merchRegisterRecordEntity.getOrderNo());
        String url = ParamUtil.getParamInfoByKey("reqUrl");
        TreeMap<String, String> form = MapTrunPojo.object2Map(query);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(url, param);
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        mapRespInfo.putAll(mapBody);
        if(mapBody.containsKey("RegisterStatus")&&"1".equals(mapBody.get("RegisterStatus"))&&mapBody.containsKey("MerchantId")&&StringUtils.isNotEmpty(mapBody.get("MerchantId"))){
            insertRegister(merchRegisterRecordEntity,mapBody.get("MerchantId"));
        }
        return mapRespInfo;
    }


    public void insertRegister(MerchRegisterRecordEntity merchResponseEntity,String merchId){
        MerchRegisterEntity merchRegister = merchRegisterDao.selectByMerchUserCodeType(merchResponseEntity.getMerchUserCode(),PayConstant.accountType.commonly);
        if(StringUtils.isEmpty(merchRegister.getMerchId())){
            merchRegister.setMerchId(merchId);
            merchRegister.setRegisterStatus("1");
            merchRegisterDao.updateMerchId(merchRegister);
            MerchConsignorMoneyEntity consignorMoneyEntity = new MerchConsignorMoneyEntity();
            consignorMoneyEntity.setMerchId(merchId);
            consignorMoneyEntity.setMerchName(merchRegister.getMerchName());
            consignorMoneyEntity.setMerchUserCode(merchRegister.getMerchUserCode());
            consignorMoneyEntity.setMoney(0.0);
            consignorMoneyEntity.preInsert();
            consignorMoneyDao.insert(consignorMoneyEntity);
        }
    }

    @Override
    public TreeMap<String,Object> merchQuery(String code) throws Exception {
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByCode(code);
        String function = Constant.function.merchQuery;
        MerchMessageQueryEntity query = new MerchMessageQueryEntity();
        query.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        query.setMerchantId(merchRegisterEntity.getMerchId());
        String url = ParamUtil.getParamInfoByKey("reqUrl");
        TreeMap<String, String> form = MapTrunPojo.object2Map(query);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(url, param);
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(response);
        TreeMap<String,Object> mapRespInfo= XmlToMap.DocumentMapType(response,"RespInfo");
        Gson gson = new Gson();
        BASE64Decoder decoder = new BASE64Decoder();
        if(mapBody.containsKey("MerchantDetail")&&StringUtils.isNotEmpty(mapBody.get("MerchantDetail"))){
            byte[] bytes = decoder.decodeBuffer(mapBody.get("MerchantDetail"));
            TreeMap<String,String> map = new TreeMap<>();
            map = gson.fromJson(new String(bytes), map.getClass());
            mapRespInfo.putAll(map);
            mapBody.remove("MerchantDetail");
        }
        if(mapBody.containsKey("BankCardParam")&&StringUtils.isNotEmpty(mapBody.get("BankCardParam"))){
            byte[] bytes = decoder.decodeBuffer(mapBody.get("BankCardParam"));
            TreeMap<String,String> map = new TreeMap<>();
            map = gson.fromJson(new String(bytes), map.getClass());
            mapRespInfo.putAll(map);
            mapBody.remove("BankCardParam");
        }
        mapRespInfo.putAll(mapBody);
        return mapRespInfo;
    }

    //人员商户详情
    public MerchantDetailWithoutBankCard merchantDetail(MemberPersonInfoEntity memberPerson){
        List<PhotoEntity> photoList = uploadPhotoDao.select(memberPerson.getCode(),MerchRegisterConstant.type.driver);
        MerchantDetailWithoutBankCard merchantDetail = new MerchantDetailWithoutBankCard();
        merchantDetail.setContactMobile(memberPerson.getPhone());
        merchantDetail.setContactName(memberPerson.getFullName());
        merchantDetail.setPrincipalMobile(memberPerson.getPhone());
        merchantDetail.setPrincipalCertType(PrincipalCertTypeEnum.IdentityCard);
        merchantDetail.setPrincipalCertNo(memberPerson.getIdentityNumber());
        merchantDetail.setPrincipalPerson(memberPerson.getFullName());
        for (PhotoEntity photo:photoList) {
            if(PhotoType.CERT_PHOTO_A.equals(photo.getPhotoType())){
                merchantDetail.setCertPhotoA(photo.getPhotoUrl());
            }else if(PhotoType.CERT_PHOTO_B.equals(photo.getPhotoType())){
                merchantDetail.setCertPhotoB(photo.getPhotoUrl());
            }
        }
        return merchantDetail;
    }

    //最终入驻调用接口
    @Override
    public String register(MerchEntity register) throws Exception {
        String function = Constant.function.register;
        TreeMap<String, String> form = MapTrunPojo.object2Map(register);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        String url = ParamUtil.getParamInfoByKey("reqUrl");
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(url, param);
        return response;
    }


    public void insertMerch(MerchRegisterRecordEntity merchResponseEntity){
        MerchRegisterRecordEntity merchRegisterRecordEntity = merchRegisterRecordDao.selectByOutTradeNo(merchResponseEntity.getOutTradeNo());
        merchRegisterRecordDao.updateMerchId(merchRegisterRecordEntity);
        MerchRegisterEntity merchRegisterEntity = new MerchRegisterEntity();
        merchRegisterEntity.setCode(generationCodingService.generateCurrencyCode());
        merchRegisterEntity.setMerchId(merchRegisterRecordEntity.getMerchId());
        merchRegisterEntity.setOutMerchId(merchRegisterRecordEntity.getOutMerchId());
        merchRegisterEntity.setMerchUserCode(merchRegisterRecordEntity.getMerchUserCode());
        merchRegisterEntity.setMerchName(merchRegisterRecordEntity.getMerchName());
        merchRegisterEntity.setType(merchRegisterRecordEntity.getType());
        merchRegisterEntity.setMerchType(merchRegisterRecordEntity.getMerchType());
        merchRegisterEntity.setPhone(merchRegisterRecordEntity.getPhone());
        merchRegisterEntity.setName(merchRegisterRecordEntity.getName());
        merchRegisterEntity.preInsert();
        merchRegisterDao.insert(merchRegisterEntity);
    }

}
