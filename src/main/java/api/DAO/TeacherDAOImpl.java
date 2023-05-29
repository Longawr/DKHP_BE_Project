package api.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Teacher;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class TeacherDAOImpl implements TeacherDAO {
    private List<Teacher> teacherList;

    @Override
    public List<Teacher> getTeacherList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Teacher";
            Query query = session.createQuery(hql);
            teacherList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return teacherList;
    }

    @Override
    public Teacher getTeacherByID(String teacherID) {
        Teacher teacher = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            teacher = session.get(Teacher.class, teacherID);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return teacher;
    }

    @Override
    public boolean removeTeacherByID(String teacherID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Teacher tch = getTeacherByID(teacherID);
        if (tch == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(tch);
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
    public boolean addTeacher(Teacher tch) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getTeacherByID(tch.getId()) != null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.save(tch);
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
    public void updateTeacher(Teacher tch) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getTeacherByID(tch.getId()) == null) {
                return;
            }
            transaction = session.beginTransaction();
            session.update(tch);
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
    public Teacher getTeacherByUsername(String username) {
        Teacher teacher = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Teacher tc where tc.accountByAccount.accountId=:username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            teacher = (Teacher) query.uniqueResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return teacher;
    }
}
