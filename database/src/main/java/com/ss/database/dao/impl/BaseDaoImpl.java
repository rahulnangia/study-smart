package com.ss.database.dao.impl;

import com.ss.common.constants.ErrorMessages;
import com.ss.database.dao.BaseDao;
import com.ss.common.exceptions.impl.SSDatabaseException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ss.database.util.HibernateConnector;
import com.ss.database.util.SessionOperationCallback;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by rahul on 12/2/15.
 */
class BaseDaoImpl implements BaseDao {

    private final HibernateConnector hibernateConnector;

    private static Logger LOGGER = LoggerFactory.getLogger(BaseDaoImpl.class);

    public BaseDaoImpl(HibernateConnector hibernateConnector) {
        this.hibernateConnector = hibernateConnector;
    }

    @Override
    public <T> T get(final Class<T> clazz, final Serializable key) {
        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {
            public Object obj;

            @Override
            public <T> void execute(Session session) {
                obj = session.load(clazz, key);
                obj = hibernateConnector.unproxy(obj);
            }

            @Override
            public T get() {
                return (T) obj;
            }
        };
        executeOperation(execution);
        return execution.get();
    }

    @Override
    public <T> List<T> getAll(final Class<T> c) {

        SessionOperationCallback<List<T>> execution = new SessionOperationCallback<List<T>>() {
            public List objects;

            @Override
            public void execute(Session session) {
                Query query = session.createQuery("from " + c.getName());
                objects = query.list();
            }

            @Override
            public List<T> get() {
                return (List<T>) objects;
            }
        };
        executeOperation(execution);
        return execution.get();
    }

    @Override
    public <T> T save(final Object entity) {
        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {
            public T object;

            @Override
            public void execute(Session session) {
                object = (T) session.save(entity);
            }

            @Override
            public T get() {
                return object;
            }
        };
        executeOperation(execution);
        return execution.get();
    }

    @Override
    public <T> void delete(final Object entity) {

        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {

            @Override
            public void execute(Session session) {
                session.delete(entity);
            }

            @Override
            public T get() {
                return null;
            }
        };
        executeOperation(execution);
    }

    @Override
    public <T> void deleteAll(final Collection entities) {
        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {

            @Override
            public void execute(Session session) {
                for (Object entity : entities) {
                    session.delete(entity);
                }
            }

            @Override
            public T get() {
                return null;
            }
        };
        executeOperation(execution);
    }

    @Override
    public <T> void saveAll(final Collection entities) {
        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {

            @Override
            public void execute(Session session) {
                for (Object entity : entities) {
                    session.save(entity);
                }
            }

            @Override
            public T get() {
                return null;
            }
        };
        executeOperation(execution);
    }

    @Override
    public <T> void saveOrUpdate(final Object entity) {

        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {

            @Override
            public void execute(Session session) {
                session.saveOrUpdate(entity);
            }

            @Override
            public T get() {
                return null;
            }
        };
        executeOperation(execution);
    }

    @Override
    public <T> void saveOrUpdateAll(final Collection entities) {
        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {

            @Override
            public void execute(Session session) {
                for (Object entity : entities) {
                    session.saveOrUpdate(entity);
                }
            }

            @Override
            public T get() {
                return null;
            }
        };
        executeOperation(execution);
    }

    @Override
    public int getTotalCount(final Class clazz) {
        SessionOperationCallback<Integer> execution = new SessionOperationCallback<Integer>() {
            public int count;

            @Override
            public void execute(Session session) {
                count = (Integer) session.createCriteria(clazz.getName()).setProjection(Projections.rowCount()).uniqueResult();
            }

            @Override
            public Integer get() {
                return count;
            }
        };
        executeOperation(execution);
        return execution.get();
    }


    @Override
    public <T> T update(final Object entity) {

        SessionOperationCallback<T> execution = new SessionOperationCallback<T>() {
            public T object;

            @Override
            public void execute(Session session) {
                object = (T) session.merge(entity);
            }

            @Override
            public T get() {
                return object;
            }
        };
        executeOperation(execution);
        return execution.get();

    }

    protected <T> void executeOperation(SessionOperationCallback<T> sqlCommand) throws HibernateException {
        if (hibernateConnector.getSessionFactory().getCurrentSession() != null && hibernateConnector.getSessionFactory().getCurrentSession().isOpen()) {
            hibernateConnector.getSessionFactory().getCurrentSession().close();
        }
        Session session = null;
        Transaction tx = null;
        try {
            session = hibernateConnector.getSessionFactory().openSession();
            tx = session.beginTransaction();
            sqlCommand.execute(session);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                hibernateConnector.rollback(tx);
            }
            LOGGER.error(ErrorMessages.DATABASE_OPERATION, e);
            throw new SSDatabaseException(e);
        } catch (Exception e) {
            LOGGER.error(ErrorMessages.DATABASE_OPERATION, e);
            throw new SSDatabaseException(e);
        } finally {
            if (session != null) {
                hibernateConnector.closeSession(session);
            }
        }
    }

}
