package io.github.kavahub.learnjava.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解。创建属性构造器
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface PropertyBuilder {
    
}
