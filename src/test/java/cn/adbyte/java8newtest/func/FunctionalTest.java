package cn.adbyte.java8newtest.func;

import cn.adbyte.java8newtest.pojo.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@RunWith(JUnit4.class)
public class FunctionalTest {
    /**
     * Predicate 接口只有一个参数，返回boolean类型。该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）：
     */
    @Test
    public void predicate() {

        Predicate<String> predicate = (s) -> s.length() > 0;

        Assert.assertTrue("长度必须大于0", predicate.test("foo"));
        Assert.assertTrue("长度不能大于0", predicate.negate().test(""));

        Predicate<Boolean> nonNull = Objects::nonNull;
        Assert.assertTrue("参数不能是null", nonNull.test(false));
        Predicate<Boolean> isNull = Objects::isNull;
        Assert.assertTrue("参数必须是null", isNull.test(null));

        Predicate<String> isEmpty = String::isEmpty;
        Assert.assertTrue("长度必须是空", isEmpty.test(""));
        //NullPointerException
        //Assert.isTrue(isEmpty.test(null),"NullPointerException");
        Predicate<String> isNotEmpty = isEmpty.negate();
        Assert.assertTrue("长度不能为空", isNotEmpty.test("foo"));
    }

    /**
     * Function 接口有一个参数并且返回一个结果，并附带了一些可以和其他函数组合的默认方法（compose, andThen）：
     */
    @Test
    public void function() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger.andThen(String::valueOf);
        Assert.assertEquals("123", backToString.apply("123"));
    }

    /**
     * Supplier 接口返回一个任意范型的值，和Function接口不同的是该接口没有任何参数
     */
    @Test
    public void supplier() {
        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();   // new Person
    }

    /**
     * Consumer 接口表示执行在单个参数上的操作。
     */
    @Test
    public void consumer() {
        Consumer<Person> greeter = System.out::println;
        greeter.accept(new Person("Luke", "Skywalker"));
    }

    /**
     * Comparator 是老Java中的经典接口， Java 8在此之上添加了多种默认方法：
     */
    @Test
    public void comparator() {
        Comparator<Person> comparator = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");

        int compare = comparator.compare(p1, p2);// > 0
        int compare1 = comparator.reversed().compare(p1, p2);// < 0
        System.out.println(compare);
        System.out.println(compare1);
    }

    /**
     * 防止NullPointerException异常的辅助类型
     */
    @Test
    public void optional() {
        Optional<String> optional = Optional.of("bam");
        System.out.println(optional.isPresent());
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"

        optional.ifPresent(System.out::println);     // "b"
    }

    /**
     * java.util.Stream 表示能应用在一组元素上一次执行的操作序列
     */
    List<String> stringCollection = new ArrayList<>();

    /**
     * Stream 操作分为中间操作或者最终操作两种，最终操作返回一特定类型的计算结果，而中间操作返回Stream本身，这样你就可以将多个操作依次串起来。
     * Stream的操作可以串行执行或者并行执行。
     */
    @Test
    public void stream() {
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
    }

    /**
     * 过滤通过一个predicate接口来过滤并只保留符合条件的元素，该操作属于中间操作，所以我们可以在过滤后的结果来应用其他Stream操作（比如forEach）
     */
    @Test
    public void filter() {
        stream();
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        // "aaa2", "aaa1"
    }

    /**
     * 排序是一个中间操作，返回的是排序好后的Stream。如果你不指定一个自定义的Comparator则会使用默认排序。
     * 排序只创建了一个排列好后的Stream，而不会影响原有的数据源
     */
    @Test
    public void sort() {
        stream();
        stringCollection
                .stream()
                .sorted()
//                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
    }

    /**
     * 中间操作map会将元素根据指定的Function接口来依次将元素转成另外的对象，
     */
    @Test
    public void map() {
        stream();
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);
    }

    /**
     * 允许检测指定的Predicate是否匹配整个Stream。所有的匹配操作都是最终操作，并返回一个boolean类型的值。
     */
    @Test
    public void match() {
        stream();
        boolean anyStartsWithA = stringCollection.stream()
                .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true
        boolean allStartsWithA = stringCollection.stream()
                .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);      // false
        boolean noneStartsWithZ = stringCollection.stream()
                .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true
    }

    /**
     * 计数是一个最终操作，返回Stream中元素的个数，返回值类型是long。
     */
    @Test
    public void count() {
        stream();
        long startsWithB = stringCollection.stream()
                .filter((s) -> s.startsWith("b"))
                .count();
        System.out.println(startsWithB);    // 3
    }

    /**
     * 这是一个最终操作，允许通过指定的函数来讲stream中的多个元素规约为一个元素，规约后的结果是通过Optional
     */
    @Test
    public void reduced() {
        stream();
        Optional<String> reduced = stringCollection.stream()
                .sorted()
                .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
// "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"
    }

    /**
     * Stream有串行和并行两种，串行Stream上的操作是在一个线程中依次完成，而并行Stream则是在多个线程上同时执行。
     */
    @Test
    public void parallelStream() {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        //串行排序：
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));
        //sequential sort took: 724 ms

        //并行排序：

        t0 = System.nanoTime();
        count = values.parallelStream().sorted().count();
        System.out.println(count);
        t1 = System.nanoTime();
        millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
        //parallel sort took: 311 ms
    }

    /**
     * Map类型不支持stream，不过Map提供了一些新的有用的方法来处理一些日常任务。
     */
    @Test
    public void streamWithMap() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }

        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33
        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true

        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33
        //如何在Map里删除一个键值全都匹配的项
        map.remove(3, "val3");
        System.out.println(map.get(3));
        map.remove(3, "val33");
        System.out.println(map.get(3));

        System.out.println(map.getOrDefault(42, "not found"));

        //Map的元素做合并
        //Merge做的事情是如果键名不存在则插入，否则则对原键对应的值做合并操作并重新插入到map中。
        System.out.println(map.get(9));
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9));
        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        System.out.println(map.get(9));
    }
}
