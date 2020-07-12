package com.rltx.wspay.other.entity;

import com.rltx.wspay.commom.SignatureUtils;
import com.rltx.wspay.commom.XmlVersion;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.utils.constant.ParamUtil;
import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

@Data
public class UploadPhotoEntity {


    private static final String CHARSET          = "UTF-8";
    private String              function         = Constant.function.uploadPhoto;
    private String              photoType;
    //外部交易号
    private String              outTradeNo;
    //图片二进制字符流
    private String              picture;
    //请求时间
    private String              reqTime;
    //签名
    private String              signature;



    public UploadPhotoEntity(String photoType, String outTradeNo, String picture) {
        this.photoType = photoType;
        this.outTradeNo = outTradeNo;
        this.picture = picture;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        this.reqTime =sdf.format(new Date());
        try {
            this.signature = sign();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String sign() throws Exception {
        Map<String, String> params = new TreeMap<String, String>();
        params.put("IsvOrgId", ParamUtil.getParamInfoByKey("IsvOrgId"));
        params.put("PhotoType", photoType);
        params.put("OutTradeNo", outTradeNo);
        params.put("Function", function);
        params.put("Version", XmlVersion.defaultVersion);
        params.put("AppId", ParamUtil.getParamInfoByKey("Appid"));
        params.put("ReqTime", reqTime);

        StringBuilder sb = new StringBuilder();
        PrivateKey privateKey =  SignatureUtils.getPrivateKey(ParamUtil.getParamInfoByKey("TEST_PRIVATE_KEY"));

        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        String source = URLEncoder.encode(sb.toString().substring(0, sb.toString().length() - 1),
                CHARSET);

        final Signature signatureChecker = Signature.getInstance(ParamUtil.getParamInfoByKey("SIGN_ALGORITHM"));
        signatureChecker.initSign(privateKey);
        signatureChecker.update(source.getBytes(CHARSET));
        String signature = Base64.encodeBase64String(signatureChecker.sign());

        return signature;
    }

}
