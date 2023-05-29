package api.DAO;

import java.util.List;

import api.POJO.Courseattend;
import api.POJO.Currentsemester;
import api.POJO.Semester;

public interface CourseattendDAO {

    List<Courseattend> getCourseattendList(String studentId , Semester sem);

    int getCourseattendListCount(String studentId, Currentsemester sem);

    int getCourseattendListCountByStudent(String studentId);

    Courseattend getCourseattendByID(String studentId, int courseId);

    List<Courseattend> getCourseattendByCourseID(int courseId);

    void removeCourseattendByID(String studentId, int id);

    void addCourseattend(Courseattend courseattend);
}