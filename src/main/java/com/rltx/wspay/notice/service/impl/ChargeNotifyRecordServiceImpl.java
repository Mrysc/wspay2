package com.rltx.wspay.notice.service.impl;

import com.rltx.wspay.account.dao.MerchConsignorMoneyDao;
import com.rltx.wspay.account.dao.MerchRegisterDao;
import com.rltx.wspay.account.entity.MerchConsignorMoneyEntity;
import com.rltx.wspay.account.entity.MerchRegisterEntity;
import com.rltx.wspay.account.service.IMerchConsignorMoneyService;
import com.rltx.wspay.commom.DomCreateResponse;
import com.rltx.wspay.commom.XmlSignUtil;
import com.rltx.wspay.commom.XmlToMap;
import com.rltx.wspay.constant.MapEntity;
import com.rltx.wspay.notice.dao.ChargeNotifyRecordDao;
import com.rltx.wspay.notice.entity.ChargeNotifyRecordEntity;
import com.rltx.wspay.notice.service.IChargeNotifyRecordService;
import com.rltx.wspay.pay.entity.PaymertRecordEntity;
import com.rltx.wspay.pay.service.IPayService;
import com.rltx.wspay.pay.service.IShareService;
import com.rltx.wspay.utils.MapChangeKay;
import com.rltx.wspay.utils.constant.PayConstant;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ChargeNotifyRecordServiceImpl implements IChargeNotifyRecordService {

    @Autowired
    private IPayService payService;
    @Autowired
    private MerchRegisterDao merchRegisterDao;
    @Autowired
    private ChargeNotifyRecordDao chargeNotifyRecordDao;
    @Autowired
    private IMerchConsignorMoneyService consignorMoneyService;
    @Autowired
    private IShareService iShareService;

    @Override
    public String notify(String data) throws Exception {
        boolean result =  XmlSignUtil.verify(data);
        TreeMap<String,String> map = XmlToMap.DocumentMap(data);
        //响应回执生成(报文组装步骤)
        String response = DomCreateResponse.requestcreateXml(map);
        //开始对响应回执进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(response);
        if(result&&responseVerify){
            transfer(data);
        }else {
            System.out.println("验签失败——result："+result+"   responseVerify:"+responseVerify);
        }
        return response;
    }


    public void transfer(String data){
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(new Runnable() {
            @SneakyThrows
            @Override
            public synchronized void run(){
                System.out.println("222");
                TreeMap<String,Object> mapBody= XmlToMap.DocumentMapType(data,"body");
                Map<String, Object> map1=  MapChangeKay.transformUpperCase(mapBody);
                ChargeNotifyRecordEntity chargeNotifyRecordEntity = (ChargeNotifyRecordEntity) MapEntity.map2Object(map1, ChargeNotifyRecordEntity.class);
                MerchRegisterEntity merchRegisterEntity = merchRegisterDao.selectByMerchId(chargeNotifyRecordEntity.getMerchantId());
                List<ChargeNotifyRecordEntity>  chargeNotifyRecordEntityList = chargeNotifyRecordDao.select(chargeNotifyRecordEntity.getOrderNo());
                if(chargeNotifyRecordEntityList.size()==0){
                    System.out.println("333");
                    if(!ObjectUtils.isEmpty(merchRegisterEntity)){
                        chargeNotifyRecordEntity.setMerchUserCode(merchRegisterEntity.getMerchUserCode());
                        chargeNotifyRecordEntity.setMerchName(merchRegisterEntity.getMerchName());
                    }
                    chargeNotifyRecordEntity.setCreateBy("网商异步通知");
                    chargeNotifyRecordEntity.setCreateTime(new Date());
                    chargeNotifyRecordEntity.setUpdateBy("网商异步通知");
                    chargeNotifyRecordEntity.setUpdateTime(new Date());
                    chargeNotifyRecordDao.insert(chargeNotifyRecordEntity);
                    String merchId = chargeNotifyRecordEntity.getMerchantId();
                    String money = chargeNotifyRecordEntity.getTotalAmount();

                    MerchRegisterEntity payee = merchRegisterDao.selectByMerchUserCodeType("842466928244647938", PayConstant.accountType.commonly);
                    TreeMap<String, Object> map = payService.balancePay(merchRegisterEntity, payee,chargeNotifyRecordEntity.getTotalAmount(),null);
                    if(map.containsKey("OrderNo")&&!ObjectUtils.isEmpty(map.get("OrderNo"))){
                        PaymertRecordEntity paymertRecordEntity = payService.payConfirm(map.get("code").toString());
                        iShareService.orderShare(paymertRecordEntity);
                        consignorMoneyService.update(merchId,money);
                    }else {
                        System.out.println(chargeNotifyRecordEntity.getMerchUserCode()+","+map.get("ResultMsg"));
                    }
                    System.out.println("444");
                }
            }
        });
        executor.shutdown();
        System.out.println("111");
    }

}