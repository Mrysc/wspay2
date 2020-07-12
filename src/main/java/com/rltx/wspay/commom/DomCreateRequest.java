package com.rltx.wspay.commom;

import com.rltx.wspay.utils.constant.ParamUtil;
import com.sun.org.apache.xml.internal.security.signature.XMLSignature;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.*;

public class DomCreateRequest {


    /***
     * 要点  拆份成 head body 子节点
     * head 根据function 和 版本（版本号建议写枚举）
     * body 根据 map 封装 遍历获取
     * 最后加签 自验
     * @param function
     * @param Version
     * @return
     */

    public static Element createHeadElement(String function,String Version,Document document){


        Element head = document.createElement("head");

        Element version =document.createElement("Version");
        version.setTextContent(Version);

        Element appid = document.createElement("Appid");
        appid.setTextContent(ParamUtil.getParamInfoByKey("Appid"));

        Element Function = document.createElement("Function");
        Function.setTextContent(function);

        Element reqTime = document.createElement("ReqTime");
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDhhmmss");
        reqTime.setTextContent(sdf.format(new Date()));

        Element reqMsgId = document.createElement("ReqMsgId");
        reqMsgId.setTextContent(UUID.randomUUID().toString().replace("-",""));

        Element inputCharset = document.createElement("InputCharset");
        inputCharset.setTextContent("UTF-8");


        Element signType = document.createElement("SignType");
        signType.setTextContent("RSA");

        head.appendChild(version);
        head.appendChild(appid);
        head.appendChild(Function);
        head.appendChild(reqTime);
        head.appendChild(reqMsgId);
        head.appendChild(inputCharset);
        head.appendChild(signType);

        return head;
    }


    public static Element createBodyElement (TreeMap<String,String > bodyMap,Document document){

        Element body = document.createElement("body");

        Iterator<Map.Entry<String, String>> entries = bodyMap.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry<String, String> entry = entries.next();

            Element name = document.createElement(entry.getKey());

            if(entry.getKey().toString().equals("ReqTime")){
                name.setTextContent(entry.getValue());
            }else{
                name.setTextContent(entry.getValue());
            }

            body.appendChild(name);

        }


        return  body;

    }




    public static  String createRequestXml(String function,String Version,TreeMap<String,String > bodyMap) throws Exception{
        Document document = DomCreateResponse.getDocumentBuilder().newDocument();
        Element root = document.createElement("document");

        Element full = document.createElement("request");
        full.setAttribute("id","request");

        Element head = createHeadElement(function,Version,document);

        Element body = createBodyElement(bodyMap,document);

        full.appendChild(head);
        full.appendChild(body);
        root.appendChild(full);

        document.appendChild(root);

        PrivateKey privateKey = SignatureUtils.getPrivateKey(ParamUtil.getParamInfoByKey("TEST_PRIVATE_KEY"));

        String xmlResult = SignatureUtils.signXmlElement(privateKey, document, "request",
                XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256, 2);

        return xmlResult;

    }





}
