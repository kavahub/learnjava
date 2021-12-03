package io.github.kavahub.learnjava;

import jakarta.jws.WebService;
import lombok.extern.slf4j.Slf4j;

/**
 * webservice服务实现
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
@WebService(endpointInterface = "io.github.kavahub.learnjava.HelloWS")
public class HelloWSImpl implements HelloWS {

    @Override
    public String hello(String name) {
        log.info(">>>>>>>>> {} <<<<<<<<<", name);
        return "Hello " + name;
    }
    
}
