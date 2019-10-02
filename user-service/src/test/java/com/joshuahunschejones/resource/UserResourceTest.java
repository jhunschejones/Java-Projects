package com.joshuahunschejones.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshuahunschejones.User;
import com.joshuahunschejones.UserDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UserResourceTest {

    private final UserDAO userDAO = mock(UserDAO.class);
    private final long newUserId = 0;
    private final long existingUserId = 100;
    private User newUser;
    private User existingUser;
    private User updatedUser;
    private ArrayList<User> existingUsers;
    private ObjectMapper mapper = new ObjectMapper();

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserResource(userDAO))
            .build();

    private ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    @Before
    public void setUp() {
        newUser = new User();
        newUser.setId(newUserId);
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setEmail("new-user@newrelic.com");

        existingUser = new User();
        existingUser.setId(existingUserId);
        existingUser.setFirstName("Existing");
        existingUser.setLastName("User");
        existingUser.setEmail("existing-user@newrelic.com");

        updatedUser = new User();
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setEmail("updated-user@newrelic.com");

        existingUsers = new ArrayList<User>();
        existingUsers.add(newUser);
    }

    @After
    public void tearDown() {
        reset(userDAO);
        existingUsers.clear();
    }

    @Test
    public void test_findById() throws JsonProcessingException {
        when(userDAO.findById(existingUserId)).thenReturn(java.util.Optional.ofNullable(existingUser));
        Response response = resources.client().target("/users/100").request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(existingUser));
    }

    @Test
    public void test_findByName() throws JsonProcessingException {
        when(userDAO.findByName("%" + existingUser.getFirstName() + "%")).thenReturn(existingUsers);
        Response response = resources.client().target("/users?name=" + existingUser.getFirstName()).request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(existingUsers));
    }

    @Test
    public void test_findAll() throws JsonProcessingException {
        when(userDAO.findAll()).thenReturn(existingUsers);
        Response response = resources.client().target("/users").request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.getHeaderString(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(existingUsers));
    }

    @Test
    public void test_create() throws JsonProcessingException {
        when(userDAO.create(any())).thenReturn(1);
        Response response = resources.client().target("/users").request().post(Entity.entity(newUser, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(newUser));
    }

    @Test
    public void test_update() throws JsonProcessingException {
        when(userDAO.findById(existingUserId)).thenReturn(java.util.Optional.ofNullable(existingUser));
        when(userDAO.update(existingUser)).thenReturn(1);
        updatedUser.setId(existingUserId);

        Response response = resources.client().target("/users/100").request().put(Entity.entity(updatedUser, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(updatedUser));
    }

    @Test
    public void test_deleteById() throws JsonProcessingException {
        when(userDAO.findById(existingUserId)).thenReturn(java.util.Optional.ofNullable(existingUser));
        when(userDAO.deleteById(existingUserId)).thenReturn(1);
        Response response = resources.client().target("/users/100").request().delete();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(existingUser));
    }
}
