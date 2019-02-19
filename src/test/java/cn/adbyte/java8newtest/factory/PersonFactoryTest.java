package cn.adbyte.java8newtest.factory;

import cn.adbyte.java8newtest.pojo.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 方法与构造函数引用
 */
@RunWith(JUnit4.class)
public class PersonFactoryTest {
    @Test
    public void create() {
        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("Peter", "Parker");
        Assert.assertNotNull(person);
        Assert.assertEquals("Peter",person.getFirstName());
    }
}