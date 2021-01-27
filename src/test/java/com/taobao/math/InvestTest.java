package com.taobao.math;

import org.junit.Test;

/**
 * @author huichi  shaokai.ysk@alibaba-inc.com
 * @Description:
 * @date 2020/3/28 上午10:46
 */
public class InvestTest {

    @Test
    public void test() {
        double des = 0.03;
        for (int i = 0; i < 5; i++) {
            calculate(des);
            des += 0.01;
        }
    }

    private void calculate(double des) {
        System.out.println("================" + des + "=================");
        //每次买入
        int size = 300;
        //每次递增
        int incSize = 300;
        //初始股价
        double start = 12.85;
        //买入总金额
        int total = 30000;
        //当前买入总金额
        double sum = 0;
        //剩余金额
        double less = 0;
        //买入数量
        int count = 0;
        //不能超过总金额，每跌des个点就买入size股
        while ((sum + (start * size)) <= total) {
            sum += start * size;
            less = total - sum;
            count += size;
            System.out.println(String.format("本次买入：%d 买入价格：%.2f 累积买入股数：%d 累积买入金额：%.2f 平均成本：%.2f 剩余金额：%.2f", size, start, count, sum,(sum/count), less));
            //跌des个点
            start = start - (start * des);
            //买入递增
            size += incSize;
            //如果剩余金额不够，买入数量要减少
            while (less < (size * start)) {
                size -= 100;
            }

            if (size == 0) {
                break;
            }

        }
    }
}
