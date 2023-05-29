package api.DAO;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Courseattend;
import api.POJO.CourseattendPK;
import api.POJO.Currentsemester;
import api.POJO.Semester;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class CourseattendDAOImpl implements Serializable, CourseattendDAO {
    private List<Courseattend> courseattendList;

    @Override
    public List<Courseattend> getCourseattendList(String studentId , Semester sem) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Courseattend c where c.courseByCourseId.semesterId=:semesterId and c.courseByCourseId.year=:year  and c.studentId=:studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("year", sem.getYear());
            query.setParameter("semesterId", sem.getId());
            courseattendList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return courseattendList;
    }
    @Override
    public int getCourseattendListCount(String studentId, Currentsemester sem) {
        int count = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(*) from Courseattend c where c.courseByCourseId.semesterId=:semesterId and c.courseByCourseId.year=:year  and c.studentId=:studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("year", sem.getYear());
            query.setParameter("semesterId", sem.getId());
            count = ((Long) query.uniqueResult()).intValue();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return count;
    }
    @Override
    public int getCourseattendListCountByStudent(String studentId) {
        int count = 0;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(*) from Courseattend c where c.studentId=:studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            count = ((Long) query.uniqueResult()).intValue();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return count;
    }
    @Override
    public Courseattend getCourseattendByID(String studentId, int courseId) {
        Courseattend res = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CourseattendPK temp = new CourseattendPK(studentId, courseId);
            res = session.get(Courseattend.class, temp);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return res;
    }
    @Override
    public List<Courseattend> getCourseattendByCourseID(int courseId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Courseattend c where c.courseId=:courseId";
            Query query = session.createQuery(hql);
            query.setParameter("courseId", courseId);
            courseattendList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return courseattendList;
    }
    @Override
    public void removeCourseattendByID(String studentId, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Courseattend res = getCourseattendByID(studentId, id);
        if (res == null) {
            return;
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
    }

    @Override
    public void addCourseattend(Courseattend courseattend) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Courseattend res = getCourseattendByID(courseattend.getStudentId(), courseattend.getCourseId());
        if (res != null) {
            return;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(courseattend);
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            assert transaction != null;
            transaction.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }
}
