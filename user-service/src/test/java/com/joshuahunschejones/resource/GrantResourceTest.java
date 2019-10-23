package com.joshuahunschejones.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshuahunschejones.grant.Grant;
import com.joshuahunschejones.grant.GrantDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GrantResourceTest {

    private final GrantDAO grantDAO = mock(GrantDAO.class);
    private Grant newGrant;
    private Grant existingGrant;

    private ObjectMapper mapper = new ObjectMapper();
    private ArgumentCaptor<Grant> grantCaptor = ArgumentCaptor.forClass(Grant.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new GrantResource(grantDAO))
            .build();

    @Before
    public void setUp() {
        newGrant = new Grant(2, 22, 222, 2222);
        existingGrant = new Grant(1, 11, 1111, 1111);
    }

    @After
    public void tearDown() {
        reset(grantDAO);
    }

    @Test
    public void test_index_with_userId_param() throws JsonProcessingException {
        when(grantDAO.findByUserId(existingGrant.getUserId())).thenReturn(new ArrayList<Grant>() {{ add(existingGrant); }});
        Response response = resources.client().target("/grants?user_id=" + existingGrant.getUserId()).request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(new ArrayList<Grant>() {{ add(existingGrant); }}));
    }

    @Test
    public void test_index_with_accountId_param() throws JsonProcessingException {
        when(grantDAO.findByAccountId(existingGrant.getAccountId())).thenReturn(new ArrayList<Grant>() {{ add(existingGrant); }});
        Response response = resources.client().target("/grants?account_id=" + existingGrant.getAccountId()).request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(new ArrayList<Grant>() {{ add(existingGrant); }}));
    }

    @Test
    public void test_index_without_valid_params() throws JsonProcessingException {
        Response response = resources.client().target("/grants").request().get();
        assertThat(response.getStatus()).isEqualTo(BAD_REQUEST.getStatusCode());
    }

    @Test
    public void test_create() throws JsonProcessingException {
        when(grantDAO.create(any())).thenReturn(1);
        Response response = resources.client().target("/grants").request().post(Entity.entity(newGrant, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(newGrant));
    }

    @Test
    public void test_create_duplicate() throws JsonProcessingException {
        when(grantDAO.find(any())).thenReturn(Optional.of(newGrant));
        Response response = resources.client().target("/grants").request().post(Entity.entity(newGrant, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(CONFLICT.getStatusCode());
    }

    @Test
    public void test_delete_existing() throws JsonProcessingException {
        when(grantDAO.find(grantCaptor.capture())).thenReturn(Optional.of(existingGrant));
        when(grantDAO.delete(existingGrant)).thenReturn(1);
        Response response = resources.client()
                .target("/grants")
                .property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true)
                .request()
                .method("DELETE", Entity.entity(existingGrant, MediaType.APPLICATION_JSON_TYPE));

        assertThat(grantCaptor.getValue().getAccountId()).isEqualTo(existingGrant.getAccountId());
        assertThat(grantCaptor.getValue().getUserId()).isEqualTo(existingGrant.getUserId());
        assertThat(grantCaptor.getValue().getRoleId()).isEqualTo(existingGrant.getRoleId());

        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(existingGrant));
        assertThat(grantCaptor.getValue().getAccountId()).isEqualTo(existingGrant.getAccountId());
    }

    @Test
    public void test_delete_nonexistent_grant() throws JsonProcessingException {
        Response response = resources.client()
                .target("/grants")
                .property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true)
                .request()
                .method("DELETE", Entity.entity(newGrant, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }
}
