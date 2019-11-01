package com.joshuahunschejones.resource;

import com.joshuahunschejones.grant.Grant;
import com.joshuahunschejones.user.User;
import com.joshuahunschejones.user.UserDAO;
import com.joshuahunschejones.user.UserWithGrants;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
        Optional<User> user = userDAO.findById(id.get());
        if (user.isPresent()) {
            return user;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/{id}/grants")
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<UserWithGrants> getUserWithGrants(@PathParam("id") LongParam id) {
        Optional<UserWithGrants> userWithGrants = userDAO.findByIdWithGrants(id.get());
        if (userWithGrants.isPresent()) {
            return userWithGrants;
        }

        Optional<User> user = userDAO.findById(id.get());
        if (user.isPresent()) {
            return Optional.of(new UserWithGrants(
                    user.get().getId(),
                    user.get().getFirstName(),
                    user.get().getLastName(),
                    user.get().getEmail(),
                    new ArrayList<Grant>()
            ));
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
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
            return user;
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}
