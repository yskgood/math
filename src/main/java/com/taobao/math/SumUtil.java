package com.taobao.math;

/**
 * 计算合
 *
 * @author huichi  shaokai.ysk@alibaba-inc.com
 * @Description:
 * @date 2018/10/30 上午10:36
 */
public class SumUtil {

    /**
     * 计算 1+2+....+n的和
     * <p>
     * s=n(n+1)/2
     *
     * @param n
     * @return
     */
    public static int sumOfN(int n) {
        StringBuilder number = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (number.length() == 0) {
                number.append(i);
            } else {
                number.append("+" + i);
            }
        }

        System.out.println("求和：" + number);
        return n * (n + 1) / 2;
    }

    /**
     * 计算n个奇数的和 1+3+5+.....+(2n-1)
     * <p>
     * s=n^2
     *
     * @param n
     * @return
     */
    public static int sumOfOdd(int n) {
        StringBuilder odd = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (odd.length() == 0) {
                odd.append((2 * i - 1));
            } else {
                odd.append("+" + (2 * i - 1));
            }

        }

        System.out.println("求奇数和：" + odd);

        return n * n;
    }

    /**
     * 计算n个偶数的和 2+4+6+....+2n
     * <p>
     * s=n(n+1)
     *
     * @param n
     * @return
     */
    public static int sumOfEven(int n) {
        StringBuilder even = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (even.length() == 0) {
                even.append(2 * i);
            } else {
                even.append("+" + 2 * i);
            }
        }

        System.out.println("求偶数和：" + even);
        return n * (n + 1);
    }

    public static void main(String[] args) {
        System.out.println(sumOfN(6));
        System.out.println(sumOfOdd(6));
        System.out.println(sumOfEven(6));
    }
}
