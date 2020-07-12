package com.rltx.wspay.bindbankcard.service.impl;

import com.rltx.wspay.account.entity.BankCardParam;
import com.rltx.wspay.account.entity.MerchEntity;
import com.rltx.wspay.bindbankcard.service.IUpdateMeerchService;
import com.rltx.wspay.commom.*;
import com.rltx.wspay.constant.*;
import com.rltx.wspay.utils.constant.ParamUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.TreeMap;
import java.util.UUID;

@Service
public class UpdateMerchServiceImpl implements IUpdateMeerchService {

    @Override
    public String updateMerchant(MerchEntity register) throws Exception {
        register.setBankCardParam("eyJDb250YWN0TGluZSI6IjMxMzQ1MTAwNzQ5OCIsIkJhbmtDYXJkTm8iOiI4NjYxMTc0OTEwMTQy" +
                "MTAwNDUzMSIsIkNlcnRUeXBlIjoiMDEiLCJCYW5rQ2VydE5hbWUiOiLlsbHkuJzkuIflkozpgJrn" +
                "ianmtYHnp5HmioDmnInpmZDlhazlj7giLCJCcmFuY2hOYW1lIjoiIiwiQnJhbmNoQ2l0eSI6IiIs" +
                "IkNlcnRObyI6IjM3MTUyMjE5ODYwNzI1MTMxMyIsIkNhcmRIb2xkZXJBZGRyZXNzIjoi5rWO5YyX" +
                "5byA5Y+R5Yy66buE5rKz5aSn6KGXMTflj7ciLCJBY2NvdW50VHlwZSI6IjAyIiwiQnJhbmNoUHJv" +
                "dmluY2UiOiIifQ==");
        String function = Constant.function.updateMerchant;
        TreeMap<String, String> form = MapTrunPojo.object2Map(register);
        String param = DomCreateRequest.createRequestXml(function, XmlVersion.defaultVersion,form);
        String url = ParamUtil.getParamInfoByKey("reqUrl");
        boolean responseVerify =  XmlSignUtil.verifyFromYourSelf(param);
        String response = HttpMain.httpsReq(url, param);
        System.out.println(response);
        return response;
    }
}
