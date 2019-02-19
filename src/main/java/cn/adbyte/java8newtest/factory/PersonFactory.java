package cn.adbyte.java8newtest.factory;

import cn.adbyte.java8newtest.pojo.Person;

public interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
