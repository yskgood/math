package com.taobao.math;

import org.junit.Test;

/**
 * 等价格买入股票
 */
public class DengJia {

    @Test
    public void test() {
        //每次买入金额
        double money = 15000;
        //总金额
        double totalMoney = 60000;
        //第一次买入股价
        double price = 17.23;
        //每次下跌几个点买入
        double des = 0.06;
        //剩余金额
        double less = 0;
        //买入总数量
        int totalBuyCount = 0;
        //买入总金额
        double totalBuyMoney = 0;

        for (int i = 1; i <= (int) (totalMoney / money); i++) {
            //本次买了多少手，一手100股
            int shou = (int) (money / (price * 100));
            int buyCount = shou * 100;
            totalBuyCount += buyCount;
            //本次买入多少钱
            double buyMoney = buyCount * price;
            //本次剩余
            double buyLess = money - buyMoney;
            //总剩余多少钱
            less += money - buyMoney;
            totalBuyMoney += buyMoney;

            System.out.println(String.format("本次买入：%d 买入价格：%.2f 买入金额：%.2f 剩余金额：%.2f", buyCount, price, buyMoney, buyLess));

            //跌des个点
            price = price - (price * des);
        }

        //剩余的钱还能买多少手
        int shou = (int) (less / (price * 100));
        System.out.println(String.format("剩余金额：%.2f", less));
        if (shou > 0) {
            int buyCount = shou * 100;
            totalBuyCount += buyCount;
            //本次买入多少钱
            double buyMoney = buyCount * price;
            //本次剩余
            less = less - buyMoney;
            //买入总金额
            totalBuyMoney += buyMoney;

            System.out.println(String.format("本次买入：%d 买入价格：%.2f 买入金额：%.2f 剩余金额：%.2f", buyCount, price, buyMoney, less));
        }


        System.out.println(String.format("总计买入：%d 平均价格：%.2f  剩余金额：%.2f", totalBuyCount, totalBuyMoney / totalBuyCount, less));
    }
}
