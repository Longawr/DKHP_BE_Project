package api.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import api.POJO.Account;
import api.UTIL.HibernateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountDAOImpl implements AccountDAO, UserDetailsService {
	
    private List<Account> accountList;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger log = LoggerFactory.getLogger(AccountDAOImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = this.getAccountByID(username);
        if(account == null){
        	log.error("user not found in database");
            try {
                throw new Exception("user not found in database");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
        	log.info("user found: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole()));
        return new org.springframework.security.core.userdetails.User(account.getAccountId(), account.getPassword(), authorities);
    }

    @Override
    public List<Account> getAccountList() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "from Account";
            Query query = session.createQuery(hql);
            accountList = query.list();
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return accountList;
    }

    @Override
    public Account getAccountByID(String accountID) {
        Account acc = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            acc = session.get(Account.class, accountID);
            session.close();
        } catch (HibernateException ex) {
            //Log the exception
            ex.printStackTrace();
        }
        return acc;
    }

    @Override
    public boolean removeAccountByID(String accountID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Account acc = getAccountByID(accountID);
        if (acc == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(acc);
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
    public boolean addAccount(Account acc) {
        Transaction transaction = null;
        acc.setPassword(passwordEncoder.encode(acc.getPassword()));
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getAccountByID(acc.getAccountId()) != null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.save(acc);
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
    public boolean updateAccount(Account acc) {
        Transaction transaction = null;
        acc.setPassword(passwordEncoder.encode(acc.getPassword()));
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            if (getAccountByID(acc.getAccountId()) == null) {
                return false;
            }
            transaction = session.beginTransaction();
            session.update(acc);
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

//    public
}
