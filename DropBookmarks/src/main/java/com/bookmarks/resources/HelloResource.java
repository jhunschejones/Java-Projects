package com.bookmarks.resources;

import com.bookmarks.core.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting() {
        return "Hello World";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/secure")
    @UnitOfWork
    public String getSecureGreeting(@Auth User user) {
        return "Hello More Secure World";
    }
}
