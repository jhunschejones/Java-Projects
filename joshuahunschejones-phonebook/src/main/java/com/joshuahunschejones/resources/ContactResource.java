package com.joshuahunschejones.resources;

import com.joshuahunschejones.dao.ContactDAO;
import com.joshuahunschejones.representations.Contact;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDAO;
    public ContactResource(DBI jdbi) {
        contactDAO = jdbi.onDemand(ContactDAO.class);
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id) {
        Contact contact = contactDAO.getContactById(id);
        return Response
            .ok(contact)
            .build();
    }

    @POST
    public Response createContact(Contact contact) throws URISyntaxException {
        int newContactId = contactDAO.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return Response
            .created(new URI(String.valueOf(newContactId)))
            .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact) {
        contactDAO.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return Response
            .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
            .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id) {
        contactDAO.deleteContact(id);
        return Response.noContent().build();
    }

}
