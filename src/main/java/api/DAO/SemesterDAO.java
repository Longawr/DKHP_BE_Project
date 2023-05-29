package api.DAO;

import java.util.List;

import api.POJO.Semester;

public interface SemesterDAO {

    List<Semester> getSemesterList();

    Semester getSemesterByID(int year, int id);

    boolean removeSemesterByID(int year, int id);

    boolean addSemester(Semester sem);












}