package io.github.kavahub.learnjava.ws;

import jakarta.jws.WebService;

/**
 * 简单示例服务
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebService
public interface WelcomeWS {
    public String say(String name);
}
