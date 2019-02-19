package cn.adbyte.java8newtest.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 未测试成功...暂时不继续
 */
@RunWith(JUnit4.class)
public class AnnotationTest {

    @Test
    public void name() {
        Hint hint = NewAnno.class.getAnnotation(Hint.class);
        System.out.println(hint);                   // null
        Hints hints1 = NewAnno.class.getAnnotation(Hints.class);
        System.out.println(hints1);  // 2

        Hint[] hints2 = NewAnno.class.getAnnotationsByType(Hint.class);
        System.out.println(hints2.length);          // 2
    }
}