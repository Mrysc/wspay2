package com.rltx.wspay.utils.constant;

/**
 * Created by  zl on 2020/6/9 14:17.
 * Description:
 */
public class BindBankCardConstant {

    //绑定的是本人卡还是他人卡
    public interface Bank_Card_Owner{
        String self = "s";//本人卡

        String other = "o";//他人卡 O 拼音o
    }

    //银行卡绑定状态
    public interface Bank_Card_Bind_Status{
        String success = "success";
        String fail = "fail";
        String binding = "binding";
    }



    //他人入驻处理状态 ，用于他人入驻绑卡待处理记录表other_register_bind_pending_rec
    public interface Payee_Register_Status{
        String success = "S";
        String fail = "F";
        String dealing = "U"; //受理中
        String pend = "P"; //待处理，默认值
    }

    //他人绑卡状态
    public interface Payee_Bind_Card_Status{
        String bind = "Y"; //已绑定
        String unbind = "N"; //未绑定
        String pend = "P";//待处理 默认值
    }

    //绑定本人卡 入驻绑卡记录表
    public interface Self_Register_Status{
        String register = "Y";//入驻
        String unregister = "N";//未入驻
        String pending = "U"; //受理中
    }




    public interface Payee_Rec_Queue_Status{
        String fail = "F"; //失败
    }


}
