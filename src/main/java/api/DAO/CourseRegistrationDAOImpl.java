package api.DAO;


import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.CourseRegistration;
import api.POJO.CourseRegistrationPK;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class CourseRegistrationDAOImpl implements CourseRegistrationDAO {
    private List<CourseRegistration> courseRegistrationList;

    @Override
    public List<CourseRegistration> getCourseRegistrationList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CourseRegistration";
            Query query = session.createQuery(hql);
            courseRegistrationList = query.list();
            session.close();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return courseRegistrationList;
    }

    @Override
    public CourseRegistration getCourseRegistrationByID(int id, int semesterId, int year) {
        CourseRegistration res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CourseRegistrationPK courseRegistrationID = new CourseRegistrationPK(id, semesterId, year);
            res = session.get(CourseRegistration.class, courseRegistrationID);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return res;
    }
    @Override
    public CourseRegistration getCourseRegistrationBySemester(int semesterId, int year) {
        CourseRegistration res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from CourseRegistration c where c.semesterId=:semesterId and c.year=:year";
            Query query = session.createQuery(hql);
            query.setParameter("semesterId", semesterId);
            query.setParameter("year", year);
            res = (CourseRegistration) query.setMaxResults(1).uniqueResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return res;
    }
    @Override
    public boolean removeCourseRegistrationByID(int id, int semesterId, int year) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseRegistration res = getCourseRegistrationByID(id, semesterId, year);
        if (res == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(res);
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
    public boolean addCourseRegistration(CourseRegistration temp) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getCourseRegistrationByID(temp.getId(), temp.getSemesterId(), temp.getYear()) != null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.save(temp);
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
