package api.DAO;

import api.POJO.Currentsemester;

public interface CurrentSemesterDAO {

    Currentsemester getCurrentSemester();

    void addCurrrentSemester(Currentsemester sem);

    void removeCurrrentSemester();

}