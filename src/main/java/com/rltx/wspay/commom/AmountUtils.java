package com.rltx.wspay.commom;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Created by  lenovo on 2019/9/21 14:19.
 * Description: No Description
 */
public class AmountUtils {

    /**金额为分的格式 */
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";



    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static String changeF2Y(String amount) throws Exception{
        if(!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new Exception("金额格式有误");
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
    }


    /**
     * 元转分，确保price保留两位有效数字
     * @return
     */
    public static int changeY2F(double price) {
        DecimalFormat df = new DecimalFormat("#.00");
        price = Double.valueOf(df.format(price));
        int money = (int)(price * 100);
        return money;
    }

    public static String changeY2F(String amount) {
        NumberFormat format = NumberFormat.getInstance();
        try {
            Number number = format.parse(amount);
            double temp = number.doubleValue() * 100.0;
            format.setGroupingUsed(false);
            // 设置返回数的小数部分所允许的最大位数
            format.setMaximumFractionDigits(0);
            amount = format.format(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return amount;
    }


    /**
     * //判断子账户余额 与 支付款(提现款)的大小
     *  余额 》 支付、提现款 true
     *  余额 《 支付、提现款 false
     * @param accBalance
     * @param tradeAmount
     * @return
     */
    public static boolean compareAmount(String accBalance,String tradeAmount){
        BigDecimal balance_decimal = new BigDecimal(accBalance);
        BigDecimal tradeAmount_decimal = new BigDecimal(tradeAmount);

        int flag = balance_decimal.compareTo(tradeAmount_decimal);
        if(flag >=0){
            return true;
        }
        return false;
    }


    //通用 金额计算 方法
    public static String sumMoney(String m1,String m2){
        BigDecimal b1 = new BigDecimal(m1);
        b1 = b1.add(new BigDecimal(m2));
        return b1.toString();
    }


    public static void main(String[] args){
        String yuan = "2.0";
        String yuan2 = "1";
        String yuan3 = "3.02";

        String fen = changeY2F(yuan);
        System.out.println(fen);
        String fen2 = changeY2F(yuan2);
        System.out.println(fen2);

        String fen3 = changeY2F(yuan3);
        System.out.println(fen3);
    }

}
