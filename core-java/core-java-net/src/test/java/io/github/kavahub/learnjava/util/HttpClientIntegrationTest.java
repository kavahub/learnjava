package io.github.kavahub.learnjava.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * HttpClient集成测试
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
public class HttpClientIntegrationTest {
    @Test
    public void sendRquestWithAuthHeader() throws Exception {
        HttpClient httpClient = new HttpClient("user1", "pass1");

        int status = httpClient.sendRquestWithAuthHeader("https://httpbin.org/basic-auth/user1/pass1");

        assertTrue(isSuccess(status));
    }

    @Test
    public void sendRquestWithAuthHeader_whenIncorrectCredentials_thenNotSuccessful() throws Exception {
        HttpClient httpClient = new HttpClient("John", "Smith");

        int status = httpClient.sendRquestWithAuthHeader("https://httpbin.org/basic-auth/user1/pass1");

        assertTrue(isUnauthorized(status));
    }

    @Test
    public void sendRquestWithAuthenticator() throws Exception {
        HttpClient httpClient = new HttpClient("user2", "pass2");

        int status = httpClient.sendRquestWithAuthenticator("https://httpbin.org/basic-auth/user2/pass2");

        assertTrue(isSuccess(status));
    }

    @Test
    public void sendRquestWithAuthenticator_whenIncorrectCredentials_thenNotSuccessful() throws Exception {
        HttpClient httpClient = new HttpClient("John", "Smith");

        int status = httpClient.sendRquestWithAuthenticator("https://httpbin.org/basic-auth/user2/pass2");

        assertTrue(isUnauthorized(status));
    }

    private boolean isSuccess(int status) {
        return (status >= 200) && (status < 300);
    }

    private boolean isUnauthorized(int status) {
        return status == 401;
    }  
}
