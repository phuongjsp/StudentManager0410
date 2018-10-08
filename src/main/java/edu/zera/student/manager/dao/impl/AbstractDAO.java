package edu.zera.student.manager.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.ParameterizedType;
import java.io.Serializable;
import java.util.List;
import org.hibernate.query.Query;
@Transactional
public abstract class AbstractDAO<PK extends Serializable, T> {
    private final Class<T> persistentClass;
    private SessionFactory sessionFactory;

    protected AbstractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    protected Session getSession() {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }
    protected List<T> findAll() {
        Query query = getSession().createQuery("FROM " + persistentClass.getSimpleName());
        return query.getResultList();
    }
    protected T getByKey(PK key) {
        return getSession().get(persistentClass, key);
    }
    protected T saveDAO(T entity) {
        try {
            getSession().save(entity);
            return entity;
        } catch (Exception e) {
            return null;
        }
    }
    protected boolean updateDAO(T entity) {
        try {
            getSession().update(entity);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    protected boolean deleteByKey(PK key) {
        try {
            T entity = getSession().byId(persistentClass).load(key);
            getSession().delete(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    protected boolean deleteDAO(T entity){
        try {
            getSession().delete(entity);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

}
