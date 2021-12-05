package io.github.kavahub.learnjava;

import java.util.Date;

import lombok.Data;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Data
public class Course {
    private int id;
    private String name;
    private String instructor;
    private Date enrolmentDate;
}
