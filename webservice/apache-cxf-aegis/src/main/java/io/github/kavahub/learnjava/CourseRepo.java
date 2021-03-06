package io.github.kavahub.learnjava;

import java.util.Map;

/**
 * 课程接口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public interface CourseRepo {
    String getGreeting();
    void setGreeting(String greeting);
    Map<Integer, Course> getCourses();
    void setCourses(Map<Integer, Course> courses);
    void addCourse(Course course);  
}
