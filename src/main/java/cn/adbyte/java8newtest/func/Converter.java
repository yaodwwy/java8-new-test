package cn.adbyte.java8newtest.func;

/**
 * 编译器如果发现你标注了这个注解的接口
 * 有多于一个抽象方法的时候会报错
 * @param <F>
 * @param <T>
 */
@FunctionalInterface
interface Converter<F, T> {
    T convert(F from);
}
