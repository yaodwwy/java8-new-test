package cn.adbyte.java8newtest.func;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RunWith(JUnit4.class)
@SuppressWarnings("all")
public class LambdaTest {
    @Test
    public void lambda() {
        //老方法
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
        //新方法
        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });
        //简洁写法
        Collections.sort(names, (String a, String b) -> b.compareTo(a));
        //超简写法
        Collections.sort(names, (a, b) -> b.compareTo(a));
        //极简写法 函数体只有一行代码的 自动推导出参数类型
        Collections.sort(names, Comparator.reverseOrder());
    }

    /**
     * 作用域
     */
    @Test
    public void testScopes1() {
        int num = 1;
        // 访问外层的局部变量
        Converter<Integer, String> stringConverter =
                (from) -> String.valueOf(from + num);
        stringConverter.convert(2);     // 3
        // num必须不可被后面的代码修改（即隐性的具有final的语义）
        // lambda表达式中试图修改num同样是不允许的
//        num = 2;
    }

    static int outerStaticNum;
    int outerNum;

    /**
     * 访问对象字段与静态变量
     * lambda内部对于实例的字段以及静态变量是即可读又可写
     */
    @Test
    public void testScopes2() {
        Converter<Integer, String> stringConverter1 = (from) -> {
            outerNum = 23;
            return String.valueOf(from);
        };

        Converter<Integer, String> stringConverter2 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from);
        };
    }
}
