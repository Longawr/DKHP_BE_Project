package api.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import api.POJO.Role;
import api.UTIL.HibernateUtil;

@Repository
public class RoleDAOImpl implements RoleDAO{

    @Override
    public Role getById(String id) {
        Role role = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            role = session.get(Role.class, id);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return role;
    }
}
