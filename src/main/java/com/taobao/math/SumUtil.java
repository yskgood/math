package com.taobao.math;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(i);
        }

        System.out.println("求和：" + format(numbers));
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
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(2 * i - 1);

        }

        System.out.println("求奇数和：" + format(numbers));

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
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            numbers.add(2 * i);
        }

        System.out.println("求偶数和：" + format(numbers));
        return n * (n + 1);
    }

    private static String format(List<Integer> numbers) {
        return numbers.stream().map(i -> Integer.toString(i)).collect(Collectors.joining("+"));
    }

    public static void main(String[] args) {
        System.out.println(sumOfN(6));
        System.out.println(sumOfOdd(6));
        System.out.println(sumOfEven(6));
    }
}
