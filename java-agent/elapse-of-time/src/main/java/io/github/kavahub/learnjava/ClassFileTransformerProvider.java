package io.github.kavahub.learnjava;

import java.lang.instrument.ClassFileTransformer;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link ClassFileTransformer} 提供者，配置环境变量 {@code class_file_transformer}
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class ClassFileTransformerProvider implements Supplier<ClassFileTransformer> {
    public final static String CLASS_FILE_TRANSFORMER_KEY = "class_file_transformer";
    private final String CLASS_FILE_TRANSFORMER_VALUE;

    public final static ClassFileTransformerProvider INSTANCE = new ClassFileTransformerProvider();

    private ClassFileTransformerProvider() {
        CLASS_FILE_TRANSFORMER_VALUE = System.getenv(CLASS_FILE_TRANSFORMER_KEY);
    }

    @Override
    public ClassFileTransformer get() {
        String className = CLASS_FILE_TRANSFORMER_VALUE;
        if (className == null) {
            // 默认
            className = ClassFileTransformerWithASM.class.getName();
        }

        // 反射创建
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
            if (ClassFileTransformer.class.isAssignableFrom(clazz)) {
                return (ClassFileTransformer)clazz.getDeclaredConstructor().newInstance();
            }
            log.error("Is not a correct subclass of ClassFileTransformer -> {}", className);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException 
            | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            log.error("Fail to create object", e);
        }

        

        return null;
    }

}
