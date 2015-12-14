package com.ss.database.util;

import com.google.common.base.Joiner;
import com.ss.common.constants.Constants;
import com.ss.common.constants.ErrorMessages;
import com.ss.common.enums.SSEnvironment;
import com.ss.common.exceptions.impl.HibernateInitializationException;
import lombok.Getter;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rahul on 12/2/15.
 */
@Getter
public class HibernateConnector {

    private SessionFactory sessionFactory;

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConnector.class);

    public HibernateConnector(SSEnvironment environment) {
        createSessionFactory(Joiner.on('/').join(environment.name().toLowerCase(), Constants.HIBERNATE_CONFIG_FILE_NAME));
    }

    public HibernateConnector(String configFile) {

    }

    /**
     * Close session factory
     */
    public void closeFactory() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            } catch (HibernateException ignored) {
                LOGGER.error("Couldn't close sessionFactory", ignored);
            }
        }
    }

    /**
     * Rollback the transaction in case if its not null
     *
     * @param tx transaction to be rolled back
     */
    public void rollback(Transaction tx) {
        try {
            if (tx != null) {
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            LOGGER.error("Couldn't rollback Transaction", ignored);
        }
    }

    /**
     * Close the session in case if its not null
     *
     * @param session session to be closed
     */
    public void closeSession(Session session) {
        if (session != null) {
            try {
                if (session.isOpen())
                    session.close();
            } catch (HibernateException ignored) {
                LOGGER.error("Couldn't close session", ignored);
            }
        }
    }

    /**
     * Used to convert hibernate objects to database transfer object
     *
     * @param proxied hibernate object to be converted
     * @return converted database transfer object
     */
    public Object unproxy(Object proxied) {
        Object entity = proxied;
        if (entity != null && entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

    private void createSessionFactory(String configFilePath){
        try {
            sessionFactory = new Configuration().configure(configFilePath).buildSessionFactory();
        } catch (Throwable e) {
            LOGGER.error(ErrorMessages.HIBERNATE_INITIALIZATION, e);
            throw new HibernateInitializationException(ErrorMessages.HIBERNATE_INITIALIZATION + String.format(" from config file: %s", configFilePath), e);
        }
    }

}
