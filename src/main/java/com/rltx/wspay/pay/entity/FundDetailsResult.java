package com.rltx.wspay.pay.entity;

import com.google.gson.JsonArray;
import lombok.Data;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

@Data
public class FundDetailsResult {

    private String amount;
    private String currency;
    private String participantId;
    private String participantType;
    private String purpose;


    public String genJsonBase64() throws JSONException {
        JSONArray objList = new JSONArray();

        JSONObject obj = new JSONObject();

        obj.put("Amount", amount);
        obj.put("Currency", currency);
        obj.put("ParticipantId", participantId);
        obj.put("ParticipantType", participantType);
        obj.put("Purpose", purpose);
        objList = JSONArray.fromObject(obj);
        System.out.println(objList.toString());
        return new BASE64Encoder().encode(objList.toString().getBytes());
    }
}
