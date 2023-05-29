package api.DAO;

import java.util.List;

import api.POJO.Classname;

public interface ClassDAO{

    List<Classname> getClassList();

    Classname getClassByID(String id);

    boolean removeClassByID(String id);

    boolean addClass(Classname cls);

    Long getClassMemberCount(String id);

    Long getClassMaleCount(String id);

    Long getClassFemaleCount(String id);

    void updateClass(Classname currentClass);
}