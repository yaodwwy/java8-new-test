package cn.adbyte.java8newtest.annotation;

/**
 * 在Java 8中支持多重注解
 */
public @interface Hints {
    Hint[] value();
}
