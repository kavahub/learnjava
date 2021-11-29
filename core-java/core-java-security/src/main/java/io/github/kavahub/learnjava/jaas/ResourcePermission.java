package io.github.kavahub.learnjava.jaas;

import java.security.BasicPermission;

/**
 * 
 * jaas 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class ResourcePermission extends BasicPermission {
    public ResourcePermission(String name) {
        super(name);
    }
    
}
