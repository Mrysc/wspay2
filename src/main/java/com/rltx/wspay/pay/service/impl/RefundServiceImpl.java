package com.rltx.wspay.pay.service.impl;

import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.constant.ParticipantType;
import com.rltx.wspay.pay.entity.*;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.pay.service.IRefundService;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.springframework.stereotype.Service;

import java.util.TreeMap;

@Service
public class RefundServiceImpl implements IRefundService {


    @Override
    public String refundApply() throws Exception {

        String function = Constant.function.refundApply;

        RefundApplyEntity refundApply = new RefundApplyEntity(
                HttpMain.IsvOrgId,
                ParticipantType.MERCHANT,
                HttpMain.merchantId,
                "",
                TradeNoUtils.getTradeNo32(),
                "",
                HttpMain.Currency,
                ""
        );

        TreeMap<String, String> form = new TreeMap<String, String>();

        form = MapTrunPojo.object2Map(refundApply);


        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);


        //打印请求报文
        System.out.println(param);

        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);

        //自签自验结果
        System.out.println("验签结果：" +responseVerify);

        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);

        System.out.println(response);

        return response;
    }

    @Override
    public String refundQuery() throws Exception {

        String function = Constant.function.refundQuery;

        RefundQueryEntity refundQuery = new RefundQueryEntity(
                HttpMain.IsvOrgId,
                "",
                ParticipantType.MERCHANT,
                HttpMain.merchantId,
                "",
                ""
        );

        TreeMap<String, String> form = new TreeMap<String, String>();

        form = MapTrunPojo.object2Map(refundQuery);


        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);


        //打印请求报文
        System.out.println(param);

        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);

        //自签自验结果
        System.out.println("验签结果：" +responseVerify);

        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);

        System.out.println(response);

        return response;
    }
}
