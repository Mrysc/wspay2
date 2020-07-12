package com.rltx.wspay.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.rltx.framework.log.support.BusinessException;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.*;
import com.rltx.wspay.account.service.IQueryService;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.constant.MapEntity;
import com.rltx.wspay.utils.MapChangeKay;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.misc.BASE64Decoder;

import java.util.*;

@Service
public class QueryServiceImpl implements IQueryService {

    @Autowired
    private MerchRegisterDao merchRegisterDao;
    @Override
    public Map<String,Object> balanceQuery(String code) throws Exception {
        String function = Constant.function.balanceQuery;
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByCode(code);
        if(ObjectUtils.isEmpty(merchRegisterEntity)){
            throw new BusinessException("用户未入住不可开户");
        }
        BalanceQueryEntity balanceQuery = new BalanceQueryEntity();
        balanceQuery.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        balanceQuery.setMerchantId(merchRegisterEntity.getMerchId());
        TreeMap<String, String> form = MapTrunPojo.object2Map(balanceQuery);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        TreeMap<String,Object> mapBody= XmlToMap.DocumentMapType(response,"body");
        if(mapBody.containsKey("BalanceList")&& StringUtils.isNotEmpty(mapBody.get("BalanceList").toString())) {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(mapBody.get("BalanceList").toString());
            List<Map<String, Object>> listMap = toListMap(new String(bytes));
            mapBody.put("balanceList",listMap);
            mapBody.remove("BalanceList");
        }
        mapBody.remove("RespInfo");
        return mapBody;
    }

    @Override
    public BatchQueryResponseEntity batchQuery(String code, String startTime, String endTime, String pageIndex, String pageSize) throws Exception {

        String function =Constant.function.batchQuery;
        MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByCode(code);
        if(ObjectUtils.isEmpty(merchRegisterEntity)){
            throw new BusinessException("用户未入住不可查询");
        }
        BatchQueryEntity batchQuery= new BatchQueryEntity();
        batchQuery.setIsvOrgId(ParamUtil.getParamInfoByKey("IsvOrgId"));
        batchQuery.setMerchantId(merchRegisterEntity.getMerchId());
        batchQuery.setStartTime(startTime);
        batchQuery.setEndTime(endTime);
        batchQuery.setPageIndex(pageIndex);
        batchQuery.setPageSize(pageSize);
        TreeMap<String, String> form = MapTrunPojo.object2Map(batchQuery);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);
        Map<String,Object> mapBody= XmlToMap.DocumentMapType(response,"body");
        Map<String, Object> map1=  MapChangeKay.transformUpperCase(mapBody);
        BatchQueryResponseEntity batchQueryResponseEntity= (BatchQueryResponseEntity) MapEntity.map2Object(map1, BatchQueryResponseEntity.class);
        if(mapBody.containsKey("VostroInfoList")&& StringUtils.isNotEmpty(mapBody.get("VostroInfoList").toString())){
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(mapBody.get("VostroInfoList").toString());
            List<Map<String,Object>> listMap = toListMap(new String(bytes));
            if(listMap.size()>0){
                VostroInfoEntity vostroInfoEntity= new VostroInfoEntity();
                List<VostroInfoEntity> vostroInfoList= new ArrayList<>();
                for (Map<String,Object> map:listMap) {
                    vostroInfoEntity= (VostroInfoEntity) MapEntity.map2Object(map, VostroInfoEntity.class);
                    vostroInfoList.add(vostroInfoEntity);
                }
                batchQueryResponseEntity.setVostroInfoList(vostroInfoList);
            }
        }

        return batchQueryResponseEntity;
    }

    @Override
    public String sceneBalanceQuery() throws Exception {

        String function =Constant.function.sceneBalanceQuery;

        SceneBalanceQueryEntity sceneBalanceQuery = new SceneBalanceQueryEntity(
                HttpMain.IsvOrgId,
                HttpMain.merchantId
        );

        TreeMap<String, String> form = MapTrunPojo.object2Map(sceneBalanceQuery);

        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);

        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);

        //自签自验结果
        System.out.println("验签结果：" +responseVerify);

        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);

        return response;
    }

    public static List<Map<String, Object>> toListMap(String json){
        List<Object> list = JSON.parseArray(json);
        List< Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
        for (Object object : list){
            Map <String,Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
            listw.add(ret);
        }
        return listw;
    }
}
