package api.DAO;


import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Semester;
import api.POJO.SemesterPK;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class SemesterDAOImpl implements SemesterDAO {
    private List<Semester> semesterList;

    @Override
    public List<Semester> getSemesterList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Semester";
            Query query = session.createQuery(hql);
            semesterList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return semesterList;
    }

    @Override
    public Semester getSemesterByID(int year, int id) {
        Semester sem = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            SemesterPK semID = new SemesterPK(year, id);
            sem = session.get(Semester.class, semID);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return sem;
    }

    @Override
    public boolean removeSemesterByID(int year, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Semester sem = getSemesterByID(year, id);
        if (sem == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sem);
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            assert transaction != null;
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean addSemester(Semester sem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getSemesterByID(sem.getYear(), sem.getId()) != null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.save(sem);
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            assert transaction != null;
            transaction.rollback();
            ex.printStackTrace();
        }
        return true;
    }
}
