package com.rltx.wspay.constant;

import org.json.JSONException;
import org.json.JSONObject;

public class BussAuthParam {
   private String bussAuthType;

   private String bussAuthNo;

public String getBussAuthType() {
	return bussAuthType;
}

public void setBussAuthType(String bussAuthType) {
	this.bussAuthType = bussAuthType;
}

public String getBussAuthNo() {
	return bussAuthNo;
}

public void setBussAuthNo(String bussAuthNo) {
	this.bussAuthNo = bussAuthNo;
}
   
   


public String genJsonBase64() throws JSONException {
    JSONObject obj = new JSONObject();
    obj.put("BussAuthType", bussAuthType);
    obj.put("BussAuthNo", bussAuthNo);
    
    System.out.println(obj.toString());

    return obj.toString();
    
}




	
	  
	
	
	
}
