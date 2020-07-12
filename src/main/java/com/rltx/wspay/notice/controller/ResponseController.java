package com.rltx.wspay.notice.controller;


import com.rltx.wspay.commom.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.TreeMap;


@Controller
public class ResponseController {

    /***
     * 自我测试使用方法：
     * 工具选用postman工具 params 选body  raw ->xml
     * 检查head请求头 Content-Type 是否为 application/xml
     * 选用 post 点击发送 即可
     * */
//    @RequestMapping(value = "/notice", method = RequestMethod.POST,consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    @PostMapping(value="/notice", produces= {"application/xml;charset=UTF-8"} )
    public  String notice(@RequestBody String  data) throws Exception {

//         对来自网商得报文做签名验证
         boolean result =  XmlSignUtil.verify(data);
         //验签结果
         System.out.println(result);

        //开始对响应回执进行组装
       /***
        * 1 采用String解析成Document 获取 head 节点内容
        * 2 将节点内容封装成Map
        * 3 开始组装报文
        * */
        TreeMap<String,String> map= XmlToMap.DocumentMap(data);
        TreeMap<String,String> mapBody= XmlToMap.DocumentMapBody(data);
        //响应回执生成(报文组装步骤)
        String response = DomCreateResponse.requestcreateXml(map);

        //开始对响应回执进行签名验证(自签自验环节)
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(response);

        //验证结果
        System.out.println(responseVerify);

        //打印回执
        System.out.println(response);
//        System.out.println(data);
        //发送回执
        return  null;

    }


}
