package com.rltx.wspay.account.entity;

import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;


public class BankCardParam {
    String bankCardNo;
    String bankCertName;
    String accountType;
    String contactLine;
    String branchName;
    String branchProvince;
    String branchCity;
    String certType;
    String certNo;
    String cardHolderAddress;
    String bankCode;//开户银行名称


    public String genJsonBase64() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("BankCardNo", bankCardNo);
        obj.put("BankCertName", bankCertName);
        obj.put("AccountType", accountType);
        obj.put("ContactLine", contactLine);
        obj.put("BranchName", branchName);
        obj.put("BranchProvince", branchProvince);
        obj.put("BranchCity", branchCity);
        obj.put("CertType", certType);
        obj.put("CertNo", certNo);
        obj.put("CardHolderAddress", cardHolderAddress);

        System.out.println(obj.toString());

        return new BASE64Encoder().encode(obj.toString().getBytes());
    }
}