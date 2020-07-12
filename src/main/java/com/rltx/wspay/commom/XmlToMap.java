package com.rltx.wspay.commom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.TreeMap;

public class XmlToMap {

    public static TreeMap<String,String> DocumentMap(String xml) throws Exception {
        TreeMap<String,String> map = new TreeMap();
        Document document =  XmlSignUtil.parseDocumentByString(xml);

        Node head  = document.getElementsByTagName("head").item(0);

        NodeList childNodes = head.getChildNodes();

        for (int i=0;i<childNodes.getLength();i++){

            if(childNodes.item(i).getNodeType() ==Node.ELEMENT_NODE){
                map.put(childNodes.item(i).getNodeName(),childNodes.item(i).getTextContent());
            }

        }

        return map;
    }

    public static TreeMap<String,String> DocumentMapBody(String xml) throws Exception {
        TreeMap<String,String> map = new TreeMap();
        Document document =  XmlSignUtil.parseDocumentByString(xml);

        Node body  = document.getElementsByTagName("body").item(0);

        NodeList childNodes = body.getChildNodes();

        for (int i=0;i<childNodes.getLength();i++){

            if(childNodes.item(i).getNodeType() ==Node.ELEMENT_NODE){
                map.put(childNodes.item(i).getNodeName(),childNodes.item(i).getTextContent());
            }

        }

        return map;
    }
    public static TreeMap<String,Object> DocumentMapType(String xml,String type) throws Exception {
        TreeMap<String,Object> map = new TreeMap();
        Document document =  XmlSignUtil.parseDocumentByString(xml);

        Node body  = document.getElementsByTagName(type).item(0);

        NodeList childNodes = body.getChildNodes();

        for (int i=0;i<childNodes.getLength();i++){

            if(childNodes.item(i).getNodeType() ==Node.ELEMENT_NODE){
                map.put(childNodes.item(i).getNodeName(),childNodes.item(i).getTextContent());
            }

        }

        return map;
    }
}
