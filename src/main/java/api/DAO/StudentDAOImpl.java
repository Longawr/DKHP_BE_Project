package api.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Student;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class StudentDAOImpl implements StudentDAO {
    private List<Student> studentList;

    @Override
    public List<Student> getStudentList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Student";
            Query query = session.createQuery(hql);
            studentList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return studentList;
    }

    @Override
    public Student getStudentByID(String studentID) {
        Student student = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            student = session.get(Student.class, studentID);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return student;
    }

    @Override
    public Student getStudentByUsername(String username) {
        Student student = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Student st where st.accountByAccount.id=:username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            student = (Student) query.uniqueResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return student;
    }
    @Override
    public boolean removeStudentByID(String studentID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student st = getStudentByID(studentID);
        if (st == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(st);
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
    public boolean addStudent(Student st) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getStudentByID(st.getId()) != null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.save(st);
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
    public void updateStudent(Student st) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getStudentByID(st.getId()) == null) {
                return;
            }
            transaction = session.beginTransaction();
            session.update(st);
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
    public List<Student> getStudentListByClass(String classId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Student st where st.classnameByClassName.id=:classId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            studentList = query.list();
            studentList.forEach(student -> student.getAccountByAccount().setPassword(""));
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return studentList;
    }
}
