package io.github.kavahub.learnjava.jaas;

import java.security.PrivilegedAction;

@SuppressWarnings("all")
public class ResourceAction implements PrivilegedAction {
    @Override
    public Object run() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new ResourcePermission("test_resource"));
        }
        System.out.println("I have access to test_resource !");
        return null;
    }
    
}
