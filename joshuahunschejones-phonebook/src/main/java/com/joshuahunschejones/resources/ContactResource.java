package com.joshuahunschejones.resources;

import com.joshuahunschejones.representations.Contact;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {
        return Response
            .ok(new Contact(id, "Carl", "Fox", "+0123456789"))
            .build();
    }

    @POST
    public Response createContact(Contact contact) {
        return Response
            .created(null)
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        return Response
            .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        return Response
            .noContent()
            .build();
    }

}
