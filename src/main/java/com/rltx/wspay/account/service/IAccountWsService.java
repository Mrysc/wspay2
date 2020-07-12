package com.rltx.wspay.account.service;



import java.util.Map;
import java.util.TreeMap;

public interface IAccountWsService {
    TreeMap<String,Object> accountOpen(String code) throws Exception;
    Map<String,String> accountQuery(String code) throws Exception;
}
