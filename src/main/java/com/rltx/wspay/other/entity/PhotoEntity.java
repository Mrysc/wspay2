package com.rltx.wspay.other.entity;

import com.rltx.wspay.commom.SignatureUtils;
import com.rltx.wspay.commom.XmlVersion;
import com.rltx.wspay.constant.Constant;
import com.rltx.wspay.utils.constant.ParamUtil;
import com.rltx.wspay.utils.entity.BaseEntity;
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
public class PhotoEntity extends BaseEntity {

    private String outTradeNo;
    private String photoType;
    private String photoUrl;
    private String merchUserCode;
    private String type;

}
