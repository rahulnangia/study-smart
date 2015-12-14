package com.ss.service.resources;

import com.google.inject.Inject;
import com.ss.common.pojos.User;
import com.ss.database.clients.RepositoryClient;
import com.ss.service.annotations.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by rahul on 12/15/15.
 */
@Resource
@Path("/user")
public class UserResource {

    private final RepositoryClient repository;

    @Inject
    public UserResource(RepositoryClient repository) {
        this.repository = repository;
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(@QueryParam("uid") String username){
        return username == null ? null : repository.getUser(username);
    }
}
