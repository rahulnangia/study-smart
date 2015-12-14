package com.ss.database.dao;

import com.ss.database.dto.User;

/**
 * Created by rahul on 12/3/15.
 */
public interface UserDao extends BaseDao {

    public User findUser(String username);

    public boolean registerUser(User user);

    public boolean unRegisterUser(User user);

    public boolean loginUser(User user);
}
