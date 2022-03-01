package com.alten.altencore.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 *
 * @author utente
 * @param <I>
 * @param <E>
 */
public class GenericDAO<I extends Serializable, E> {

    private final Class<E> clazz;
    private SessionFactory sessionFactory;
    private static HashMap<String, SessionFactory> sessionFactoryMap;

    static {
        if (sessionFactoryMap == null) {
            sessionFactoryMap = new LinkedHashMap<>();
        }
    }

    public GenericDAO(String cfgXmlPath, Class<E> clazz) {
        if (clazz != null) {
            this.clazz = clazz;
        } else {
            throw new IllegalArgumentException("You are trying to pass a null class to GenericDAO's constructor");
        }

        if (cfgXmlPath == null || cfgXmlPath.equals("")) {
            throw new IllegalArgumentException("You are trying to pass a blank cfg xml path to GenericDAO's constructor");
        }

        if (sessionFactoryMap.keySet().contains(cfgXmlPath)) {
            sessionFactory = sessionFactoryMap.get(cfgXmlPath);
        } else {
            Configuration configuration = new Configuration().configure(cfgXmlPath);
            ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
            registry.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            sessionFactoryMap.put(cfgXmlPath, sessionFactory);
        }
    }

    public GenericDAO(SessionFactory sessionFactory, Class<E> clazz) {
        this.clazz = Objects.requireNonNull(clazz, "You are trying to pass a null class to GenericDAO's constructor");
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "You are trying to pass a null sessionFactory to GenericDAO's constructor");
    }

    public Session openSession() {
        return getSessionFactory().openSession();
    }

    public void closeSession(Session s) {
        if (s != null && s.isOpen()) {
            s.clear();
            s.flush();
            s.close();
        }
    }

    public E get(I id) {
        Session s = null;
        try {
            s = getSessionFactory().openSession();
            return (E) get(id, s);
        } finally {
            closeSession(s);
        }
    }

    public E get(I id, Session s) {
        return (E) s.get(clazz, id);
    }

    public List<E> loadAll() {
        Session s = null;
        try {
            s = getSessionFactory().openSession();
            return loadAll(s);
        } finally {
            closeSession(s);
        }
    }

    public List<E> loadAll(Session s) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        return dc.getExecutableCriteria(s).list();
    }

    public void save(E e) {
        Session s = null;
        Transaction t = null;
        try {
            s = getSessionFactory().openSession();
            t = s.beginTransaction();
            save(e, s);
            t.commit();
        } catch (HibernateException he) {
            if (t != null) {
                t.rollback();
            }
            throw he;
        } finally {
            closeSession(s);
        }
    }

    public void save(E e, Session s) {
        s.save(e);
    }

    public void saveOrUpdate(E e) {
        Session s = null;
        Transaction t = null;
        try {
            s = getSessionFactory().openSession();
            t = s.beginTransaction();
            saveOrUpdate(e, s);
            t.commit();
        } catch (HibernateException he) {
            if (t != null) {
                t.rollback();
            }
            throw he;
        } finally {
            closeSession(s);
        }
    }

    public void saveOrUpdate(E e, Session s) {
        s.saveOrUpdate(e);
    }

    public void update(E e) {
        Session s = null;
        Transaction t = null;
        try {
            s = getSessionFactory().openSession();
            t = s.beginTransaction();
            update(e, s);
            t.commit();
        } catch (HibernateException he) {
            if (t != null) {
                t.rollback();
            }
            throw he;
        } finally {
            closeSession(s);
        }
    }

    public void update(E e, Session s) {
        s.update(e);
    }

    public void remove(E e) {
        Session s = null;
        Transaction t = null;
        try {
            s = getSessionFactory().openSession();
            t = s.beginTransaction();
            remove(e, s);
            t.commit();
        } catch (HibernateException he) {
            if (t != null) {
                t.rollback();
            }
            throw he;
        } finally {
            closeSession(s);
        }
    }

    public void remove(I id, Session s) {
        E e = get(id, s);
        remove(e, s);
    }

    public void remove(I id) {
        E e = get(id);
        remove(e);
    }

    public void remove(E e, Session s) {
        s.delete(e);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
