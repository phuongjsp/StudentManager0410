package edu.zera.student.manager.dao.impl;

import edu.zera.student.manager.dao.UserDAO;
import edu.zera.student.manager.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class UserDAOImpl extends AbstractDAO<Integer,User> implements UserDAO {

    protected UserDAOImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public User save(User user) {
        return super.saveDAO(user);
    }

    @Override
    public boolean update(User user) {
        return super.updateDAO(user);
    }

    @Override
    public boolean delete(String username) {
           User user = this.findByUsername(username);
           if (user==null) return false;
         return super.deleteDAO(user);
    }

    @Override
    public User findByUsername(String username) {
      try {
          return (User) getSession().createQuery("select u FROM User u where u.username=:username")
                  .setParameter("username",username)
                  .getSingleResult();
      }catch (Exception e){
          return null;
      }
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }
}
