package io.github.kavahub.learnjava;

import javax.jws.WebService;

/**
 * 学生WebService服务
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebService
public interface StudentWS {
    String hello(String name);

    String register(Student student);
}
