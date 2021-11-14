package io.github.kavahub.learnjava.jaas;

import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;

@SuppressWarnings("all")
public class JaasAuthorization {
    public static void main(String[] args) throws LoginException {

        LoginService loginService = new LoginService();
        Subject subject = loginService.login();

        PrivilegedAction privilegedAction = new ResourceAction();
        Subject.doAsPrivileged(subject, privilegedAction, null);
    }
}
