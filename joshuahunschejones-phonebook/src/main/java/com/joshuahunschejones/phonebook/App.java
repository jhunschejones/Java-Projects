package com.joshuahunschejones.phonebook;

import com.google.common.cache.CacheBuilderSpec;
import com.joshuahunschejones.resources.ClientResource;
import com.joshuahunschejones.resources.ContactResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import io.dropwizard.Application;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application<PhonebookConfiguration> {
    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void initialize(Bootstrap<PhonebookConfiguration> bootstrap) {

    }

    @Override
    public void run(PhonebookConfiguration c, Environment e) throws Exception {
        LOGGER.info("Method App#run() called");
        System.out.println(c.getMessage());
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");
        e.jersey().register(new ContactResource(jdbi, e.getValidator()));

        // authenticator with caching support
        CachingAuthenticator<BasicCredentials, Boolean> authenticator = new CachingAuthenticator<BasicCredentials, Boolean>(
                e.metrics(),
                new PhonebookAuthenticator(jdbi),
                CacheBuilderSpec.parse("maximumSize=10000, expireAfterAccess=10m"));
        e.jersey().register(new BasicAuthProvider<Boolean>(authenticator, "Web Service Realm"));

        // basic browser-based client, normally this wouldn't be in the same project
        final Client client = new JerseyClientBuilder(e).build("REST Client");
        client.addFilter(new HTTPBasicAuthFilter("clientuser", "clientpassword"));
        e.jersey().register(new ClientResource(client));
    }
}
