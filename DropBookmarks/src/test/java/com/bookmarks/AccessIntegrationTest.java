package com.bookmarks;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class AccessIntegrationTest {

    public static final String CONFIG_PATH = "config.yml";

    @ClassRule
    public static final DropwizardAppRule<DropBookmarksConfiguration> RULE =
            new DropwizardAppRule<DropBookmarksConfiguration>(DropBookmarksApplication.class, CONFIG_PATH);

    private static final HttpAuthenticationFeature FEATURE = HttpAuthenticationFeature.basic("username", "super_secure");
    private static final String TARGET = "http://localhost:8080";
    private static final String PATH = "/hello/secure";
    private Client client;

    @Before
    public void setup() {
        client= ClientBuilder.newClient();
    }

    @After
    public void teardown() {
        client.close();
    }

    @Test
    public void testUnauthorizedRequest() {
        Response response = client.target(TARGET).path(PATH).request().get();

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAuthorizedRequest() {
        String expected = "Hello More Secure World";
        client.register(FEATURE);
        String response = client.target(TARGET).path(PATH).request(MediaType.TEXT_PLAIN).get(String.class);

        assertEquals(expected, response);
    }
}
