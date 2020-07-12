package com.rltx.wspay.constant;

public interface Constant {
    interface function{
        //入驻
        String register = "ant.mybank.merchantprod.merch.register";
        //商户入驻结果查询
        String registerQuery = "ant.mybank.merchantprod.merch.register.query";
        //商户入驻查询
        String merchQuery = "ant.mybank.merchantprod.merch.query";
        // 商户通用开户接口 开启保证金子户 合并子户子户专用
        String accountOpen = "ant.mybank.bkcloudfunds.account.open";
        //商户子户查询
        String accountQuery = "ant.mybank.bkcloudfunds.account.query";
        //余额查询
        String balanceQuery = "ant.mybank.bkcloudfunds.balance.query";
        //保证金查询
        String batchQuery = "ant.mybank.bkcloudfunds.vostro.charge.batchquery";
        //账户场景查询
        String sceneBalanceQuery = "ant.mybank.bkcloudfunds.merchant.scene.balance.query";
        //商户信息修改
        String updateMerchant = "ant.mybank.merchantprod.merch.updateMerchant";

        //上传图片
        String uploadPhoto = "ant.mybank.merchantprod.merchant.uploadphoto";
        //短信验证码发送
        String sendSmsCode = "ant.mybank.merchantprod.sendsmscode";


        String kybApply = "ant.mybank.bkmerchantprod.kyb.apply";
        String kybMatch = "ant.mybank.bkmerchantprod.kyb.match";

        //余额支付创建接口
        String balancePay = "ant.mybank.bkcloudfunds.balance.pay";
        //开通商户余额支付权限接口
        String openPay = "ant.mybank.bkcloudfunds.merchant.openPay";
        //余额支付确认接口
        String payconfirm = "ant.mybank.bkcloudfunds.balance.payconfirm";
        //余额支付查询接口
        String payQuery = "ant.mybank.bkcloudfunds.balance.payquery";

        //支付退回申请
        String refundApply = "ant.mybank.bkcloudfunds.refund.apply";
        //支付退回查询接口
        String refundQuery = "ant.mybank.bkcloudfunds.refund.query";

        //分账申请接口
        String orderShare = "ant.mybank.bkcloudfunds.order.share";
        //分账退回申请接口
        String refundShare = "ant.mybank.bkcloudfunds.refundshare.apply";
        //分账退回查询接口
        String refundShareQuery = "ant.mybank.bkcloudfunds.refundshare.query";
        //单笔分账查询接口
        String shareQuery = "ant.mybank.bkcloudfunds.ordershare.query";

        //商户单笔提现确认接口
        String applyConfirm = "ant.mybank.bkcloudfunds.withdraw.applyconfirm";
        //商户单笔提现申请接口
        String withdrawApply = "ant.mybank.bkcloudfunds.withdraw.apply";
        //单笔提现查询接口
        String withdrawQuery = "ant.mybank.bkcloudfunds.withdraw.query";

        String electronicreceiptApply = "ant.mybank.bkcloudfunds.electronicreceipt.apply";
        String electronicreceiptNotify = "ant.mybank.bkcloudfunds.electronicreceipt.notify";
        String electronicreceiptQuery = "ant.mybank.bkcloudfunds.electronicreceipt.query";
        String electronicreceiptBatchapply = "ant.mybank.bkcloudfunds.electronicreceipt.batchapply";
    }

}
