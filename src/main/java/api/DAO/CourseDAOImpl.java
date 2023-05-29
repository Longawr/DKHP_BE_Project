package api.DAO;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Course;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class CourseDAOImpl implements Serializable, CourseDAO {
    private List<Course> courseList;

    @Override
    public List<Course> getCourseListBySemester(int id, int year) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Course c where c.semesterId=:semId and c.year=:year";
            Query query = session.createQuery(hql);
            query.setParameter("semId", id);
            query.setParameter("year", year);
            courseList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return courseList;
    }

    @Override
    public Course getCourseByID(int id) {
        Course res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            res = session.get(Course.class, id);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return res;
    }
    @Override
    public int getCourseStudentCount(int id) {
        int count = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select  count (*) from Courseattend ca where ca.courseId=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = ((Long) query.uniqueResult()).intValue();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return count;
    }
    @Override
    public boolean removeCourseByID(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course res = getCourseByID(id);
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
    public boolean addCourse(Course temp) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
    @Override
    public boolean updateCourse(Course temp) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(temp);
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
    public Course getUniqueCourseBySubject(String subjectId) {
        Course res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Course c where c.subjectBySubjectId.id=:subjectId";
            Query query = session.createQuery(hql);
            query.setParameter("subjectId", subjectId);
            res = (Course) query.setMaxResults(1).uniqueResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return res;
    }
}
