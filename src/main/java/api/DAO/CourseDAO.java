package api.DAO;

import java.util.List;

import api.POJO.Course;

public interface CourseDAO {

    List<Course> getCourseListBySemester(int id, int year);

    Course getCourseByID(int id);

    int getCourseStudentCount(int id);

    boolean removeCourseByID(int id);

    boolean addCourse(Course temp);

    boolean updateCourse(Course temp);

    Course getUniqueCourseBySubject(String subjectId);

}