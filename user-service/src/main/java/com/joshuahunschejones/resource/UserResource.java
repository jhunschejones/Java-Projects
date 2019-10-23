package com.joshuahunschejones.resource;

import com.joshuahunschejones.user.User;
import com.joshuahunschejones.user.UserDAO;
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
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<User> getUser(@PathParam("id") LongParam id) {
        return userDAO.findById(id.get());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findByName(@QueryParam("name") Optional<String> name) {
        if (name.isPresent()) {
            return userDAO.findByName("%" + name.get() + "%");
        } else {
            return userDAO.findAll();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<User> saveUser(User user) {
        if (userDAO.create(user) > 0) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<User> updateUser(@PathParam("id") long id, User updatedUser) {
        Optional<User> user = userDAO.findById(id);

        if (user.isPresent()) {
            user.get().setFirstName(updatedUser.getFirstName());
            user.get().setLastName(updatedUser.getLastName());
            user.get().setEmail(updatedUser.getEmail());
            if (userDAO.update(user.get()) > 0) {
                return Optional.of(user.get());
            } else {
                return Optional.empty();
            }
        } else {
            userDAO.create(updatedUser);
            return Optional.of(updatedUser);
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<User> delete(@PathParam("id")LongParam id) {
        Optional<User> user = userDAO.findById(id.get());

        if (user.isPresent()) {
            userDAO.deleteById(id.get());
        }
        return user;
    }
}
