package api.DAO;

import java.util.List;

import api.POJO.Student;

public interface StudentDAO {

    List<Student> getStudentList();

    Student getStudentByID(String studentID);

    Student getStudentByUsername(String username);

    boolean removeStudentByID(String studentID);

    boolean addStudent(Student st);

    void updateStudent(Student st);

    List<Student> getStudentListByClass(String classId);

}