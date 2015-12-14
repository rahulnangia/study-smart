package com.ss.database.clients;

import com.ss.common.pojos.School;
import com.ss.common.pojos.User;
import com.ss.database.dao.UserDao;
import com.ss.database.dao.impl.UserDaoImpl;
import com.ss.database.util.HibernateConnector;

/**
 * A client to connect all kind of DAOs
 * Created by rahul on 12/15/15.
 */
public class RepositoryClient {

    private final UserDao userDao;

    public RepositoryClient(HibernateConnector hibernateConnector) {
        this.userDao = new UserDaoImpl(hibernateConnector);
    }

    public User getUser(String username){
        com.ss.database.dto.User userDto = userDao.findUser(username);  
        return userDto == null ? null : User.builder()
                .fname(userDto.getFname())
                .lname(userDto.getLname())
                .email(userDto.getEmail())
                .isActive(userDto.isActive())
                .school(userDto.getSchool() == null ? null : School.builder().sid(userDto.getSchool().getSid()).name(userDto.getSchool().getName()).build())
                .build();
    }
}
