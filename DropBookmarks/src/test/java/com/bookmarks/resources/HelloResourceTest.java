package com.bookmarks.resources;

import com.bookmarks.core.User;
import com.google.common.base.Optional;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class HelloResourceTest {

    private static final HttpAuthenticationFeature FEATURE = HttpAuthenticationFeature.basic("u", "p");
    
    // https://www.dropwizard.io/0.9.2/docs/manual/auth.html
    private static final Authenticator<BasicCredentials, User> AUTHENTICATOR = new Authenticator<BasicCredentials, User>(){

        @Override
        public Optional<User> authenticate(BasicCredentials basicCredentials) {
            return Optional.of(new User());
        }
    };

    @BeforeClass
    public static void setUpClass() {
        RULE.getJerseyTest().client().register(FEATURE);
    }

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule
            .builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addProvider(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                    .setAuthenticator(AUTHENTICATOR)
                    .setRealm("SECURITY REALM")
                    .buildAuthFilter()))
            .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
            .addResource(new HelloResource())
            .build();

    @Test
    //@Ignore
    public void testhelloResource() {
        String expected = "Hello World";
        String actual = RULE.getJerseyTest().target("/hello").request(MediaType.TEXT_PLAIN).get(String.class);
        assertEquals(expected, actual);
    }

    @Test
    public void testSecureHelloResource() {
        String expected = "Hello More Secure World";
        String actual = RULE.getJerseyTest().target("/hello/secure").request(MediaType.TEXT_PLAIN).get(String.class);
        assertEquals(expected, actual);
    }
}