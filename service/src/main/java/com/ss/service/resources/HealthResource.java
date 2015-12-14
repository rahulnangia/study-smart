package com.ss.service.resources;

import com.ss.service.annotations.Resource;
import com.ss.service.model.NameRequest;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by rahul on 12/1/15.
 */
@Path("/health")
@Resource
public class HealthResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public NameRequest heathCheck(){
        return new NameRequest("Ok","status",null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello(@Valid NameRequest request){
        return Response.ok().entity(String.valueOf("Hello " + request.getMyName())).build();
    }
}
