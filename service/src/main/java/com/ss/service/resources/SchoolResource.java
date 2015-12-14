package com.ss.service.resources;

import com.ss.service.annotations.Resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by rahul on 12/15/15.
 */
@Path("/school")
@Resource
public class SchoolResource {

    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public void addSchool(){

    }
}
