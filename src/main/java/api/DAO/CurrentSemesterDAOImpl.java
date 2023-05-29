package api.DAO;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Currentsemester;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class CurrentSemesterDAOImpl implements CurrentSemesterDAO {
    private Currentsemester currentSemester;

    @Override
    public Currentsemester getCurrentSemester() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Currentsemester";
            Query query = session.createQuery(hql);
            currentSemester = (Currentsemester) query.getSingleResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return currentSemester;
    }

    @Override
    public void addCurrrentSemester(Currentsemester sem) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
    }

    @Override
    public void removeCurrrentSemester() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Currentsemester sem = getCurrentSemester();
            transaction = session.beginTransaction();
            session.remove(sem);
            transaction.commit();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            assert transaction != null;
            transaction.rollback();
            ex.printStackTrace();
        }
    }
}
