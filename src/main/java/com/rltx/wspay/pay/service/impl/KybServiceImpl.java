package com.rltx.wspay.pay.service.impl;

import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.pay.entity.*;
import com.rltx.wspay.pay.service.IKybService;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Service
public class KybServiceImpl implements IKybService {



    @Override
    public String kybApply() throws Exception {

        String function = Constant.function.kybApply;

        KybApplyEntity apply = new KybApplyEntity(
                TradeNoUtils.getTradeNo32().replaceAll("-",""),
                HttpMain.IsvOrgId,
                HttpMain.merchantId
        );


        TreeMap<String, String> form = new TreeMap<String, String>();

        form = MapTrunPojo.object2Map(apply);


        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);


        //打印请求报文
        System.out.println(param);

        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);

        //自签自验结果
        System.out.println("验签结果：" +responseVerify);

        String response = HttpMain.httpReq(ParamUtil.getParamInfoByKey("kybApply"),param);

        System.out.println(response);

        return response;
    }

    @Override
    public String kybMatch() throws Exception {
        String function = Constant.function.kybMatch;
        KybMatchEntity match = new KybMatchEntity(
                TradeNoUtils.getTradeNo32().replaceAll("-",""),
                HttpMain.IsvOrgId,
                HttpMain.merchantId,
                "202004031012052200000000000090040100025800",
                "2020040311150710350000000000000000168348",
                "51"

        );

        TreeMap<String, String> form = new TreeMap<String, String>();

        form = MapTrunPojo.object2Map(match);


        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);


        //打印请求报文
        System.out.println(param);

        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);

        //自签自验结果
        System.out.println("验签结果：" +responseVerify);

        String response = HttpMain.httpReq(ParamUtil.getParamInfoByKey("kybMatch"),param);

        System.out.println(response);
        return response;
    }
}
