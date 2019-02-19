package cn.adbyte.java8newtest.func;

/**
 * default关键字
 */
public interface Formula {
    double calculate(int a);
    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
