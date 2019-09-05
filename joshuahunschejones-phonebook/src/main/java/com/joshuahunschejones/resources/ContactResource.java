package com.joshuahunschejones.resources;

import com.joshuahunschejones.dao.ContactDAO;
import com.joshuahunschejones.representations.Contact;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {

    private final ContactDAO contactDao;
    private final Validator validator;

    public ContactResource(DBI jdbi, Validator validator) {
        contactDao = jdbi.onDemand(ContactDAO.class);
        this.validator = validator;
    }

    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id, @Auth Boolean isAuthenticated) {
        Contact contact = contactDao.getContactById(id);
        return Response
            .ok(contact)
            .build();
    }

    @POST
    public Response createContact(Contact contact, @Auth Boolean isAuthenticated) throws URISyntaxException {
        // validate new contact
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                .status(Status.BAD_REQUEST)
                .entity(validationMessages)
                .build();
        } else {
            int newContactId = contactDao.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());
            return Response.created(new URI(String.valueOf(newContactId))).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, Contact contact, @Auth Boolean isAuthenticated) {
        // validate contact updates
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Contact> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {
            contactDao.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());
            return Response
                    .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id, @Auth Boolean isAuthenticated) {
        contactDao.deleteContact(id);
        return Response.noContent().build();
    }

}
