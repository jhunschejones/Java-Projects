package com.bookmarks.resources;

import com.bookmarks.core.User;
import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;

@Path("/hello")
public class HelloResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting() {
        return "Hello World";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/secure") // "/helo/secure"
    public String getSecuredGreeting(@Auth User user) {
        return "Hello More Secure World";
    }
}
