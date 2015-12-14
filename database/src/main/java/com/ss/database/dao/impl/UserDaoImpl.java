package com.ss.database.dao.impl;

import com.ss.database.dao.UserDao;
import com.ss.database.dto.User;
import com.ss.database.util.HibernateConnector;
import com.ss.database.util.SessionOperationCallback;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by rahul on 12/3/15.
 */
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    public UserDaoImpl(HibernateConnector hibernateConnector) {
        super(hibernateConnector);
    }

    @Override
    public User findUser(final String username) {
        final SessionOperationCallback<User> userInfo = new SessionOperationCallback<User>() {
            private User user;
            @Override
            public <T> void execute(Session session) {
                String hql = "from User as user where user.username=:uid";
                Query query = session.createQuery(hql);
                query.setString("uid", username);
                user = (User) query.uniqueResult();
            }

            @Override
            public User get() {
                return user;
            }
        };
        executeOperation(userInfo);
        return userInfo.get();
    }

    @Override
    public boolean registerUser(User user) {
        return false;
    }

    @Override
    public boolean unRegisterUser(User user) {
        return false;
    }

    @Override
    public boolean loginUser(User user) {
        return false;
    }
}
