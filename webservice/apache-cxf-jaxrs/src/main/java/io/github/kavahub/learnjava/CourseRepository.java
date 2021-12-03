package io.github.kavahub.learnjava;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * 接口
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
@Path("courses")
@Produces("text/xml")
public class CourseRepository {
    private Map<Integer, Course> courses = new HashMap<>();

    {
        Student student1 = new Student();
        Student student2 = new Student();
        student1.setId(1);
        student1.setName("Student A");
        student2.setId(2);
        student2.setName("Student B");

        Course course1 = new Course();
        Course course2 = new Course();
        course1.setId(1);
        course1.setName("REST with Spring");
        course1.addStudent(student1);
        course1.addStudent(student2);
        course2.setId(2);
        course2.setName("Learn Spring Security");

        courses.put(1, course1);
        courses.put(2, course2);
    }



    @GET
    @Path("{courseId}")
    public Response getCourse(@PathParam("courseId") int courseId) {
        Course existingCourse = findById(courseId);
        if (existingCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(existingCourse).build();
    }

    @PUT
    @Path("{courseId}")
    public Response updateCourse(@PathParam("courseId") int courseId, Course course) {
        Course existingCourse = findById(courseId);
        if (existingCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (existingCourse.equals(course)) {
            return Response.notModified().build();
        }
        courses.put(courseId, course);
        return Response.ok().build();
    }

    @GET
    @Path("{courseId}/students/{studentId}")
    public Response getStudent(@PathParam("courseId") int courseId, @PathParam("studentId") int studentId) {
        Course existingCourse = findById(courseId);
        if (existingCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return existingCourse.getStudent(studentId);
    }

    @DELETE
    @Path("{courseId}/students/{studentId}")
    public Response deleteStudent(@PathParam("courseId") int courseId, @PathParam("studentId") int studentId) {
        Course existingCourse = findById(courseId);
        if (existingCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return existingCourse.deleteStudent(studentId);
    }

    @POST
    @Path("{courseId}/students")
    public Response createStudent(@PathParam("courseId") int courseId, Student student) {
        Course existingCourse = findById(courseId);
        if (existingCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return existingCourse.createStudent(student);
    }

    private Course findById(int id) {
        for (Map.Entry<Integer, Course> course : courses.entrySet()) {
            if (course.getKey() == id) {
                return course.getValue();
            }
        }
        return null;
    }
}
