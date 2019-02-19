package cn.adbyte.java8newtest.func;

import org.junit.Assert;
import org.junit.Test;

public class FormulaTest {

    @Test
    public void sqrt() {
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 256);
            }
        };
        //delta 精度值
        Assert.assertEquals(32.91,formula.calculate(4),0.91);
        Assert.assertEquals(4,formula.sqrt(16),0);
    }
}