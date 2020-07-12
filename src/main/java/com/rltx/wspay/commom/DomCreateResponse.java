package com.rltx.wspay.commom;

import com.rltx.wspay.utils.constant.ParamUtil;
import com.sun.org.apache.xml.internal.security.signature.XMLSignature;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.security.PrivateKey;
import java.util.Iterator;
import java.util.Map;

public class DomCreateResponse {

    public static DocumentBuilder getDocumentBuilder(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db  = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return  db;
    }


    //各类回调通知响应
    public static String  requestcreateXml(Map notifyUrl) throws Exception{

        DocumentBuilder db = getDocumentBuilder();
        Document document = db.newDocument();

        Element root = document.createElement("document");

        Element full = document.createElement("response");
        full.setAttribute("id","response");

        Element head = document.createElement("head");



        Iterator<Map.Entry<String, String>> entries = notifyUrl.entrySet().iterator();
        while (entries.hasNext()) {

            Map.Entry<String, String> entry = entries.next();


            Element name = document.createElement(entry.getKey());

            if (entry.getKey().equals("ReqTime")){
                name = document.createElement("RespTime");
            }

            if(entry.getKey().equals("ReqTimeZone")){
                name = document.createElement("RespTimeZone");
            }

                name.setTextContent(entry.getValue());



            head.appendChild(name);

        }


        Element body = document.createElement("body");

        Element  RespInfo = document.createElement("RespInfo");

        Element ResultStatus = document.createElement("ResultStatus");
        ResultStatus.setTextContent("S");
        RespInfo.appendChild(ResultStatus);

        Element ResultCode = document.createElement("ResultCode");
        ResultCode.setTextContent("0000");
        RespInfo.appendChild(ResultCode);

        Element ResultMsg = document.createElement("ResultMsg");
        ResultMsg.setTextContent("成功");
        RespInfo.appendChild(ResultMsg);

        body.appendChild(RespInfo);

        full.appendChild(head);
        full.appendChild(body);
        root.appendChild(full);

        document.appendChild(root);

        PrivateKey privateKey = SignatureUtils.getPrivateKey(ParamUtil.getParamInfoByKey("TEST_PRIVATE_KEY"));

        String xmlResult = SignatureUtils.signXmlElement(privateKey, document, "response",
                XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA256, 2);

        return xmlResult;
    }





}
