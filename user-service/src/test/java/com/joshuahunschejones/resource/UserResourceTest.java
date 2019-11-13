package com.joshuahunschejones.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joshuahunschejones.grant.Grant;
import com.joshuahunschejones.user.User;
import com.joshuahunschejones.user.UserDAO;
import com.joshuahunschejones.user.UserWithGrants;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Optional;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UserResourceTest {

    private final UserDAO userDAO = mock(UserDAO.class);
    private final long newUserId = 0;
    private final long existingUserId = 100;
    private User newUser;
    private Grant grant;
    private UserWithGrants userWithGrants;
    private UserWithGrants userWithNoGrants;
    private User existingUser;
    private User updatedUser;
    private ArrayList<User> existingUsers;
    private ObjectMapper mapper = new ObjectMapper();

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UserResource(userDAO))
            .build();

    @Before
    public void setUp() {
        newUser = new User(newUserId, "Test", "User","new-user@newrelic.com");
        existingUser = new User(existingUserId, "Existing", "User", "existing-user@newrelic.com");

        updatedUser = new User();
        updatedUser.setFirstName("Updated");
        updatedUser.setLastName("User");
        updatedUser.setEmail("updated-user@newrelic.com");

        existingUsers = new ArrayList<User>();
        existingUsers.add(newUser);

        grant = new Grant(2, newUserId, 222, 2222);
        userWithGrants = new UserWithGrants(existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail(), new ArrayList<Grant>() {{add(grant);}});
        userWithNoGrants = new UserWithGrants(existingUser.getId(), existingUser.getFirstName(), existingUser.getLastName(), existingUser.getEmail(), new ArrayList<Grant>());
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
    public void test_findNewUserById() throws JsonProcessingException {
        when(userDAO.findById(newUser.getId())).thenReturn(Optional.empty());
        Response response = resources.client().target("/users/0").request().get();
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }

    @Test
    public void test_findByName() throws JsonProcessingException {
        when(userDAO.findByName("%" + existingUser.getFirstName() + "%")).thenReturn(existingUsers);
        Response response = resources.client().target("/users?name=" + existingUser.getFirstName()).request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(existingUsers));
    }

    @Test
    public void test_getUserWithGrants() throws JsonProcessingException {
        when(userDAO.findByIdWithGrants(existingUserId)).thenReturn(java.util.Optional.ofNullable(userWithGrants));
        Response response = resources.client().target("/users/100/grants").request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(userWithGrants));
    }

    @Test
    public void test_getNewUserWithGrants() throws JsonProcessingException {
        when(userDAO.findByIdWithGrants(newUser.getId())).thenReturn(Optional.empty());
        Response response = resources.client().target("/users/0/grants").request().get();
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }

    @Test
    public void test_getUserWithNoGrants() throws JsonProcessingException {
        when(userDAO.findByIdWithGrants(existingUserId)).thenReturn(Optional.empty());
        when(userDAO.findById(existingUserId)).thenReturn(java.util.Optional.ofNullable(existingUser));
        Response response = resources.client().target("/users/100/grants").request().get();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(userWithNoGrants));
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
    public void test_updateCalledForNewUser() throws JsonProcessingException {
        when(userDAO.findById(existingUserId)).thenReturn(Optional.empty());
        Response response = resources.client().target("/users/0").request().put(Entity.entity(newUser, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(newUser));
    }

    @Test
    public void test_deleteById() throws JsonProcessingException {
        when(userDAO.findById(existingUserId)).thenReturn(java.util.Optional.ofNullable(existingUser));
        when(userDAO.deleteById(existingUserId)).thenReturn(1);
        Response response = resources.client().target("/users/100").request().delete();
        assertThat(response.getStatus()).isEqualTo(OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo(mapper.writeValueAsString(existingUser));
    }

    @Test
    public void test_deleteNewUserById() throws JsonProcessingException {
        when(userDAO.findById(newUser.getId())).thenReturn(Optional.empty());
        Response response = resources.client().target("/users/0").request().delete();
        assertThat(response.getStatus()).isEqualTo(NOT_FOUND.getStatusCode());
    }
}
