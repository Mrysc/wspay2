package com.rltx.wspay.utils.constant;

public class MerchRegisterConstant {

    public interface type{
        String driver = "01";//司机
        String consignor = "02";//货主
        String platform = "03"; //平台
    }

    public interface merchType{
        String person = "01";//自然人
        String individual = "02";//个体工商户
        String enterprise = "03"; //企业商户
    }

    public interface openPay{
        String open = "true";//开通
        String notOpen = "false";//未开通
    }

    public interface registerStatus{
        String success = "S";
        String fail = "F";
        String dealing = "U";
    }

}
