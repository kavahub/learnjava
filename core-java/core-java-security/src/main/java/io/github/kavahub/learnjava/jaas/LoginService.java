package io.github.kavahub.learnjava.jaas;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * 
 * jaas 应用示例
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class LoginService {
    public Subject login() throws LoginException {
        LoginContext loginContext = new LoginContext("jaasApplication", new ConsoleCallbackHandler());
        loginContext.login();
        return loginContext.getSubject();
    }
}
