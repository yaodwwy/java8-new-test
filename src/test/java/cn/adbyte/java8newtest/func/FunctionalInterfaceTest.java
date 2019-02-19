package cn.adbyte.java8newtest.func;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FunctionalInterfaceTest {
    @Test
    public void converter() {
        Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

        /**
         * Java 8 允许你使用 :: 关键字来传递方法或者构造函数引用
         * @Link cn.adbyte.java8newtest.factory.PersonFactoryTest
         */
        converter = Integer::valueOf;
        converted = converter.convert("123");
        System.out.println(converted);   // 123
    }
}
