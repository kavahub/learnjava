package io.github.kavahub.learnjava;


import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

/**
 * 学生
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
@XmlRootElement(name = "Student")
public class Student {
    private int id;
    private String name;
}
