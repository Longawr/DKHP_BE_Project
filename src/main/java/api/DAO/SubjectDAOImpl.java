package api.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Subject;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class SubjectDAOImpl implements SubjectDAO {
    private List<Subject> subjectList;

    @Override
    public List<Subject> getSubjectList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Subject";
            Query query = session.createQuery(hql);
            subjectList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return subjectList;
    }

    @Override
    public Subject getSubjectByID(String subjectID) {
        Subject sub = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            sub = session.get(Subject.class, subjectID);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return sub;
    }

    @Override
    public boolean removeSubjectByID(String subjectID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Subject sub = getSubjectByID(subjectID);
        if (sub == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sub);
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
    public boolean addSubject(Subject sub) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getSubjectByID(sub.getId()) != null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.save(sub);
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

    @Override
    public void updateSubject(Subject sub) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getSubjectByID(sub.getId()) == null) {
                return;
            }
            transaction = session.beginTransaction();
            session.update(sub);
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
