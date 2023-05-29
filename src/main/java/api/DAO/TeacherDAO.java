package api.DAO;

import java.util.List;

import api.POJO.Teacher;

public interface TeacherDAO {

    List<Teacher> getTeacherList();

    Teacher getTeacherByID(String teacherID);

    boolean removeTeacherByID(String teacherID);

    boolean addTeacher(Teacher tch);

    void updateTeacher(Teacher tch);

    Teacher getTeacherByUsername(String username);




}