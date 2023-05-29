package api.DAO;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import api.POJO.Classname;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Transactional
public class ClassDAOImpl implements ClassDAO{
    private List<Classname> classList;

    @Override
    public List<Classname> getClassList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Classname";
            Query query = session.createQuery(hql);
            classList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return classList;
    }
    @Override
    public Classname getClassByID(String id) {
        Classname cls = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            cls = session.get(Classname.class, id);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return cls;
    }

    @Override
    public boolean removeClassByID(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Classname cls = getClassByID(id);
        if (cls == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(cls);
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
    public boolean addClass(Classname cls) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if ((getClassByID(cls.getId())) != null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.save(cls);
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
    public Long getClassMemberCount(String id) {
        Long count = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(*) from Student st inner join st.classnameByClassName where st.classnameByClassName.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = (Long) query.uniqueResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public Long getClassMaleCount(String id) {
        Long count = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(*) from Student st inner join st.classnameByClassName where st.classnameByClassName.id=:id and st.sex='Nam'";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = (Long) query.uniqueResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public Long getClassFemaleCount(String id) {
        Long count = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "select count(*) from Student st inner join st.classnameByClassName where st.classnameByClassName.id=:id and st.sex = 'Nữ'";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = (Long) query.uniqueResult();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public void updateClass(Classname currentClass) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getClassByID(currentClass.getId()) == null) {
                return;
            }
            transaction = session.beginTransaction();
            session.update(currentClass);
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
