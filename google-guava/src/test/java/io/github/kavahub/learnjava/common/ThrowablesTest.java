package io.github.kavahub.learnjava.common;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.base.Supplier;
import com.google.common.base.Throwables;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link Throwables} 简化异常和错误的传播与检查
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ThrowablesTest {
    @Test
    public void whenThrowable_shouldWrapItInRuntimeException() throws Exception {
        assertThrows(RuntimeException.class, () -> {
            try {
                throwThrowable(Throwable::new);
            } catch (Throwable t) {
                // 当且仅当它是一个RuntimeException和Error时，传递throwable
                Throwables.propagateIfPossible(t, Exception.class);
                throw new RuntimeException(t);
            }
        });
    }

    @Test
    public void whenError_shouldPropagateAsIs() throws Exception {
        assertThrows(Error.class, () -> {
            try {
                throwThrowable(Error::new);
            } catch (Throwable t) {
                Throwables.propagateIfPossible(t, Exception.class);
                //throw new RuntimeException(t);
            }
        });
    }

    @Test
    public void whenException_shouldPropagateAsIs() throws Exception {
        assertThrows(Exception.class, () -> {
            try {
                throwThrowable(Exception::new);
            } catch (Throwable t) {
                Throwables.propagateIfPossible(t, Exception.class);
                //throw new RuntimeException(t);
            }
        });
    }

    @Test
    public void whenChildException_shouldPropagateAsIs() throws Exception {
        assertThrows(Exception.class, () -> {
            try {
                throwThrowable(IllegalArgumentException::new);
            } catch (Throwable t) {
                Throwables.propagateIfPossible(t, RuntimeException.class);
                //throw new RuntimeException(t);
            }
        });
    }

    private <T extends Throwable> void throwThrowable(Supplier<T> exceptionSupplier) throws Throwable {
        throw exceptionSupplier.get();
    }
}
