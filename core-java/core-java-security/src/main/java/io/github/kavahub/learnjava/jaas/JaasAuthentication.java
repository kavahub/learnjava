package io.github.kavahub.learnjava.jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

/**
 * 
 * jaas 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class JaasAuthentication {
    public static void main(String[] args) throws LoginException {
        LoginService loginService = new LoginService();
        Subject subject = loginService.login();
        System.out.println(subject.getPrincipals().iterator().next() + " sucessfully logeed in");
    }   
}
