package io.github.kavahub.learnjava.ws;

import io.github.kavahub.learnjava.SecurityContextHolder;
import jakarta.jws.WebService;
import lombok.extern.slf4j.Slf4j;

/**
 * 简单示例服务实现
 * 
 * <p>
 * 此服务不需要安全认知
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
@WebService(endpointInterface = "io.github.kavahub.learnjava.ws.WelcomeWS")
public class WelcomeWSImpl implements WelcomeWS{

    @Override
    public String say(String name) {
        log.info(SecurityContextHolder.INSTANCE.currentInfo());

        return name + " Welcome";
    }
    
}
