package com.joshuahunschejones.resource;

import com.google.common.base.Optional;
import com.joshuahunschejones.User;
import com.joshuahunschejones.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private UserDAO userDAO;

    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Optional<User> getUser(@PathParam("id") LongParam id) {
        return userDAO.findById(id.get());
    }

    @GET
    @UnitOfWork
    public List<User> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return userDAO.findByName(name.get());
        } else {
            return userDAO.findAll();
        }
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Optional<User> delete(@PathParam("id")LongParam id) {
        Optional<User> user = userDAO.findById(id.get());

        if (user.isPresent()) {
            userDAO.delete(user.get());
        }
        return user;
    }

    @POST
    @UnitOfWork
    public User saveUser(User user) {
        return userDAO.save(user);
    }
}
