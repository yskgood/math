package com.taobao.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 如何对一个数进行质因子分解
 *
 * @author huichi  shaokai.ysk@alibaba-inc.com
 * @Description:
 * @date 2018/8/30 下午7:19
 */
public class PrimeNumber {

    /**
     * 朴素筛法，就是直接试除
     * 时间复杂度是O(n)
     *
     * @param number
     * @return
     */
    public static boolean isPrimeNumber(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 质因子分解
     * <p>
     * 假设要分解的整数为M
     * <p>
     * 1、获取小于M的所有质数
     * 2、从最小的质数2开始循环处理，如果是M的因子（M%2==0),添加2作为因子，对结果N（M/2）开始递归处理
     * 3、如果不是找下一个因子按上一个方式进行处理，直至所有的质数都穷尽
     *
     * @param number
     * @return
     */
    public static List<Integer> primeFactorization(int number) {
        List<Integer> factors = new ArrayList<Integer>();
        doPrimeFactorization(number, factors);
        return factors;
    }

    private static void doPrimeFactorization(int number, List<Integer> factors) {
        if (isPrimeNumber(number)) {
            factors.add(number);
            return;
        }
        //获取所有小于number的质数
        List<Integer> primeNumbers = getPrimeNumbers(number);
        //从最小的质数开始尝试
        for (int i = 0; i < primeNumbers.size(); i++) {
            int numerator = primeNumbers.get(i);
            if (number % numerator == 0) {
                //可以整除,添加质数因子
                factors.add(numerator);
                //递归处理
                doPrimeFactorization(number / numerator, factors);
                break;
            }
        }
    }

    /**
     * 获取所有小于参数的质数
     * 时间复杂度是O(N平方)
     *
     * @param number
     * @return
     */
    public static List<Integer> getPrimeNumbers(int number) {
        List<Integer> primeNumbers = new ArrayList<Integer>();
        for (int i = 2; i < number; i++) {
            if (isPrimeNumber(i)) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
}
