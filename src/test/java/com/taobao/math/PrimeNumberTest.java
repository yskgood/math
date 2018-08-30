package com.taobao.math;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author huichi  shaokai.ysk@alibaba-inc.com
 * @Description:
 * @date 2018/8/30 下午7:22
 */
public class PrimeNumberTest {

    @Test
    public void isPrimeNumber() {
        Assert.assertTrue(PrimeNumber.isPrimeNumber(2));

        for (int i = 2; i < 200; i++) {
            if (PrimeNumber.isPrimeNumber(i)) {
                System.out.println(i);
            }
        }
    }

    @Test
    public void primeFactorization() {

        List<Integer> factors = PrimeNumber.primeFactorization(9);
        for (int i = 0; i < factors.size(); i++) {
            System.out.println(factors.get(i));
        }
    }
}