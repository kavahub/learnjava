package io.github.kavahub.learnjava.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 当前正在执行方法查找
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class CurrentlyExecutedMethodFinderTest {
    @Test
    public void givenCurrentThread_whenGetStackTrace_thenFindMethod() {
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        assertEquals("givenCurrentThread_whenGetStackTrace_thenFindMethod", stackTrace[1].getMethodName());
    }

    @Test
    public void givenException_whenGetStackTrace_thenFindMethod() {
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        assertEquals("givenException_whenGetStackTrace_thenFindMethod", methodName);
    }

    @Test
    public void givenThrowable_whenGetStacktrace_thenFindMethod() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        assertEquals("givenThrowable_whenGetStacktrace_thenFindMethod", stackTrace[0].getMethodName());
    }

    @Test
    public void givenObject_whenGetEnclosingMethod_thenFindMethod() {
        String methodName = new Object() {}.getClass().getEnclosingMethod().getName();
        assertEquals("givenObject_whenGetEnclosingMethod_thenFindMethod", methodName);
    }

    @Test
    public void givenLocal_whenGetEnclosingMethod_thenFindMethod() {
        class Local {};
        String methodName = Local.class.getEnclosingMethod().getName();
        assertEquals("givenLocal_whenGetEnclosingMethod_thenFindMethod", methodName);
    }
    
}
