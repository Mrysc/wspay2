package com.rltx.wspay.other.controller;





import com.rltx.wspay.commom.*;
import com.rltx.wspay.constant.BizTypeEnum;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.utils.constant.ParamUtil;

import java.util.TreeMap;
import java.util.UUID;


//***
// 短信验证码发送接口
// /
public class SendSmsCode {

    public static void main(String[] args) throws Exception {
        System.out.println(sendSmsCode(
                UUID.randomUUID().toString(), BizTypeEnum.MerchantdApply, "15869018186"));


    }



    @SuppressWarnings("rawtypes")
    public static String sendSmsCode(String outTradeNo, BizTypeEnum bizType,
                                      String mobile) throws Exception {
        //短信验证码发送
        String function = Constant.function.sendSmsCode;

        TreeMap<String, String> form = new TreeMap<String, String>();
        form.put("IsvOrgId", ParamUtil.getParamInfoByKey("IsvOrgId"));
        form.put("BizType", bizType.getBizCode());
        form.put("OutTradeNo", outTradeNo);
        form.put("Mobile", mobile);

        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);

      //打印请求报文
        System.out.println(param);

        //开始对请求报文进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);

        //自签自验结果
        System.out.println("验签结果：" +responseVerify);

        String response = HttpMain.httpsReq(ParamUtil.getParamInfoByKey("reqUrl"), param);


      return  response;
    }
}
