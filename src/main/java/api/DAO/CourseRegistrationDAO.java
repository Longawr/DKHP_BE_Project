package api.DAO;

import java.util.List;

import api.POJO.CourseRegistration;

public interface CourseRegistrationDAO {

    List<CourseRegistration> getCourseRegistrationList();

    CourseRegistration getCourseRegistrationByID(int id, int semesterId, int year);

    CourseRegistration getCourseRegistrationBySemester(int semesterId, int year);

    boolean removeCourseRegistrationByID(int id, int semesterId, int year);

    boolean addCourseRegistration(CourseRegistration temp);

}