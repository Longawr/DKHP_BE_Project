package api.DAO;

import java.util.List;

import api.POJO.Subject;

public interface SubjectDAO {

    List<Subject> getSubjectList();

    Subject getSubjectByID(String subjectID);

    boolean removeSubjectByID(String subjectID);

    boolean addSubject(Subject sub);

    void updateSubject(Subject sub);

}