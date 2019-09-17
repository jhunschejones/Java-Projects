package com.joshuahunschejones.resource;

import com.joshuahunschejones.User;
import com.joshuahunschejones.UserDAO;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

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

    @POST
    @UnitOfWork
    public User saveUser(User user) {
        return userDAO.save(user);
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public User updateUser(@PathParam("id") LongParam id, User updatedUser) {
        Optional<User> user = userDAO.findById(id.get());

        if (user.isPresent()) {
            user.get().setFirstName(updatedUser.getFirstName());
            user.get().setLastName(updatedUser.getLastName());
            user.get().setEmail(updatedUser.getEmail());
            return userDAO.save(user.get());
        } else {
            return userDAO.save(updatedUser);
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
}
