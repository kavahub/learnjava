package io.github.kavahub.learnjava;

import javax.jws.WebService;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@WebService(endpointInterface = "io.github.kavahub.learnjava.StudentWS")
public class StudentWSImpl implements StudentWS {
    private int counter;

    public String hello(String name) {
        return "Hello " + name + "!";
    }

    public String register(Student student) {
        counter++;
        return student.getName() + " is registered student number " + counter;
    }
}
