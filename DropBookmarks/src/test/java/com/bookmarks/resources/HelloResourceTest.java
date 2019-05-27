package com.bookmarks.resources;

import com.bookmarks.core.User;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

public class HelloResourceTest {

    private static final HttpAuthenticationFeature FEATURE = HttpAuthenticationFeature.basic("u", "p");

    private static final Authenticator<BasicCredentials, User> authenticator = new Authenticator<BasicCredentials, User>() {

        @Override
        public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
            return Optional.of(new User());
        }
    };

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule
            .builder()
            .addProvider(AuthFactory.binder(new BasicAuthFactory<>(authenticator, "REALM", User.class)))
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addResource(new HelloResource())
            .build();

    @BeforeClass
    public static void setUpClass() {
        RULE.getJerseyTest().client().register(FEATURE);
    }

    @Test
    public void testGetGreeting() {
        String expected = "Hello World";
        String actual = RULE.getJerseyTest().target("/hello").request(MediaType.TEXT_PLAIN).get(String.class);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetSecureGreeting() {
        String expected = "Hello More Secure World";
        String actual = RULE.getJerseyTest().target("/hello/secure").request(MediaType.TEXT_PLAIN).get(String.class);

        Assert.assertEquals(expected, actual);
    }
}
