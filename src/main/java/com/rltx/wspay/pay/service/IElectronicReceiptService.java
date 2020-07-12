package com.rltx.wspay.pay.service;


import java.util.TreeMap;

public interface IElectronicReceiptService {

    TreeMap<String, Object> apply(String paymertCode) throws Exception;
    TreeMap<String, Object> query(String paymertCode) throws Exception;
}
