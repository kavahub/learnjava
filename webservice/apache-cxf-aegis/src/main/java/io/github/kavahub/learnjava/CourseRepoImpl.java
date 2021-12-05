package io.github.kavahub.learnjava;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class CourseRepoImpl implements CourseRepo {
    private String greeting;
    private Map<Integer, Course> courses = new HashMap<>();

    @Override
    public String getGreeting() {
        return greeting;
    }

    @Override
    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    @Override
    public Map<Integer, Course> getCourses() {
        return courses;
    }

    @Override
    public void setCourses(Map<Integer, Course> courses) {
        this.courses = courses;
    }

    @Override
    public void addCourse(Course course) {
        courses.put(course.getId(), course);
    }
}
