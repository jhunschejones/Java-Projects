package com.joshuahunschejones.resource;

import com.joshuahunschejones.grant.Grant;
import com.joshuahunschejones.grant.GrantDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/grants")
@Produces(MediaType.APPLICATION_JSON)
public class GrantResource {

    private GrantDAO grantDAO;
    public GrantResource(GrantDAO grantDAO) {
        this.grantDAO = grantDAO;
    }

    // GET `/grants?account_id=1`
    // GET /grants?user_id=1`
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Grant> index(@QueryParam("user_id") Optional<String> userId,
                             @QueryParam("account_id") Optional<String> accountId) {
        if (userId.isPresent()) {
            return grantDAO.findByUserId(Long.parseLong(userId.get()));
        } else if (accountId.isPresent()) {
            return grantDAO.findByAccountId(Long.parseLong(accountId.get()));
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    // POST `/grants`
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<Grant> create(Grant grant) {
        if (grantDAO.find(grant).isPresent()) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        }
        if (grantDAO.create(grant) > 0) {
            return Optional.of(grant);
        }
        return Optional.empty();
    }

    // DELETE `/grants`
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Grant delete(Grant grant) {
        Optional<Grant> grantToDelete = grantDAO.find(grant);

        if (grantToDelete.isPresent()) {
            grantDAO.delete(grantToDelete.get());
        } else {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return grant;
    }
}
