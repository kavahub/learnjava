package io.github.kavahub.learnjava;

import jakarta.jws.WebService;

/**
 * webservice接口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebService
public interface HelloWS {
    public String hello(String name);
}
