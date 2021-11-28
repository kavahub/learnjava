package io.github.kavahub.learnjava.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * {@link MethodHandles}示例
 * 
 * <p>
 * MethodHandle进行方法调用一般需要以下几步：
 * 
 * <ul>
 * <li>（1）创建MethodType对象，指定方法的签名；</li>
 * <li>（2）在MethodHandles.Lookup中查找类型为MethodType的MethodHandle；</li>
 * <li>（3）传入方法参数并调用MethodHandle.invoke或者MethodHandle.invokeExact方法。</li>
 * </ul>
 * 
 * <p>
 * MethodType实例，有三种创建方式：
 * 
 * <ul>
 * <li>（1）methodType及其重载方法：需要指定返回值类型以及0到多个参数；</li>
 * <li>（2）genericMethodType：需要指定参数的个数，类型都为Object；</li>
 * <li>（3）fromMethodDescriptorString：通过方法描述来创建。</li>
 * </ul>
 * 
 * Lookup
 * 
 * <p>
 * LookupMethodHandle.Lookup相当于MethodHandle工厂类，通过findxxx方法可以得到相应的MethodHandle，
 * 还可以配合反射API创建MethodHandle，对应的方法有unreflect、unreflectSpecial等。
 * 
 * <p>
 * MethodHandle后就可以进行方法调用了，有三种调用形式：
 * 
 * <ul>
 * <li>（1）invokeExact:调用此方法与直接调用底层方法一样，需要做到参数类型精确匹配；</li>
 * <li>（2）invoke:参数类型松散匹配，通过asType自动适配；</li>
 * <li>（3）invokeWithArguments:直接通过方法参数来调用。其实现是先通过genericMethodType方法得到MethodType，
 * 再通过MethodHandle的asType转换后得到一个新的MethodHandle，最后通过新MethodHandle的invokeExact方法来完成调用 </li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MethodHandlesTest {
    @Test
    public void givenConcatMethodHandle_whenInvoked_thenCorrectlyConcatenated() throws Throwable {
        // 所有 public 方法
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        // 返回值：String 参数：String 
        MethodType mt = MethodType.methodType(String.class, String.class);
        // String 类中查找方法：1. 方法可见性：public  2. 方法名：concat 3. 参数：String 4. 返回值：String
        MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        // 调用
        String output = (String) concatMH.invoke("Effective ", "Java");

        assertEquals("Effective Java", output);
    }

    @Test
    public void givenAsListMethodHandle_whenInvokingWithArguments_thenCorrectlyInvoked() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(List.class, Object[].class);
        // 静态方法查找
        MethodHandle asListMH = publicLookup.findStatic(Arrays.class, "asList", mt);

        @SuppressWarnings("unchecked")
        List<Integer> list = (List<Integer>) asListMH.invokeWithArguments(1, 2);

        assertThat(Arrays.asList(1, 2)).isEqualTo(list);
    }

    @Test
    public void givenConstructorMethodHandle_whenInvoked_thenObjectCreatedCorrectly() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(void.class, String.class);
        // 构造函数查找
        MethodHandle newIntegerMH = publicLookup.findConstructor(Integer.class, mt);

        Integer integer = (Integer) newIntegerMH.invoke("1");

        assertEquals(1, integer.intValue());
    }

    @Test
    public void givenAFieldWithoutGetter_whenCreatingAGetter_thenCorrectlyInvoked() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // getter方法查找
        MethodHandle getTitleMH = lookup.findGetter(Book.class, "title", String.class);

        Book book = new Book("ISBN-1234", "Effective Java");

        assertEquals("Effective Java", getTitleMH.invoke(book));
    }

    @Test
    public void givenPrivateMethod_whenCreatingItsMethodHandle_thenCorrectlyInvoked() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        // 私有方法
        Method formatBookMethod = Book.class.getDeclaredMethod("formatBook");
        formatBookMethod.setAccessible(true);

        MethodHandle formatBookMH = lookup.unreflect(formatBookMethod);

        Book book = new Book("ISBN-123", "Java in Action");

        assertEquals("ISBN-123 > Java in Action", formatBookMH.invoke(book));
    }

    @Test
    public void givenReplaceMethod_whenUsingReflectionAndInvoked_thenCorrectlyReplaced() throws Throwable {
        Method replaceMethod = String.class.getMethod("replace", char.class, char.class);

        String string = (String) replaceMethod.invoke("jovo", 'o', 'a');

        assertEquals("java", string);
    }

    @Test
    public void givenReplaceMethodHandle_whenInvoked_thenCorrectlyReplaced() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMH = publicLookup.findVirtual(String.class, "replace", mt);

        String replacedString = (String) replaceMH.invoke("jovo", Character.valueOf('o'), 'a');

        assertEquals("java", replacedString);
    }

    @Test
    public void givenReplaceMethodHandle_whenInvokingExact_thenCorrectlyReplaced() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        MethodHandle replaceMH = lookup.findVirtual(String.class, "replace", mt);

        String s = (String) replaceMH.invokeExact("jovo", 'o', 'a');

        assertEquals("java", s);
    }

    @Test
    public void givenSumMethodHandle_whenInvokingExact_thenSumIsCorrect() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);

        int sum = (int) sumMH.invokeExact(1, 11);

        assertEquals(12, sum);
    }

    @Test // (expected = WrongMethodTypeException.class)
    public void givenSumMethodHandleAndIncompatibleArguments_whenInvokingExact_thenException() throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(int.class, int.class, int.class);
        MethodHandle sumMH = lookup.findStatic(Integer.class, "sum", mt);

        assertThrows(WrongMethodTypeException.class, () -> sumMH.invokeExact(Integer.valueOf(1), 11));
    }

    @Test
    public void givenSpreadedEqualsMethodHandle_whenInvokedOnArray_thenCorrectlyEvaluated() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(boolean.class, Object.class);
        MethodHandle equalsMH = publicLookup.findVirtual(String.class, "equals", mt);

        MethodHandle methodHandle = equalsMH.asSpreader(Object[].class, 2);

        assertTrue((boolean) methodHandle.invoke(new Object[] { "java", "java" }));
        assertFalse((boolean) methodHandle.invoke(new Object[] { "java", "jova" }));
    }

    @Test
    public void givenConcatMethodHandle_whenBindToAString_thenCorrectlyConcatenated() throws Throwable {
        MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();
        MethodType mt = MethodType.methodType(String.class, String.class);
        MethodHandle concatMH = publicLookup.findVirtual(String.class, "concat", mt);

        MethodHandle bindedConcatMH = concatMH.bindTo("Hello ");

        assertEquals("Hello World!", bindedConcatMH.invoke("World!"));
    }

    public static class Book {

        String id;
        String title;

        public Book(String id, String title) {
            this.id = id;
            this.title = title;
        }

        @SuppressWarnings("unused")
        private String formatBook() {
            return id + " > " + title;
        }
    }
}
