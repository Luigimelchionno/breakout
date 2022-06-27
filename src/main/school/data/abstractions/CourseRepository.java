package main.school.data.abstractions;

import main.school.data.DataException;
import main.school.model.Course;

import java.io.IOException;
import java.util.List;

public interface CourseRepository {
    void addCourse(Course course) throws DataException;

    List<Course> getCoursesByTitleLike(String title) throws DataException;
    List<Course> getAllCourses() throws DataException;
}
