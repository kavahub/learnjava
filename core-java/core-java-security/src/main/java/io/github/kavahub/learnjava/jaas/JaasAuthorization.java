package io.github.kavahub.learnjava.jaas;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

/**
 * 
 * jaas 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class JaasAuthorization {
    public static void main(String[] args) throws LoginException {

        LoginService loginService = new LoginService();
        Subject subject = loginService.login();

        PrivilegedAction privilegedAction = new ResourceAction();
        Subject.doAsPrivileged(subject, privilegedAction, null);
    }
}
